package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.Command.Command;
import invaders.ConfigReader;
import invaders.Points;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.factory.EnemyProjectile;
import invaders.factory.PlayerProjectile;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.rendering.Renderable;
import invaders.status.ScoreTimeKeeper;
import invaders.status.Subject;
import invaders.undo.MementoManager;
import invaders.undo.EnemyMemento;
import invaders.undo.Memento;
import invaders.undo.Originator;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements Originator {
	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables =  new ArrayList<>();
	private List<Enemy> enemies;

	private Player player;

	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private ScoreTimeKeeper scoreTimeKeeper;
	private MementoManager mementoManager;


	private int timer = 45;

	public GameEngine(String config){
		scoreTimeKeeper = new ScoreTimeKeeper();
		mementoManager = new MementoManager(this, scoreTimeKeeper);

		enemies = new ArrayList<>();

		// Read the config here
		ConfigReader.parse(config);

		// Get game width and height
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);



		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}


		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			enemy.attachObserver(scoreTimeKeeper);
			gameObjects.add(enemy);
			renderables.add(enemy);
			enemies.add(enemy);
		}
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		timer+=1;

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){
				}
				else{
					// Copying same style of ensuring renderable is of "x" type.
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						if(renderableA.getRenderableObjectName().equals("PlayerProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile")){
							((EnemyProjectile)renderableB).notifyObservers();
						}
						if(renderableB.getRenderableObjectName().equals("PlayerProjectile") && renderableA.getRenderableObjectName().equals("EnemyProjectile")){
							((EnemyProjectile)renderableA).notifyObservers();
						}
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
					}
				}
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	/* ========== Additional methods implemented in GameEngine for new features ========== */



	/**
	 * Saved Pressed calls on Memento Manager to execute saving states.
	 */
	public void savePressed(){
		mementoManager.saved();
	}

	/**
	 * Undo Pressed calls on Memento Manager to execute restoring to saved state IF not null and saved state exists.
	 */
	public void undoPressed(){
		mementoManager.undo();
	}

	/**
	 * Maintains reference of observer created in this class and attaches it to subjects when called.
	 * @param subject: Subject to add existing single instance of ScoreTimeKeeper to.
	 */
	public void toBeAttached(Subject subject){
		subject.attachObserver(scoreTimeKeeper);
	}

	/**
	 * Returns instance of ScoreTimeKeeper.
	 * @return ScoreTimeKeeper
	 */
	public ScoreTimeKeeper getScoreKeeper(){
		return scoreTimeKeeper;
	}

	@Override
	public Memento save() {
		return new EnemyMemento(
				this,
				copyEnemies()
		);
	}

	/**
	 * Performs a deep copy of enemies.
	 * @return List of Enemy
	 */
	public List<Enemy> copyEnemies(){
		List<Enemy> copiedList = new ArrayList<>();
		for(Enemy e:enemies){
			copiedList.add(e.deepCopy());
		}
		return copiedList;
	}

	/**
	 * Restores enemies and their positions on screen.
	 * @param enemies: List of saved enemies to redrawn on the screen.
	 */
	public void setEnemies(List<Enemy> enemies) {
		List<GameObject> toRemove = new ArrayList<>();

		for (Renderable ren : renderables) {
			if (ren.getRenderableObjectName().equals("Enemy")) {
				toRemove.add((GameObject) ren);
				((Enemy) ren).setLives(0);
			}
			if (ren.getRenderableObjectName().equals("EnemyProjectile")) {
                toRemove.add((GameObject) ren);
				ren.takeDamage(1);
			}
		}
		pendingToRemoveGameObject.addAll(toRemove);
		gameObjects.addAll(enemies);
		renderables.addAll(enemies);
	}

	/**
	 * Removes instances of fast aliens and increments the player's score by 4 points for each fast alien.
	 */
	public void removeFastAlien(){
		List<Enemy> toRemove = new ArrayList<>();
		int total = 0;
		for(Enemy e:enemies){
			if(e.getProjectileStrategy().getProjectileStrategyName().equals("FastProjectileStrategy")){
				e.setLives(0);
				toRemove.add(e);
				total += Points.FAST_ALIEN.getPoints();
			}
		}
		pendingToRemoveGameObject.removeAll(toRemove);
		pendingToRemoveRenderable.removeAll(toRemove);
		enemies.removeAll(toRemove);
		scoreTimeKeeper.notify(total);
	}

	/**
	 * Removes instances of slow aliens and increments the player's score by 3 points for each slow alien.
	 */
	public void removeSlowAlien(){
		List<Enemy> toRemove = new ArrayList<>();
		int total = 0;
		for(Enemy e:enemies){
			if(e.getProjectileStrategy().getProjectileStrategyName().equals("SlowProjectileStrategy")){
				e.setLives(0);
				toRemove.add(e);
				total += Points.SLOW_ALIEN.getPoints();
			}
		}
		pendingToRemoveGameObject.removeAll(toRemove);
		pendingToRemoveRenderable.removeAll(toRemove);
		enemies.removeAll(toRemove);
		scoreTimeKeeper.notify(total);
	}

	/**
	 * Removes instances of fast projectiles and increments the player's score by 2 points for each fast projectile.
	 */
	public void removeFastProjectile() {
		List<Projectile> toRemove = new ArrayList<>();
		int total = 0;
		for (Renderable ren : renderables) {
			if (ren.getRenderableObjectName().equals("EnemyProjectile") && ((EnemyProjectile)ren).getProjectileStrategyName().equals("FastProjectileStrategy") && ren.isAlive()) {
				ren.takeDamage(1);
				toRemove.add((Projectile) ren);
				total += Points.FAST_PROJECTILE.getPoints();
			}
		}
		pendingToRemoveGameObject.removeAll(toRemove);
		pendingToRemoveRenderable.removeAll(toRemove);
		scoreTimeKeeper.notify(total);
	}

	/**
	 * Removes instances of slow projectiles and increments the player's score by 1 points for each slow projectile.
	 */
	public void removeSlowProjectile() {
		List<Projectile> toRemove = new ArrayList<>();
		int total = 0;
		for (Renderable ren : renderables) {
			if (ren.getRenderableObjectName().equals("EnemyProjectile") && ((EnemyProjectile)ren).getProjectileStrategyName().equals("SlowProjectileStrategy") && ren.isAlive()) {
				ren.takeDamage(1);
				toRemove.add((Projectile) ren);
				total += Points.SLOW_PROJECTILE.getPoints();
			}
		}
		pendingToRemoveGameObject.removeAll(toRemove);
		pendingToRemoveRenderable.removeAll(toRemove);
		scoreTimeKeeper.notify(total);
	}

}

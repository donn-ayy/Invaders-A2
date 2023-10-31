package invaders.undo;

import invaders.engine.GameEngine;
import invaders.gameobject.Enemy;
import java.util.List;

/**
 * Memento class for Enemy state.
 */
public class EnemyMemento implements Memento {
    GameEngine model;
    List<Enemy> enemies;

    /**
     * Constructor for EnemyMemento.
     * @param model: reference to GameEngine object.
     * @param enemies: deep copy of Enemies obtained from GameEngine.
     */
    public EnemyMemento(GameEngine model, List<Enemy> enemies){
        this.model = model;
        this.enemies =enemies;
    }

    @Override
    public void undo() {
       model.setEnemies(enemies);
    }

}

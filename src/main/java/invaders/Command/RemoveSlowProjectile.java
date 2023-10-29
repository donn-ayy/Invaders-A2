package invaders.Command;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectile;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.rendering.Renderable;
import invaders.status.ScoreTimeKeeper;

import java.util.List;

public class RemoveSlowProjectile extends Command{

    public RemoveSlowProjectile(GameEngine model, ScoreTimeKeeper keeper) {
        super(model, keeper);
    }

    @Override
    public void execute() {
        List<GameObject> gameObjects = model.getGameObjects();
        List<Renderable> renderables = model.getRenderables();
        int total = 0;
        for(GameObject g:gameObjects){
            if(g instanceof Enemy){
                ((Enemy)g).setLives(0);
            }
            if(g instanceof EnemyProjectile){
                ((EnemyProjectile)g).takeDamage(1);
            }
        }
        for(Renderable ren:renderables){
            if(ren instanceof Enemy){
                ((Enemy)ren).setLives(0);
            }
            if(ren instanceof EnemyProjectile){
                ren.takeDamage(1);
            }
        }
        gameObjects.addAll(enemies);
        renderables.addAll(enemies);
        keeper.notify(3);
    }

}

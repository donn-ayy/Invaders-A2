package invaders.Command;

import invaders.engine.GameEngine;
import invaders.gameobject.Enemy;
import invaders.status.ScoreTimeKeeper;

import java.util.List;

public class RemoveSlowAlien extends Command{

    public RemoveSlowAlien(GameEngine model, ScoreTimeKeeper keeper) {
        super(model, keeper);
    }

    @Override
    public void execute() {
        List<Enemy> enemies = model.getEnemies();
        int total = 0;
        for(Enemy e:enemies){
            if(e.getProjectileStrategy().getProjectileStrategyName().equals("SlowProjectileStrategy")){
                e.setLives(0);
                total += 3;
            }
        }
        keeper.notify(3);
    }

}

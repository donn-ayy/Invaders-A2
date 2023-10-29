package invaders.Command;

import invaders.engine.GameEngine;
import invaders.gameobject.Enemy;
import invaders.status.ScoreTimeKeeper;

import java.util.List;

public class RemoveFastAlien extends Command{

    public RemoveFastAlien(GameEngine model, ScoreTimeKeeper keeper) {
        super(model, keeper);
    }

    @Override
    public void execute() {
        List<Enemy> enemies = model.getEnemies();
        int total = 0;
        for(Enemy e:enemies){
            if(e.getProjectileStrategy().getProjectileStrategyName().equals("FastProjectileStrategy")){
                e.setLives(0);
                total += 3;
            }
        }
        keeper.notify(4);
    }

}

package invaders.undo;

import invaders.engine.GameEngine;
import invaders.gameobject.Enemy;
import java.util.List;

public class GameEngineMemento implements Memento {
    GameEngine model;
    List<Enemy> enemies;

    public GameEngineMemento(GameEngine model, List<Enemy> enemies){
        this.model = model;
        this.enemies =enemies;
    }

    @Override
    public void undo() {
        int i = 1;
        System.out.println("No. --- X --- Y ---");
        for(Enemy e:enemies){
            System.out.printf("%d --- %.2f --- %.2f", i, e.getPosition().getX(), e.getPosition().getY());
        }
       model.setEnemies(enemies);
    }
}

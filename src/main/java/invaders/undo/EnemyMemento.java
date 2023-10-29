package invaders.undo;

import invaders.engine.GameEngine;
import invaders.gameobject.Enemy;
import java.util.List;

public class EnemyMemento implements Memento {
    GameEngine model;
    List<Enemy> enemies;

    public EnemyMemento(GameEngine model, List<Enemy> enemies){
        this.model = model;
        this.enemies =enemies;
    }

    @Override
    public void undo() {
       model.setEnemies(enemies);
    }

}

package invaders.undo;

import invaders.engine.GameEngine;
import invaders.status.ScoreTimeKeeper;

public class MementoManager {
    Originator model;
    Originator keeper;
    Memento enemyState;
    Memento keeperState;
    boolean saved;

    public MementoManager(GameEngine model, ScoreTimeKeeper keeper){
        this.model = model;
        this.keeper = keeper;
        saved = false;
    }

    public void saved(){
        enemyState = model.save();
        keeperState = keeper.save();
        saved = true;
    }

    public void undo(){
        if(saved && enemyState != null && keeperState != null) {
            enemyState.undo();
            keeperState.undo();
            saved = false;
        }
    }

}

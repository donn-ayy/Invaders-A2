package invaders.Command;

import invaders.engine.GameEngine;
import invaders.status.ScoreTimeKeeper;

public abstract class Command {

    protected GameEngine model;
    protected ScoreTimeKeeper keeper;
    public Command(GameEngine model, ScoreTimeKeeper keeper){
        this.model = model;
        this.keeper = keeper;
    }

    public abstract void execute();

}

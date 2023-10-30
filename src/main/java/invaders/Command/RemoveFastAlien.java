package invaders.Command;

import invaders.engine.GameEngine;

public class RemoveFastAlien implements Command{

    private GameEngine model;
    public RemoveFastAlien(GameEngine model){
        this.model = model;
    }

    @Override
    public void execute() {
        model.removeFastAlien();
    }

}

package invaders.Command;

import invaders.engine.GameEngine;

/**
 *  Remove Fast Alien is a concrete command class that calls on GameEngine method to handle the logic of
 *  removing fast alien.
 */
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

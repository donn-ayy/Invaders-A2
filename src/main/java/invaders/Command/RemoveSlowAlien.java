package invaders.Command;

import invaders.engine.GameEngine;
/**
 *  Remove Slow Alien is a concrete command class that calls on GameEngine method to handle the logic of
 *  removing slow alien.
 */
public class RemoveSlowAlien implements Command{
    private GameEngine model;

    public RemoveSlowAlien(GameEngine model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.removeSlowAlien();
    }

}

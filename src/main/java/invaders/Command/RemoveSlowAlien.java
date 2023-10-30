package invaders.Command;

import invaders.engine.GameEngine;

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

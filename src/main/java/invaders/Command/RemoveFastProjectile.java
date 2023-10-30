package invaders.Command;

import invaders.engine.GameEngine;

public class RemoveFastProjectile implements Command{
    private GameEngine model;

    public RemoveFastProjectile(GameEngine model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.removeFastProjectile();
    }

}

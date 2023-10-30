package invaders.Command;

import invaders.engine.GameEngine;

public class RemoveSlowProjectile implements Command{
    private GameEngine model;

    public RemoveSlowProjectile(GameEngine model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.removeSlowProjectile();
    }
}

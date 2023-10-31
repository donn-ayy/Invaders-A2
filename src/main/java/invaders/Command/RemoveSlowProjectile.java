package invaders.Command;

import invaders.engine.GameEngine;
/**
 *  Remove Fast Projectile is a concrete command class that calls on GameEngine method to handle the logic of
 *  removing fast projectile.
 */
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

package invaders.strategy;

import invaders.factory.Projectile;
import invaders.physics.Vector2D;

public interface ProjectileStrategy {
   public void update(Projectile p);
   // since using instance of is a 'bad design smell' -- enforce classes to produce a string name as in renderable
   public String getProjectileStrategyName();
}

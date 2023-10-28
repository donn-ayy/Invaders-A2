package invaders.undo;

import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.gameobject.Enemy;
import invaders.physics.Vector2D;

import java.util.ArrayList;

public class EnemyMemento implements Memento {
    Enemy enemy;
    Vector2D position;
    ProjectileFactory factory;
    ArrayList<Projectile> toDelete;
    ArrayList<Projectile> projectiles;
    int xVel;

    public EnemyMemento(Enemy enemy, Vector2D position, ArrayList<Projectile> projectiles

        ){
        this.enemy = enemy;
        this.position = position;
        this.projectiles = projectiles;
    }

    @Override
    public void undo() {
        enemy.setPosition(position);
        enemy.setProjectiles(projectiles);
    }
}

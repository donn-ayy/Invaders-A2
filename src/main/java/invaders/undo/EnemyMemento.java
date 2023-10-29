package invaders.undo;

import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.gameobject.Enemy;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class EnemyMemento implements Memento {
    Enemy enemy;
    Vector2D position;
    ProjectileFactory factory;
    ArrayList<Projectile> toDelete;
    ArrayList<Projectile> projectiles;
    Image image;
    int xVel;

    public EnemyMemento(Enemy enemy, Vector2D position, ArrayList<Projectile> projectiles,
        Image image){
        this.enemy = enemy;
        this.position = position;
        this.projectiles = projectiles;
        this.image = image;
    }

    @Override
    public void undo() {
        enemy.setImage(image);
        enemy.setPosition(position);
        enemy.setProjectiles(projectiles);
    }
}

package invaders.gameobject;

import invaders.Points;
import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectile;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.status.Observer;
import invaders.status.Subject;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.util.List;

import java.util.ArrayList;
import java.util.Random;

public class Enemy implements GameObject, Renderable, Subject {
    private Vector2D position;
    private int lives = 1;
    private Image image;
    private int xVel = -1;

    private ArrayList<Projectile> enemyProjectile;
    private ArrayList<Projectile> pendingToDeleteEnemyProjectile;
    private ProjectileStrategy projectileStrategy;
    private ProjectileFactory projectileFactory;
    private Image projectileImage;
    private Random random = new Random();
    private ArrayList<Observer> observers;

    public Enemy(Vector2D position) {
        this.position = position;
        this.projectileFactory = new EnemyProjectileFactory();
        this.enemyProjectile = new ArrayList<>();
        this.pendingToDeleteEnemyProjectile = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public Enemy(Vector2D position, Image image, ProjectileFactory projectileFactory, List<Projectile> enemyProjectile,
                 List<Projectile> pending, List<Observer> observers, ProjectileStrategy strategy, Image projectileImage, int xVel
        ){
        this.position = position;
        this.image = image;
        this.projectileFactory = projectileFactory;
        this.enemyProjectile = (ArrayList<Projectile>) enemyProjectile;
        this.pendingToDeleteEnemyProjectile = (ArrayList<Projectile>) pending;
        this.observers = (ArrayList<Observer>) observers;
        this.projectileStrategy = strategy;
        this.projectileImage = projectileImage;
        this.xVel = xVel;
    }

    @Override
    public void start() {
    }

    @Override
    public void update(GameEngine engine) {
        if (enemyProjectile.size() < 3) {
            if (this.isAlive() && random.nextInt(120) == 20) {
                Projectile p = projectileFactory.createProjectile(new Vector2D(position.getX() + this.image.getWidth() / 2, position.getY() + image.getHeight() + 2), projectileStrategy, projectileImage);
                enemyProjectile.add(p);
                engine.toBeAttached((Subject) p);
                engine.getPendingToAddGameObject().add(p);
                engine.getPendingToAddRenderable().add(p);
            }
        } else {
            pendingToDeleteEnemyProjectile.clear();
            for (Projectile p : enemyProjectile) {
                if (!p.isAlive()) {
                    engine.getPendingToRemoveGameObject().add(p);
                    engine.getPendingToRemoveRenderable().add(p);
                    pendingToDeleteEnemyProjectile.add(p);
                }
            }

            for (Projectile p : pendingToDeleteEnemyProjectile) {
                enemyProjectile.remove(p);
            }
        }

        if (this.position.getX() <= this.image.getWidth() || this.position.getX() >= (engine.getGameWidth() - this.image.getWidth() - 1)) {
            this.position.setY(this.position.getY() + 25);
            xVel *= -1;
        }

        this.position.setX(this.position.getX() + xVel);

        if ((this.position.getY() + this.image.getHeight()) >= engine.getPlayer().getPosition().getY()) {
            engine.getPlayer().takeDamage(Integer.MAX_VALUE);
        }

    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return this.image.getWidth();
    }

    @Override
    public double getHeight() {
        return this.image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProjectileImage(Image projectileImage) {
        this.projectileImage = projectileImage;
    }

    @Override
    public void takeDamage(double amount) {
        notifyObservers();
        this.lives -= 1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public String getRenderableObjectName() {
        return "Enemy";
    }

    @Override
    public boolean isAlive() {
        return this.lives > 0;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    @Override
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            if (projectileStrategy.getProjectileStrategyName().equals("FastProjectileStrategy")) {
                observer.notify(Points.FAST_ALIEN.getPoints());
            } else if (projectileStrategy.getProjectileStrategyName().equals("SlowProjectileStrategy")) {
                observer.notify(Points.SLOW_ALIEN.getPoints());
            } else {
                observer.notify(Points.DEFAULT.getPoints());
            }
        }
    }

    public ProjectileStrategy getProjectileStrategy() {
        return projectileStrategy;
    }

    public Enemy deepCopy() {
        Vector2D positionCopy = new Vector2D(position.getX(), position.getY());

        ArrayList<Projectile> enemyProjectileCopy = new ArrayList<>();
        for (Projectile p : enemyProjectile) {
            enemyProjectileCopy.add(((EnemyProjectile)p).deepCopy());
        }

        ArrayList<Projectile> pendingToDeleteCopy = new ArrayList<>();
        for (Projectile p : pendingToDeleteEnemyProjectile) {
            pendingToDeleteCopy.add(((EnemyProjectile)p).deepCopy());
        }

        ArrayList<Observer> observersCopy = new ArrayList<>(observers);

        ProjectileStrategy strategyCopy = projectileStrategy;

        return new Enemy(
                positionCopy,
                this.image,
                this.projectileFactory,
                enemyProjectileCopy,
                pendingToDeleteCopy,
                observersCopy,
                strategyCopy,
                this.projectileImage,
                this.xVel
        );
    }
}


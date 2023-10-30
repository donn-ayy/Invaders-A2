package invaders.factory;

import invaders.Points;
import invaders.engine.GameEngine;
import invaders.gameobject.GameObject;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.status.Observer;
import invaders.status.Subject;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnemyProjectile extends Projectile implements Subject {
    private ProjectileStrategy strategy;
    private ArrayList<Observer> observers;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
        this.observers = new ArrayList<>();
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            // flying off the screen
            this.takeDamage(1);
        }
    }
    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    @Override
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer:observers){
            if(strategy.getProjectileStrategyName().equals("FastProjectileStrategy")){
                observer.notify(Points.FAST_PROJECTILE.getPoints());
            } else if (strategy.getProjectileStrategyName().equals("SlowProjectileStrategy")){
                observer.notify(Points.SLOW_PROJECTILE.getPoints());
            } else{
                observer.notify(Points.DEFAULT.getPoints());
            }
        }
    }

    public EnemyProjectile deepCopy() {
        Vector2D positionCopy = new Vector2D(this.getPosition().getX(), this.getPosition().getY());
        ProjectileStrategy strategyCopy = this.strategy;
        Image imageCopy = this.getImage();
        EnemyProjectile copy = new EnemyProjectile(positionCopy, strategyCopy, imageCopy);
        for (Observer observer : this.observers) {
            copy.attachObserver(observer);
        }
        return copy;
    }

    public String getProjectileStrategyName() {
        return strategy.getProjectileStrategyName();
    }
}

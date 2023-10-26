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
            // when projectile gets hit - notify observer
            notifyObservers();
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
            if(strategy instanceof FastProjectileStrategy){
                observer.notify(Points.FAST_ALIEN.getPoints());
            } else if (strategy instanceof SlowProjectileStrategy){
                observer.notify(Points.SLOW_ALIEN.getPoints());
            } else{
                observer.notify(Points.DEFAULT.getPoints());
            }
        }
    }
}

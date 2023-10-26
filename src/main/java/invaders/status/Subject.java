package invaders.status;

import invaders.gameobject.GameObject;

public interface Subject {
    void attachObserver(Observer o);
    void notifyObservers();
}

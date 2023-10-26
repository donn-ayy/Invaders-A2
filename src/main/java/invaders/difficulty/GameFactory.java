package invaders.difficulty;

import invaders.engine.GameEngine;

public interface GameFactory {
    public GameEngine createGameEngine();
}

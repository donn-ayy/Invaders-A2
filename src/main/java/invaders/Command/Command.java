package invaders.Command;

import invaders.engine.GameEngine;
import invaders.status.ScoreTimeKeeper;

/**
 * Command interface for Command design pattern.
 * Contains the execute method for concrete classes to implement.
 */
public interface Command {
    public void execute();
}

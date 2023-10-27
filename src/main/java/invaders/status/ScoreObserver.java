package invaders.status;

import invaders.engine.GameWindow;

public class ScoreObserver implements Observer{
    int playerScore;
    GameWindow gameWindow;
    public ScoreObserver(){
        playerScore = 0;
    }
    @Override
    public void notify(int points) {
        this.playerScore += points;
        gameWindow.displayPlayerScore(playerScore);
    }

    public void setupGame(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }

}

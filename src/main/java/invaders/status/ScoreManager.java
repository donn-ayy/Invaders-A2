package invaders.status;

public class ScoreManager implements Observer{
    int playerScore;
    public ScoreManager(){
        playerScore = 0;
    }
    @Override
    public void notify(int points) {
        this.playerScore += points;
    }
    public int getPlayerScore(){
        return playerScore;
    }
}

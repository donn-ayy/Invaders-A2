package invaders.undo;


import invaders.status.ScoreTimeKeeper;

/**
 * Memento class for ScoreTime-Keeper.
 */
public class ScoreTimeMemento implements Memento {
    ScoreTimeKeeper scoreTimeKeeper;
    int elapsedTimeInSeconds;
    int minutes;
    int seconds;
    int playerScore;

    /**
     * Constructor for ScoreTimeMemento
     * @param scoreTimeKeeper: reference to ScoreTimeKeeper object.
     * @param elapsedTimeInSeconds: the elapsed time in seconds of when the state was saved from start.
     * @param minutes: the actual minutes of when the state was saved from start.
     * @param seconds: the actual seconds of when the state was saved from start.
     * @param playerScore: score of player of when the state was triggered to save.
     */
    public ScoreTimeMemento(ScoreTimeKeeper scoreTimeKeeper, int elapsedTimeInSeconds, int minutes, int seconds, int playerScore){
        this.scoreTimeKeeper = scoreTimeKeeper;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.minutes = minutes;
        this.seconds = seconds;
        this.playerScore = playerScore;
    }

    public void undo(){
        scoreTimeKeeper.setPlayerScore(playerScore);
        scoreTimeKeeper.setElapsedTimeInSeconds(elapsedTimeInSeconds);
        scoreTimeKeeper.setMinutes(minutes);
        scoreTimeKeeper.setSeconds(seconds);
    }
}

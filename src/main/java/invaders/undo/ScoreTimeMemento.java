package invaders.undo;


import invaders.status.ScoreTimeKeeper;

public class ScoreTimeMemento implements Memento {
    ScoreTimeKeeper scoreTimeKeeper;
    int elapsedTimeInSeconds;
    int minutes;
    int seconds;
    int playerScore;

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

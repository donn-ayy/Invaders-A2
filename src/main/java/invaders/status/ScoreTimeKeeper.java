package invaders.status;

import invaders.undo.Memento;
import invaders.undo.Originator;
import invaders.undo.ScoreTimeMemento;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * ScoreTime-Keeper class observes subjects and is updated by Subjects through notify method based on Observer pattern.
 */
public class ScoreTimeKeeper implements Observer, Originator {
    int playerScore;
    int elapsedTimeInSeconds;
    int minutes;
    int seconds;
    public ScoreTimeKeeper(){
        playerScore = 0;
        elapsedTimeInSeconds = 0;
        minutes = 0;
        seconds = 0;
        startTimer();
    }
    @Override
    public void notify(int points) {
        this.playerScore += points;
    }

    public int getPlayerScore(){
        return playerScore;
    }
    public int getMinutes(){
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setElapsedTimeInSeconds(int elapsedTimeInSeconds) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    private void startTimer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    elapsedTimeInSeconds++;
                    minutes = elapsedTimeInSeconds / 60;
                    seconds = elapsedTimeInSeconds % 60;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    @Override
    public Memento save() {
        return new ScoreTimeMemento(
this,
               elapsedTimeInSeconds,
               minutes,
               seconds,
               playerScore
        );
    }

}

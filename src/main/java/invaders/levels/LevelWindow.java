package invaders.levels;

import invaders.engine.GameEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LevelWindow {
    private static final Integer COUNTDOWN_TIME = 10;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = COUNTDOWN_TIME;
    private GameEngine model;
    public GameEngine selectGameDifficulty(){
        timeline = new Timeline();
        model = EasyLevel.getInstance();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // This makes the window modal
        stage.setTitle("Select Level");

        // Create and configure VBox
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);

        // Message Label
        Label message = new Label("Please Select Level");
        message.setStyle("-fx-font-size: 1.5em;");

        // Buttons
        Button easyButton = new Button("Easy");
        Button mediumButton = new Button("Medium");
        Button hardButton = new Button("Hard");

        // Set actions for buttons
        easyButton.setOnAction(event -> {
            model = EasyLevel.getInstance();
            System.out.println("EASY");
            timeline.stop();
            stage.close();
        });

        mediumButton.setOnAction(event -> {
            model = MediumLevel.getInstance();
            System.out.println("MEDIUM");
            timeline.stop();
            stage.close();
        });

        hardButton.setOnAction(event -> {
            model = HardLevel.getInstance();
            System.out.println("HARD");
            timeline.stop();
            stage.close();
        });

        // Countdown Timer
        timerLabel.setText(timeSeconds.toString());
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeSeconds--;
                    timerLabel.setText(timeSeconds.toString());
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        stage.close();
                    }
                })
        );
        timeline.playFromStart();

        vb.getChildren().addAll(message, easyButton, mediumButton, hardButton, timerLabel);

        Scene scene = new Scene(vb, 300, 250);
        stage.setScene(scene);
        stage.showAndWait();

        return model;
    }
}

package invaders;

import invaders.levels.EasyLevel;
import invaders.levels.GameLevel;
import invaders.levels.HardLevel;
import invaders.levels.MediumLevel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;


public class App extends Application {

    private static final Integer COUNTDOWN_TIME = 10; // 10 seconds for demo
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = COUNTDOWN_TIME;
    private GameLevel configLevel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Level");

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
            configLevel = EasyLevel.getInstance();
            closeWindow(primaryStage);
        });

        mediumButton.setOnAction(event -> {
            configLevel = MediumLevel.getInstance();
            closeWindow(primaryStage);
        });

        hardButton.setOnAction(event -> {
            configLevel = HardLevel.getInstance();
            closeWindow(primaryStage);
        });

        // Countdown Timer
        timerLabel.setText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeSeconds--;
                    timerLabel.setText(timeSeconds.toString());
                    if (timeSeconds <= 0) {
                        closeWindow(primaryStage);
                    }
                })
        );
        timeline.playFromStart();

        vb.getChildren().addAll(message, easyButton, mediumButton, hardButton, timerLabel);

        Scene scene = new Scene(vb, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void closeWindow(Stage stage) {
        if (timeline != null) {
            timeline.stop();
        } else{
            configLevel = EasyLevel.getInstance();
        }
        stage.close();

        GameEngine model = new GameEngine(configLevel.getConfig());
        GameWindow window = new GameWindow(model);
        window.run();

        Stage gameStage = new Stage();
        gameStage.setTitle("Space Invaders");
        gameStage.setScene(window.getScene());
        gameStage.show();

        window.run();
    }
}

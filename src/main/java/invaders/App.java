package invaders;

import invaders.levels.HardLevel;
import invaders.levels.GameLevel;
import invaders.levels.LevelWindow;
import invaders.status.ScoreObserver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Project level window and get user input for game difficulty
        LevelWindow levelWindow = new LevelWindow();

        // Returns an instance of GameEngine with JSON difficulty loaded
        GameEngine model = levelWindow.selectGameDifficulty();

        primaryStage.setTitle("Space Invaders");
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();
    }
}

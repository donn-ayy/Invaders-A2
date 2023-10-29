package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import invaders.status.ScoreTimeKeeper;
import invaders.undo.Memento;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;
    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private Label timerLabel;
    private Label playerLabel;
    private int minutes;
    private int seconds;
    private int bottomBoxSize;
    private int playerScore;
    private ScoreTimeKeeper keeper;
    private Label shortcutKeys;


    // private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model) {
        this.model = model;
        this.width =  model.getGameWidth();
        this.height = model.getGameHeight();
        this.keeper = model.getScoreKeeper();


        bottomBoxSize = 50;
        playerScore = 0;

        pane = new Pane();
        pane.setStyle("-fx-background-color: transparent");
        pane.setPrefSize(width, height);
        pane.setPrefHeight(height);

        this.background = new SpaceBackground(model, pane);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: black");
        vbox.getChildren().add(pane);

        HBox bottomBox = new HBox();
        bottomBox.setPrefHeight(bottomBoxSize);
        vbox.getChildren().add(bottomBox);

        scene = new Scene(vbox, width, height + bottomBoxSize);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        timerLabel = new Label("TIME: 00:00");
        timerLabel.setFont(new Font("Arial", 20));
        timerLabel.setTextFill(Color.LIMEGREEN);
        bottomBox.getChildren().add(timerLabel);

        playerLabel = new Label("PLAYER SCORE: 0");
        playerLabel.setFont(new Font("Arial", 20));
        playerLabel.setTextFill(Color.LIMEGREEN);

        shortcutKeys = new Label("s:save | u:undo | ");
        playerLabel.setFont(new Font("Arial", 20));
        playerLabel.setTextFill(Color.LIMEGREEN);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomBox.getChildren().add(spacer);
        bottomBox.getChildren().add(playerLabel);

        bottomBox.setSpacing(10);
        bottomBox.setPadding(new Insets(10));
    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }


    private void draw(){
        model.update();

        minutes = keeper.getMinutes();
        seconds = keeper.getSeconds();
        playerScore = keeper.getPlayerScore();

        playerLabel.setText(String.format("PLAYER SCORE: %d", playerScore));
        timerLabel.setText(String.format("TIME: %02d:%02d", minutes, seconds));


        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }



        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());

        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);


    }

	public Scene getScene() {
        return scene;
    }

}

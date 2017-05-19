package Quidditch.GameSystem;

import Quidditch.GameSystem.Resources.Resources;
import Quidditch.HarryPotterMain;
import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 17/05/2017.
 */


public class GameSystem  {

    private final double width = 1280, height = 720; // set screen size
    private Resources res = new Resources();
    private Pane root;
    private boolean gameOver = false;
    private boolean incrementOnce = true;
    private int score = 0;
    private int highScore = 0;
    private double FPS = 40;
    private int counter_30FPS = 0;
    private UserCharacter userCharacter;
    private TranslateTransition jump;
    private TranslateTransition fall;
    private RotateTransition rotator;
    private ArrayList<Tower> listOfTubes = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;

    public void startGame() {
        Stage primaryStage = HarryPotterMain.get_primaryStage();
        root = new Pane();
        root.setStyle("-fx-background-color: #C0392B");
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        initGame();
        root.setPrefSize(width, height);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP) {
                if (!gameOver)
                    jumpflappy();
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver)
                    fallFlappy();
                else
                    initializeGame();
            }

        });

    }


    private void updateCounters() {
        if (counter_30FPS % 4 == 0) {
            counter_30FPS = 1;
        }
        counter_30FPS++;
    }

    private void jumpflappy() {
        rotator.setDuration(Duration.millis(100));
        rotator.stop();
        rotator.play();
        jump.setByY(-50);
        jump.setCycleCount(1);
        userCharacter.jumping = true;
        fall.stop();
        jump.stop();
        jump.play();

    }

    private void fallFlappy() {
        rotator.setDuration(Duration.millis(100));
        rotator.stop();
        rotator.play();
        jump.setByY(50);
        jump.setCycleCount(1);
        userCharacter.jumping = true;
        fall.stop();
        jump.stop();
        jump.play();

    }

    private void checkCollisions() {
        Tower tube = listOfTubes.get(0);
        if (tube.getTranslateX() < 35 && incrementOnce) {
            score++;
            scoreLabel.setText("Score: " + score);
            incrementOnce = false;
        }
        Path towerPath = (Path) Shape.intersect(userCharacter.getBounds(), tube.getBounds());

        boolean intersection = !(towerPath.getElements().isEmpty());
        if (userCharacter.getBounds().getCenterY() + userCharacter.getBounds().getRadiusY() > height || userCharacter.getBounds().getCenterY() - userCharacter.getBounds().getRadiusY() < 0) {
            intersection = true;
        }
        if (intersection) {
            GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2);
            highScore = highScore < score ? score : highScore;
            gameOverLabel.setText("Tap to retry. Score: " + score + "\n\tHighScore: " + highScore);
            saveHighScore();
            root.getChildren().add(gameOverLabel);
            root.getChildren().get(1).setOpacity(0);
            gameOver = true;
            gameLoop.stop();
        }
    }

    private void initializeGame() {
        listOfTubes.clear();
        listOfClouds.clear();
        listOfLightnings.clear();
        root.getChildren().clear();
        userCharacter.getGraphics().setTranslateX(100);
        userCharacter.getGraphics().setTranslateY(150);
        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: 0");
        root.getChildren().addAll(userCharacter.getGraphics(), scoreLabel);

        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud();
            cloud.setX(Math.random() * width);
            cloud.setY(Math.random() * height * 0.5 + 0.1);
            listOfClouds.add(cloud);
            root.getChildren().add(cloud);
            Lightning lightning = new Lightning();
            lightning.setX(Math.random() * width);
            lightning.setY(Math.random() * height * 0.5 + 0.1);
            listOfLightnings.add(lightning);
            root.getChildren().add(lightning);
        }

        for (int i = 0; i < 5; i++) {
            SimpleDoubleProperty y = new SimpleDoubleProperty(0);
            y.set(root.getHeight() * Math.random() / 2.0);
            Tower tube = new Tower(res.towerImage, y, root,  false);
            tube.setTranslateX(i * (width / 4 + 10) + 400);
            listOfTubes.add(tube);
            root.getChildren().add(tube);
        }

        score = 0;
        incrementOnce = true;
        gameOver = false;
        userCharacter.jumping = false;
        fall.stop();
        fall.play();
        gameLoop.play();
    }

    private void initGame() {
        userCharacter = new UserCharacter(res.userImage);
        rotator = new RotateTransition(Duration.millis(500), userCharacter.getGraphics());
        jump = new TranslateTransition(Duration.millis(450), userCharacter.getGraphics());
        fall = new TranslateTransition(Duration.millis(5 * height), userCharacter.getGraphics());
        jump.setInterpolator(Interpolator.LINEAR);
        fall.setByY(height + 20);
        rotator.setCycleCount(1);
        userCharacter.getGraphics().setRotationAxis(Rotate.Z_AXIS);
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                updateCounters();
                checkCollisions();
                if (listOfTubes.get(0).getTranslateX() <= -width / 12.3) {
                    listOfTubes.remove(0);
                    SimpleDoubleProperty y = new SimpleDoubleProperty(0);
                    y.set(root.getHeight() * Math.random() / 2.0);
                    Tower tube;
                    if (Math.random() < 0.4) {
                        tube = new Tower(res.towerImage, y, root,  false);
                    } else if (Math.random() > 0.85) {
                        tube = new Tower(res.towerImage,y, root,  true);
                    } else {
                        tube = new Tower(res.towerImage, y, root,  false);
                    }
                    tube.setTranslateX(listOfTubes.get(listOfTubes.size() - 1).getTranslateX() + (width / 4 + 10));
                    listOfTubes.add(tube);
                    incrementOnce = true;
                    root.getChildren().remove(7);
                    root.getChildren().add(tube);
                }
                for (int i = 0; i < listOfTubes.size(); i++) {
                    if (listOfClouds.get(i).getX() < -listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX()) {
                        listOfClouds.get(i).setX(listOfClouds.get(i).getX() + width + listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX());
                    }
                    if (listOfLightnings.get(i).getX() < -listOfLightnings.get(i).getImage().getWidth() * listOfLightnings.get(i).getScaleY()) {
                        listOfLightnings.get(i).setX(listOfLightnings.get(i).getX() + width + listOfLightnings.get(i).getImage().getWidth() * listOfLightnings.get(i).getScaleX());
                    }

                    listOfClouds.get(i).setX(listOfClouds.get(i).getX() - 1);
                    listOfLightnings.get(i).setX(listOfLightnings.get(i).getX() -1);

                    listOfTubes.get(i).setTranslateX(listOfTubes.get(i).getTranslateX() - 2);
                }
            }
        }));
        gameLoop.setCycleCount(-1);
        initializeGame();
        loadHighScore();
    }

    private void loadHighScore() {

    }

    private void saveHighScore() {

    }
}

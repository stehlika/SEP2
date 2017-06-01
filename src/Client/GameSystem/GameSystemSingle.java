package Client.GameSystem;

import Client.Controller.*;
import Client.GameSystem.Resources.Resources;
import Client.HarryPotterMain;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by adamstehlik on 17/05/2017.
 */


public class GameSystemSingle {

    private final double width = 1280, height = 720; // set screen size
    private Resources res = new Resources();
    private Pane root;
    private boolean gameOver = false;
    private boolean incrementOnce = true;
    private int score = 0;
    private int highScore = 0;
    private double FPS = 70;
    private int counter_30FPS = 0;

    private UserCharacter userCharacter;
    private Dementor dementor;

    private TranslateTransition userUp;
    private TranslateTransition userDown;

    private StartScreen startScreen = new StartScreen(400, 300);

    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;
    private Player _player;
    private Date startTime;

    private static GameSystemSingle instance = null;
    private GameSystemSingle()  {
    }

    public static GameSystemSingle getInstance() {
        if(instance == null)
                instance = new GameSystemSingle();
        return instance;
    }

    /**
     *  Method that initialises playing screen and checks for user's keyboard input.
     *  @KeyCode.UP is calling function upMovement.
     *  @KeyCode.DOWN is calling function downMovement.
     */
    public void startGame(Player player) {
        _player = player;
        startTime = new Date();

        Stage primaryStage = HarryPotterMain.get_primaryStage();
        root = new Pane();
        root.setStyle("-fx-background-color: #ACCAD1");
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        initGame(_player);
        root.setPrefSize(width, height);

        scene.setOnKeyPressed(event -> {
            countScore();

            if (event.getCode() == KeyCode.UP) {
                if (!gameOver) {
                    upMovement();
                } else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver) {
                    downMovement();
                } else
                    initializeGame();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                if (gameOver) {
                    showProfile();
                }
            }
        });
    }


    /**
     * This function is taking care of FPS for user movement
     */
    private void updateCounters() {
        if (counter_30FPS % 4 == 0) {
            counter_30FPS = 1;
        }
        counter_30FPS++;
    }

    /**
     * Method that moves user GUI object up 50px and animates the movement.
     */
    private void upMovement() {
        userUp.setByY(-50);
        userUp.setCycleCount(1);
        userDown.stop();
        userUp.stop();
        userUp.play();
    }


    /**
     * Method is moving down user's GUI object by 50px and animates.
     */
    private void downMovement() {
        userUp.setByY(50);
        userUp.setCycleCount(1);
        userDown.stop();
        userUp.stop();
        userUp.play();
    }

    private void checkCollisions() {
        Cloud cloud = listOfClouds.get(0);
        Tower tower = listOfTowers.get(0);
        Lightning lightning = listOfLightnings.get(0);
        if (tower.getTranslateX() < 35 && incrementOnce) {
            score++;
            scoreLabel.setText("Score: " + score);
            incrementOnce = false;
            System.out.println("Score: " + score);
        }

        Path cloudPath = (Path) Shape.intersect(userCharacter.getBounds(), cloud.getBounds());
        Path towerPath = (Path) Shape.intersect(userCharacter.getBounds(), tower.getBounds());
        Path lightningPath = (Path) Shape.intersect(userCharacter.getBounds(), lightning.getBounds());

        boolean cloudIntersection = !(cloudPath.getElements().isEmpty());
        boolean towerIntersection = !(towerPath.getElements().isEmpty());
        boolean lightningIntersection = !(lightningPath.getElements().isEmpty());
        boolean dementorApproached = false;

        if (dementor.getTranslateX() + dementor.getBounds().getWidth() >= 200) {
            dementorApproached =  true;
        }

        if (userCharacter.getBounds().getCenterY() + userCharacter.getBounds().getRadiusY() > height || userCharacter.getBounds().getCenterY() - userCharacter.getBounds().getRadiusY() < 0) {
            towerIntersection = true;
        }

        if (towerIntersection) {
            gameLoop.setRate(0.05);
            dementor.setTranslateX(dementor.getTranslateX() + 10);
        } else if (cloudIntersection) {
            gameLoop.setRate(0.2);
            dementor.setTranslateX(dementor.getTranslateX() + 4);
        } else {
            gameLoop.setRate(1.0);
        }

        if (lightningIntersection || dementorApproached) {
            ClientRMI.getInstance().userUpdate(new UserMovement(this._player, "DIE"));

            GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2);
            highScore = highScore < score ? score : highScore;
            gameOverLabel.setText("Tap to retry. Score: " + score + "\n\tHighScore: " + highScore
                                    + "\nTo EXIT, press Esc.");
            root.getChildren().add(gameOverLabel);
            root.getChildren().get(1).setOpacity(0);
            gameOver = true;
            gameLoop.stop();
            saveHighScore();
        }
    }

    /**
     * Method that initializes game, clears lists of towers, clouds, Lightnings, clears view than draws users GUI object
     * sets X, Y position initializes score screen
     * Sets up the enviroment distance between towers etc
     *
     */
    private void initializeGame() {
        listOfTowers.clear();
        listOfClouds.clear();
        listOfLightnings.clear();
        root.getChildren().clear();

        userCharacter.getGraphics().setTranslateX(200);
        userCharacter.getGraphics().setTranslateY(150);

        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: " + score);
        root.getChildren().addAll(userCharacter.getGraphics(), scoreLabel);

        Level level = new Level();
        Image towerImg;
        Tower tower;
        Cloud cloud;
        Lightning bolt;

        for (int i = 0; i < level.getListOfTowersX().size(); i++) {
            if (i % 4 == 0) {
                towerImg = res.towerImageGryf;
            }
            else if (i%4==1) {
                towerImg = res.towerImageRave;
            }
            else if (i%4==2) {
                towerImg = res.towerImageHuff;
            } else {
                towerImg = res.towerImageSlyt;
            }
            if (Math.random() < 0.2) {
                tower = new Tower(towerImg, root, true);
            } else {
                tower = new Tower(towerImg, root, false);
            }

            tower.setTranslateX(level.getListOfTowersX().get(i));
            tower.setTranslateY(level.getListOfTowersY().get(i));
            listOfTowers.add(tower);
            root.getChildren().add(tower);
        }
        for (int i = 0; i < level.getListOfCloudsX().size(); i++) {

            cloud = new Cloud(res.cloudImage, root);
            cloud.setTranslateX(level.getListOfCloudsX().get(i));
            cloud.setTranslateY(level.getListOfCloudsY().get(i));
            listOfClouds.add(cloud);
            root.getChildren().add(cloud);
        }
        for (int i = 0; i < level.getListOfLightningsX().size(); i++) {

            bolt = new Lightning(res.lightningImage, root);
            bolt.setTranslateX(level.getListOfLightningsX().get(i));
            bolt.setTranslateY(level.getListOfLightningsY().get(i));
            listOfLightnings.add(bolt);
            root.getChildren().add(bolt);
        }

        dementor = new Dementor(res.deatheaterImage, root);
        dementor.setTranslateX(40.0);
        dementor.setTranslateY(100.0);
        root.getChildren().add(dementor);

        incrementOnce = true;
        gameOver = false;
        userCharacter.jumping = false;

        gameLoop.play();
    }

    private void initGame(Player player) {
        root.getChildren().add(startScreen);

        if (player.getFaculty().equals("gryffindor")) {
            userCharacter = new UserCharacter(res.userImageGryf);
        } else if (player.getFaculty().equals("ravenclaw")) {
            userCharacter = new UserCharacter(res.userImageRave);
        } else if (player.getFaculty().equals("hufflepuff")) {
            userCharacter = new UserCharacter(res.userImageHuff);
        } else if (player.getFaculty().equals("slytherin")) {
            userCharacter = new UserCharacter(res.userImageSlyt);
        } else {
            userCharacter = new UserCharacter(res.userImage);
        }

        userUp = new TranslateTransition(Duration.millis(450), userCharacter.getGraphics());
        userDown = new TranslateTransition(Duration.millis(5 * height), userCharacter.getGraphics());

        userUp.setInterpolator(Interpolator.LINEAR);
        userDown.setByY(height + 20);
        userCharacter.getGraphics().setRotationAxis(Rotate.Z_AXIS);

        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), e -> {
            updateCounters();
            checkCollisions();
            if (listOfClouds.get(0).getTranslateX() <= -width / 12.3) {
                listOfClouds.remove(0);
            }
            if (listOfTowers.get(0).getTranslateX() <= -width / 12.3) {
                listOfTowers.remove(0);
            }
            if (listOfLightnings.get(0).getTranslateX() <= -width / 12.3) {
                listOfLightnings.remove(0);
            }
            for (Cloud listOfCloud : listOfClouds) {
                listOfCloud.setTranslateX(listOfCloud.getTranslateX() - 2);
            }
            for (Tower listOfTower : listOfTowers) {
                listOfTower.setTranslateX(listOfTower.getTranslateX() - 2);
            }
            for (Lightning listOfLightning : listOfLightnings) {
                listOfLightning.setTranslateX(listOfLightning.getTranslateX() - 2);
            }
        }));

        gameLoop.setCycleCount(-1);
        initializeGame();
        loadHighScore();
    }

    private void loadHighScore() {
        try {
            ArrayList<Integer> highScores = ClientRMI.getInstance().getHighScoreForPlayer(_player.getNickname());
            if (highScores.size() > 0) {
                highScore = highScores.get(0);
            } else {
                highScore = -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            highScore = -1;
        }
    }

    private void showProfile() {
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../View/mainView.fxml"));
            root.getChildren().add(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {
        try {
            ClientRMI.getInstance().saveScore(_player.getNickname(),score);
            System.out.println("USPESNE UPDATNUTE v DB");
        } catch (IOException e) {
            e.printStackTrace();
            MasterController.showAlertView("We were unable to update scores in DB",500);
        }
    }

    private void countScore() {
        final Date currentTime = new Date();
        long timeDifference = -(startTime.getTime() - currentTime.getTime()) / 1000;
        score = Math.toIntExact(timeDifference);
        scoreLabel.setText("Score: " + score);
    }
}

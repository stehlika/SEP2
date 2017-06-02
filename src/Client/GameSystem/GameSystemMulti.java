package Client.GameSystem;

import Client.Controller.ClientRMI;
import Client.Controller.MasterController;
import Client.GameSystem.Resources.Resources;
import Client.HarryPotterMain;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class GameSystemMulti {

    private final double width = 1280, height = 720; // set screen size
    private Resources res = new Resources();
    private Pane root;
    private boolean gameOver = false;
    private boolean incrementOnce = true;
    private int score = 0;
    private int highScore = 0;
    private double FPS = 60;
    private int counter_30FPS = 0;

    private UserCharacter userCharacter1;
    private UserCharacter userCharacter2;

    private TranslateTransition user1Up;
    private TranslateTransition user1Down;

    private TranslateTransition user2Up;
    private TranslateTransition user2Down;

    private StartScreen startScreen = new StartScreen(400, 300);

    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;
    private Player _player;

    private static GameSystemMulti instance = null;
    private Date startTime;


    private Level level;

    /**
     * Private constructor to follow Singleton design pattern.
     */
    private GameSystemMulti()  {
        level = ClientRMI.getInstance().getLevel();
    }

    /**
     * Public static method returning instance of class for following Singleton design pattern.
     */
    public static GameSystemMulti getInstance() {
        if(instance == null)
                instance = new GameSystemMulti();
        return instance;
    }

    /**
     *  Method that initialises playing screen and checks for user's keyboard input.
     *  Keyboard arrow UP is calling function upMovement.
     *  Keyboard arrow DOWN is calling function downMovement.
     *  Keyboard arrow ESCAPE is returning user to profile view
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
            if(event.getCode() == KeyCode.UP) {
                if (!gameOver) {
                    upMovement();
                    ClientRMI.getInstance().userUpdate(new UserMovement(this._player, "UP"));
                }
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver) {
                    downMovement();
                    ClientRMI.getInstance().userUpdate(new UserMovement(this._player, "DOWN"));
                }
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                showProfile();
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
        user1Up.setByY(-50);
        user1Up.setCycleCount(1);
        userCharacter1.jumping = true;
        user1Down.stop();
        user1Up.stop();
        user1Up.play();
    }


    /**
     * Method is moving down user's GUI object by 50px and animates.
     */
    private void downMovement() {
        user1Up.setByY(50);
        user1Up.setCycleCount(1);
        userCharacter1.jumping = true;
        user1Down.stop();
        user1Up.stop();
        user1Up.play();
    }

    /**
     * Method for checking collisions between user, obstacles in game and deatheater.
     */
    private void checkCollisions() {
        Tower tower = listOfTowers.get(0);
        Cloud cloud = listOfClouds.get(0);
        Lightning lightning = listOfLightnings.get(0);
        if (tower.getTranslateX() < 35 && incrementOnce) {
            score++;
            scoreLabel.setText("Score: " + score);
            incrementOnce = false;
        }

        Path towerPath = (Path) Shape.intersect(userCharacter1.getBounds(), tower.getBounds());
        Path cloudPath = (Path) Shape.intersect(userCharacter1.getBounds(), cloud.getBounds());
        Path lightningPath = (Path) Shape.intersect(userCharacter1.getBounds(), lightning.getBounds());

        boolean cloudIntersection = !(cloudPath.getElements().isEmpty());
        boolean towerIntersection = !(towerPath.getElements().isEmpty());
        boolean lightningIntersection = !(lightningPath.getElements().isEmpty());

        if (userCharacter1.getBounds().getCenterY() + userCharacter1.getBounds().getRadiusY() > height || userCharacter1.getBounds().getCenterY() - userCharacter1.getBounds().getRadiusY() < 0) {
            towerIntersection = true;
        }

        if (towerIntersection || cloudIntersection || lightningIntersection) {
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
     * Sets up the entire game environment.
     *
     */
    private void initializeGame() {
        listOfTowers.clear();
        listOfClouds.clear();
        listOfLightnings.clear();
        root.getChildren().clear();
        score = 0;

        userCharacter1.getGraphics().setTranslateX(100);
        userCharacter1.getGraphics().setTranslateY(150);

        userCharacter2.getGraphics().setTranslateX(100);
        userCharacter2.getGraphics().setTranslateY(150);
        userCharacter2.getGraphics().setOpacity(0.5);

        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: " + score);

        root.getChildren().addAll(userCharacter1.getGraphics(), scoreLabel, userCharacter2.getGraphics());

        for (int i = 0; i < level.getListOfTowersX().size(); i++) {
            Tower tower;
            if (Math.random() < 0.4) {
                tower = new Tower(res.towerImage, root, false);
            } else if (Math.random() > 0.85) {
                tower = new Tower(res.towerImage, root, true);
            } else {
                tower = new Tower(res.towerImage, root, false);
            }
            tower.setTranslateX(level.getListOfTowersX().get(i));
            tower.setTranslateY(level.getListOfTowersY().get(i));
            listOfTowers.add(tower);
            root.getChildren().add(tower);
        }

        for (int i = 0; i < level.getListOfCloudsX().size(); i++) {
            Cloud cloud = new Cloud(res.cloudImage, root);
            cloud.setTranslateX(level.getListOfCloudsX().get(i));
            cloud.setTranslateY(level.getListOfCloudsY().get(i));
            listOfClouds.add(cloud);
            root.getChildren().add(cloud);
        }

        for (int i = 0; i < level.getListOfLightningsX().size(); i++) {
            Lightning bolt = new Lightning(res.lightningImage, root);
            bolt.setTranslateX(level.getListOfLightningsX().get(i));
            bolt.setTranslateY(level.getListOfLightningsY().get(i));
            listOfLightnings.add(bolt);
            root.getChildren().add(bolt);
        }

        incrementOnce = true;
        gameOver = false;

        gameLoop.play();
    }

    /**
     * Method setting up the right graphics for every player. Method is also preparing view for game-play,
     * loading scores etc.
     * @param player is necessary for assigning right GUI representation of user character
     */
    private void initGame(Player player) {
        root.getChildren().add(startScreen);

        if (player.getFaculty().equals("gryffindor")) {
            userCharacter1 = new UserCharacter(res.userImageGryf);
        } else if (player.getFaculty().equals("ravenclaw")) {
            userCharacter1 = new UserCharacter(res.userImageRave);
        } else if (player.getFaculty().equals("hufflepuff")) {
            userCharacter1 = new UserCharacter(res.userImageHuff);
        } else if (player.getFaculty().equals("slytherin")) {
            userCharacter1 = new UserCharacter(res.userImageSlyt);
        } else {
            userCharacter1 = new UserCharacter(res.userImage);
        }

        userCharacter2 = new UserCharacter(res.userImageGryf);


        user1Up = new TranslateTransition(Duration.millis(450), userCharacter1.getGraphics());
        user1Down = new TranslateTransition(Duration.millis(5 * height), userCharacter1.getGraphics());

        user2Up = new TranslateTransition(Duration.millis(450), userCharacter2.getGraphics());
        user2Down = new TranslateTransition(Duration.millis(5 * height), userCharacter2.getGraphics());

        user1Up.setInterpolator(Interpolator.LINEAR);
        user1Down.setByY(height + 20);
        userCharacter1.getGraphics().setRotationAxis(Rotate.Z_AXIS);

        user2Up.setInterpolator(Interpolator.LINEAR);
        user2Down.setByY(height + 20);
        userCharacter2.getGraphics().setRotationAxis(Rotate.Z_AXIS);

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
            for (int i = 0; i < listOfClouds.size(); i++) {
                listOfClouds.get(i).setTranslateX(listOfClouds.get(i).getTranslateX() - 2);
            }
            for (int i = 0; i < listOfLightnings.size(); i++) {
                listOfLightnings.get(i).setTranslateX(listOfLightnings.get(i).getTranslateX() - 2);
            }
            for (int i = 0; i < listOfTowers.size(); i++) {
                listOfTowers.get(i).setTranslateX(listOfTowers.get(i).getTranslateX() - 2);
            }
        }));
        gameLoop.setCycleCount(-1);
        initializeGame();
        loadHighScore();
    }

    /**
     * Method loading highest score for current player. To inform user of his/her most successful game.
     */
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

    /**
     * Method necessary for saving current score to server.
     */
    private void saveHighScore() {
        try {
            ClientRMI.getInstance().saveScore(_player.getNickname(),score);
        } catch (IOException e) {
            e.printStackTrace();
            MasterController.showAlertView("We were unable to update scores in DB",500);
        }
    }


    /**
     * Method is taking care of second players actions.
     * @param userMovement - for determining who did what
     */
    public void updateUser2(UserMovement userMovement) {
        if ((userMovement.getPlayer().equals(this._player))) {
            //ignore own requests
        } else {
            if (userMovement.getMovement().equals("UP")) {
                user2Up.setByY(-50);
                user2Up.setCycleCount(1);
                userCharacter2.jumping = true;
                user2Down.stop();
                user2Up.stop();
                user2Up.play();

            } else if (userMovement.getMovement().equals("DOWN")) {
                user2Up.setByY(50);
                user2Up.setCycleCount(1);
                userCharacter2.jumping = true;
                user2Down.stop();
                user2Up.stop();
                user2Up.play();

            } else if (userMovement.getMovement().equals("DIE")) {
//                The opponent died and frist user should be notified
                GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2);
            }
        }
    }

    /**
     * Method opening profile screen when user decides to close game and go back to views.
     */
    public void showProfile() {
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../View/mainView.fxml"));
            root.getChildren().add(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is counting score for each play and is updating label on game screen to inform user how is he/she doing.
     */
    private void countScore() {
        final Date currentTime = new Date();
        long timeDifference = -(startTime.getTime() - currentTime.getTime()) / 1000;
        score = Math.toIntExact(timeDifference);
        scoreLabel.setText("Score: " + score);
    }

}

package Client.Quidditch.GameSystem;

import Client.Quidditch.Controller.ClientRMI;
import Client.Quidditch.GameSystem.Resources.Resources;
import Client.Quidditch.HarryPotterMain;
import Server.Domain.Model.Player;
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

import java.io.IOException;
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
  //  private RotateTransition rotator;
    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;
    private Player _player;

    // rotator sluzi na to aby sa mohli charactery a towere naklapat tak ako ste to videli na zaciatku

    /**
     *  Method that initialises playing screen and checks for user's keyboard input.
     *  @KeyCode.UP is calling function upMovement.
     *  @KeyCode.DOWN is calling function downMovement.
     */
    public void startGame(Player player) {
        _player = player;

        Stage primaryStage = HarryPotterMain.get_primaryStage();
        root = new Pane();
        root.setStyle("-fx-background-color: #C0392B");
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        initGame(_player);
        root.setPrefSize(width, height);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP) {
                if (!gameOver)
                    upMovement();
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver)
                    downMovement();
                else
                    initializeGame();
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
        jump.setByY(-50);
        jump.setCycleCount(1);
        userCharacter.jumping = true;
        fall.stop();
        jump.stop();
        jump.play();

    }


    /**
     * Method is moving down user's GUI object by 50px and animates.
     */
    private void downMovement() {
       // rotator.setDuration(Duration.millis(100));
       // rotator.stop();
       // rotator.play();
        jump.setByY(50);
        jump.setCycleCount(1);
        userCharacter.jumping = true;
        fall.stop();
        jump.stop();
        jump.play();

    }

    private void checkCollisions() {
        //toto pocita kolko towerov user uz obisiel
        Tower tower = listOfTowers.get(0);
        Cloud cloud = listOfClouds.get(0);
        Lightning lightning = listOfLightnings.get(0);
        if (tower.getTranslateX() < 35 && incrementOnce) {
            score++;
            scoreLabel.setText("Score: " + score);
            incrementOnce = false;
        }
        //Getovanie bounds zo shape pre zistenie prieniku suradnic toweru a usera.
        Path towerPath = (Path) Shape.intersect(userCharacter.getBounds(), tower.getBounds());
        Path cloudPath = (Path) Shape.intersect(userCharacter.getBounds(), cloud.getBounds());
        Path lightningPath = (Path) Shape.intersect(userCharacter.getBounds(), lightning.getBounds());

        //porovnava bound toweru a usera a meni premennu intersection podla toho
        boolean towerIntersection = !(towerPath.getElements().isEmpty());
        boolean lightningIntersection = !(lightningPath.getElements().isEmpty());
        boolean cloudIntersection = !(cloudPath.getElements().isEmpty());

        if (userCharacter.getBounds().getCenterY() + userCharacter.getBounds().getRadiusY() > height || userCharacter.getBounds().getCenterY() - userCharacter.getBounds().getRadiusY() < 0) {
            towerIntersection = true;
        }

        // momentalne je to nastavene tak ze sa da game over obrazovka ked sa pretne tower s userom
        // TODO treba spravit viacero verzii intersection pre tower-user, user-cloud, user-lightning, user-dementor
        if (towerIntersection) {

            GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2);
            highScore = highScore < score ? score : highScore;
            gameOverLabel.setText("Tap to retry. Score: " + score + "\n\tHighScore: " + highScore);
          //  saveHighScore(); zatial nie je potreba pre fungovanie
            root.getChildren().add(gameOverLabel);
            root.getChildren().get(1).setOpacity(0);
            gameOver = true;
            gameLoop.stop();
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
        userCharacter.getGraphics().setTranslateX(100); // Posuva hraca na X osi do lava prava defualt 100
        userCharacter.getGraphics().setTranslateY(150);
        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: 0");
        root.getChildren().addAll(userCharacter.getGraphics(), scoreLabel);

        //  vytvara cloudy na screen na  random X,Y poziciu a prida do listu cloudov
        // vytvara lightning na screen na random X,Y poziciu a prida do listu lightning
        for (int i = 0; i < 5; i++) {
            //Cloud
            Cloud cloud = new Cloud(res.cloudImage, root);
           // cloud.setX(Math.random() * width);
           // cloud.setY(Math.random() * height * 0.5 + 0.1);
            listOfClouds.add(cloud);
            root.getChildren().add(cloud);
            root.getChildren().add(cloud.getBounds());

            //Lightning
            Lightning lightning = new Lightning(res.lightningImage, root);
//            double xValue = Math.random() * width;
//            double yValue = Math.random() * height * 0.5 + 0.1;
//            lightning.setX(xValue);
//            lightning.setY(yValue);
//            lightning.setPosition(xValue, yValue);
            listOfLightnings.add(lightning);
            root.getChildren().add(lightning);
            root.getChildren().add(lightning.getBounds());
        }

        // vytvara towery na random pozicie a pridava ich do listu towerov

        for (int i = 0; i < 5; i++) {
            SimpleDoubleProperty y = new SimpleDoubleProperty(0);
            y.set(root.getHeight() * Math.random() / 2.0);
            Tower tower = new Tower(res.towerImage, y, root,  false);
            tower.setTranslateX(i * (width / 4 + 10) + 400);
            listOfTowers.add(tower);
            root.getChildren().add(tower);
        }

        score = 0;
        incrementOnce = true;
        gameOver = false;
        userCharacter.jumping = false;
        fall.stop();
        fall.play();
        gameLoop.play();
    }

    private void initGame(Player player) {
        if (player.getFaculty().equals("gryffindor")) {
            userCharacter = new UserCharacter(res.userImageGryf);
        } else if (player.getFaculty().equals("ravenclaw")) {
            userCharacter = new UserCharacter(res.userImageRave);
        } else if (player.getFaculty().equals("hufflepuff")) {
            userCharacter = new UserCharacter(res.userImageHuff);
        } else if (player.getFaculty().equals("slytherin")) {
            userCharacter = new UserCharacter(res.userImageSlyt);
        } else {
            System.out.println("jajojaofhohso");
            userCharacter = new UserCharacter(res.userImage);
        }
      //  rotator = new RotateTransition(Duration.millis(500), userCharacter.getGraphics());
        jump = new TranslateTransition(Duration.millis(450), userCharacter.getGraphics());
        fall = new TranslateTransition(Duration.millis(5 * height), userCharacter.getGraphics());
        jump.setInterpolator(Interpolator.LINEAR);
        fall.setByY(height + 20);
       // rotator.setCycleCount(1);
        userCharacter.getGraphics().setRotationAxis(Rotate.Z_AXIS);
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                updateCounters();
                checkCollisions();
                if (listOfTowers.get(0).getTranslateX() <= -width / 12.3) {
                    listOfTowers.remove(0);
                    SimpleDoubleProperty y = new SimpleDoubleProperty(0);
                    y.set((root.getHeight() * Math.random() / 2.0));
                    Tower tube;
                    if (Math.random() < 0.4) {
                        tube = new Tower(res.towerImage, y, root,  false);
                    } else if (Math.random() > 0.85) {
                        tube = new Tower(res.towerImage,y, root,  true);
                    } else {
                        tube = new Tower(res.towerImage, y, root,  false);
                    }
                    tube.setTranslateX(listOfTowers.get(listOfTowers.size() - 1).getTranslateX() + (width / 4 + 10) + 0); // po prvom sete towerov + nieco nastavuje vzdialenost towerov
                    listOfTowers.add(tube);
                    incrementOnce = true;
                    root.getChildren().remove(7);
                    root.getChildren().add(tube);
                }
//                for (int i = 0; i < listOfTowers.size(); i++) {
//                    if (listOfClouds.get(i).getX() < -listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX()) {
//                        listOfClouds.get(i).setX(listOfClouds.get(i).getX() + width + listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX());
//                    }
//                    if (listOfLightnings.get(i).getTranslateX() < -listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleY()) {
//                        listOfLightnings.get(i).setTranslateX(listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX());
//                        listOfLightnings.get(i).setTranslateX((listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX()), 100);
//                    }
//
//                    listOfClouds.get(i).setX(listOfClouds.get(i).getX() - 1);
//                    listOfLightnings.get(i).setX(listOfLightnings.get(i).getX() -1);
//
//                    listOfTowers.get(i).setTranslateX(listOfTowers.get(i).getTranslateX() - 2);
//                }
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

    private void saveHighScore() {

    }
}

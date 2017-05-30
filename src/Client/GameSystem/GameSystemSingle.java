package Client.GameSystem;

import Client.Controller.ClientRMI;
import Client.GameSystem.Resources.Resources;
import Client.HarryPotterMain;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adamstehlik on 17/05/2017.
 */


public class GameSystemSingle extends TimerTask {

    private final double width = 1280, height = 720; // set screen size
    private Resources res = new Resources();
    private Pane root;
    private boolean gameOver = false;
    private boolean incrementOnce = true;
    private int score = 0;
    private int highScore = 0;
    private double FPS = 40;
    private int counter_30FPS = 0;
//    private Date startTime;
//    private Date endTime;
//    Timer timer = new Timer(false);

    private UserCharacter userCharacter;
    private Dementor dementor;

    private TranslateTransition userjump;
    private TranslateTransition userfall;

    private StartScreen startScreen = new StartScreen(400, 300);

    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;
    private Player _player;

    private static GameSystemSingle instance = null;
    private GameSystemSingle()  {
    }

    @Override
    public void run() {
        score += 3;
        scoreLabel.setText("Score: " + score);
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
        System.out.println("Player: " + _player);

        Stage primaryStage = HarryPotterMain.get_primaryStage();
        root = new Pane();
        root.setStyle("-fx-background-color: #ACCAD1");
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        initGame(_player);
        root.setPrefSize(width, height);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP) {
                if (!gameOver) {
                    upMovement();
                }
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver) {
                    downMovement();
                }
                else
                    initializeGame();
            } else {
                System.out.println("bullshit");
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
        userjump.setByY(-50);
        userjump.setCycleCount(1);
        userCharacter.jumping = true;
        userfall.stop();
        userjump.stop();
        userjump.play();

    }


    /**
     * Method is moving down user's GUI object by 50px and animates.
     */
    private void downMovement() {
        userjump.setByY(50);
        userjump.setCycleCount(1);
        userCharacter.jumping = true;
        userfall.stop();
        userjump.stop();
        userjump.play();

    }

    private void checkCollisions() {
        Cloud cloud = listOfClouds.get(0);
        Tower tower = listOfTowers.get(0);
        Lightning lightning = listOfLightnings.get(0);
//        if (tower.getTranslateX() < 35 && incrementOnce) {
//            score++;
//            scoreLabel.setText("Score: " + score);
//            incrementOnce = false;
//        }
        //Getovanie bounds zo shape pre zistenie prieniku suradnic toweru a usera.
        Path cloudPath = (Path) Shape.intersect(userCharacter.getBounds(), cloud.getBounds());
        Path towerPath = (Path) Shape.intersect(userCharacter.getBounds(), tower.getBounds());
        Path lightningPath = (Path) Shape.intersect(userCharacter.getBounds(), lightning.getBounds());

        //porovnava bound toweru a usera a meni premennu intersection podla toho
        boolean cloudIntersection = !(cloudPath.getElements().isEmpty());
        boolean towerIntersection = !(towerPath.getElements().isEmpty());
        boolean lightningIntersection = !(lightningPath.getElements().isEmpty());
        boolean dementorApproached = false;

        if (dementor.getTranslateX() + dementor.getBounds().getWidth() >= 200) {
            System.out.println("GAME OVER");
            dementorApproached =  true;
        }

        //toto su vrchne a spodne bounds (towerIntersection je len zavolane aby to vtedy spomalilo - asi lepsie zavolat gameover?)
        if (userCharacter.getBounds().getCenterY() + userCharacter.getBounds().getRadiusY() > height || userCharacter.getBounds().getCenterY() - userCharacter.getBounds().getRadiusY() < 0) {
            towerIntersection = true;
        }

        // momentalne je to nastavene tak ze sa da game over obrazovka ked sa pretne tower s userom
        // TODO treba spravit viacero verzii intersection pre tower-user, user-cloud, user-lightning, user-dementor
        if (towerIntersection) {
            gameLoop.setRate(0.05);
            dementor.setTranslateX(dementor.getTranslateX() + 10);
        } else if (cloudIntersection) {
            gameLoop.setRate(0.2);
            dementor.setTranslateX(dementor.getTranslateX() + 4);
        } else if (lightningIntersection) {
        } else {
            gameLoop.setRate(1.0);
//            System.out.println("Game resumed " + new Date());
        }

        if (lightningIntersection || dementorApproached) {
            ClientRMI.getInstance().userUpdate(new UserMovement(this._player, "DIE"));

            GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2);
            highScore = highScore < score ? score : highScore;
            gameOverLabel.setText("Tap to retry. Score: " + score + "\n\tHighScore: " + highScore);
          //  saveHighScore(); zatial nie je potreba pre fungovanie
            root.getChildren().add(gameOverLabel);
            root.getChildren().get(1).setOpacity(0);
            gameOver = true;
            gameLoop.stop();
//            timer.cancel();
        }
    }

    /**
     * Method that initializes game, clears lists of towers, clouds, Lightnings, clears view than draws users GUI object
     * sets X, Y position initializes score screen
     * Sets up the enviroment distance between towers etc
     *
     */
    private void initializeGame() {
//        timer.schedule(this, 0, 3000);

        listOfTowers.clear();
        listOfClouds.clear();
        listOfLightnings.clear();
        root.getChildren().clear();

        userCharacter.getGraphics().setTranslateX(200); // Posuva hraca na X osi do lava prava defualt 100
        userCharacter.getGraphics().setTranslateY(150);

        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: " + score);
        root.getChildren().addAll(userCharacter.getGraphics(), scoreLabel);

        //  vytvara cloudy na screen na  random X,Y poziciu a prida do listu cloudov
        // vytvara lightning na screen na random X,Y poziciu a prida do listu lightning
        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud(res.cloudImage, root, false);
            listOfClouds.add(cloud);

            Lightning lightning = new Lightning(res.lightningImage, root);
            listOfLightnings.add(lightning);
        }

        // vytvara towery na random pozicie a pridava ich do listu towerov

        Level level = new Level();

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

            Cloud cloud = new Cloud(res.cloudImage, root, false);
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

        dementor = new Dementor(res.deatheaterImage, root);
        dementor.setTranslateX(40.0);
        dementor.setTranslateY(100.0);
        root.getChildren().add(dementor);

//        score = 0;
        incrementOnce = true;
        gameOver = false;
        userCharacter.jumping = false;
//        userfall.stop();
//        userfall.play();

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
            System.out.println("jajojaofhohso");
            userCharacter = new UserCharacter(res.userImage);
        }



        userjump = new TranslateTransition(Duration.millis(450), userCharacter.getGraphics());
        userfall = new TranslateTransition(Duration.millis(5 * height), userCharacter.getGraphics());


        userjump.setInterpolator(Interpolator.LINEAR);
        userfall.setByY(height + 20);
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
            for (int i = 0; i < listOfClouds.size(); i++) {
                listOfClouds.get(i).setTranslateX(listOfClouds.get(i).getTranslateX() - 2);
            }
            for (int i = 0; i < listOfTowers.size(); i++) {
                listOfTowers.get(i).setTranslateX(listOfTowers.get(i).getTranslateX() - 2);
            }
            for (int i = 0; i < listOfLightnings.size(); i++) {
                listOfLightnings.get(i).setTranslateX(listOfLightnings.get(i).getTranslateX() - 2);
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

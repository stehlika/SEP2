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
import java.rmi.RemoteException;
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


    private UserCharacter userCharacter1;
    public UserCharacter userCharacter2;


    private TranslateTransition user1jump;
    private TranslateTransition user1fall;

    private TranslateTransition user2jump;
    private TranslateTransition user2fall;

  //  private RotateTransition rotator;
    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();
    private ScoreLabel scoreLabel = new ScoreLabel(width, 0);
    private Timeline gameLoop;
    private Player _player;



    private static GameSystem instance = null;
    private GameSystem()  {

    }

    public static GameSystem getInstance() {
        if(instance == null)
                instance = new GameSystem();

        return instance;
    }

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
                if (!gameOver) {
                    upMovement();
                    ClientRMI.getInstance().updateUserPosition(50, 0, _player.getNickname());
                    try {
                        ClientRMI.getInstance().update(ClientRMI.getInstance(), "Ahoj adam odtilato som pri3iel");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!gameOver) {
                    ClientRMI.getInstance().updateUserPosition(-50, 0 , _player.getNickname());
                    downMovement();
                }
                else
                    initializeGame();
            } else if (event.getCode() == KeyCode.LEFT) {
                if (!gameOver) {

                    updateUser2UP();
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
        user1jump.setByY(-50);
        user1jump.setCycleCount(1);
        userCharacter1.jumping = true;
        user1fall.stop();
        user1jump.stop();
        user1jump.play();

    }


    /**
     * Method is moving down user's GUI object by 50px and animates.
     */
    private void downMovement() {
        user1jump.setByY(50);
        user1jump.setCycleCount(1);
        userCharacter1.jumping = true;
        user1fall.stop();
        user1jump.stop();
        user1jump.play();

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
        Path towerPath = (Path) Shape.intersect(userCharacter1.getBounds(), tower.getBounds());
        Path cloudPath = (Path) Shape.intersect(userCharacter1.getBounds(), cloud.getBounds());
        Path lightningPath = (Path) Shape.intersect(userCharacter1.getBounds(), lightning.getBounds());

        //porovnava bound toweru a usera a meni premennu intersection podla toho
        boolean towerIntersection = !(towerPath.getElements().isEmpty());
        boolean lightningIntersection = !(lightningPath.getElements().isEmpty());
        boolean cloudIntersection = !(cloudPath.getElements().isEmpty());




        if (userCharacter1.getBounds().getCenterY() + userCharacter1.getBounds().getRadiusY() > height || userCharacter1.getBounds().getCenterY() - userCharacter1.getBounds().getRadiusY() < 0) {
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

        userCharacter1.getGraphics().setTranslateX(100); // Posuva hraca na X osi do lava prava defualt 100
        userCharacter1.getGraphics().setTranslateY(150);

        userCharacter2.getGraphics().setTranslateX(100);
        userCharacter2.getGraphics().setTranslateY(200);
        userCharacter2.getGraphics().setOpacity(0.5);

        scoreLabel.setOpacity(0.8);
        scoreLabel.setText("Score: 0");
        root.getChildren().addAll(userCharacter1.getGraphics(), scoreLabel, userCharacter2.getGraphics());

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
        userCharacter1.jumping = false;
        user1fall.stop();
        user1fall.play();


        gameLoop.play();
    }

    private void initGame(Player player) {
        if (player.getFaculty().equals("gryffindor")) {
            userCharacter1 = new UserCharacter(res.userImageGryf);
        } else if (player.getFaculty().equals("ravenclaw")) {
            userCharacter1 = new UserCharacter(res.userImageRave);
        } else if (player.getFaculty().equals("hufflepuff")) {
            userCharacter1 = new UserCharacter(res.userImageHuff);
        } else if (player.getFaculty().equals("slytherin")) {
            userCharacter1 = new UserCharacter(res.userImageSlyt);
        } else {
            System.out.println("jajojaofhohso");
            userCharacter1 = new UserCharacter(res.userImage);
        }

        userCharacter2 = new UserCharacter(res.userImageGryf);


        user1jump = new TranslateTransition(Duration.millis(450), userCharacter1.getGraphics());
        user1fall = new TranslateTransition(Duration.millis(5 * height), userCharacter1.getGraphics());

        user2jump = new TranslateTransition(Duration.millis(450), userCharacter2.getGraphics());
        user2fall = new TranslateTransition(Duration.millis(5 * height), userCharacter2.getGraphics());

        user1jump.setInterpolator(Interpolator.LINEAR);
        user1fall.setByY(height + 20);
        userCharacter1.getGraphics().setRotationAxis(Rotate.Z_AXIS);

        user2jump.setInterpolator(Interpolator.LINEAR);
        user2fall.setByY(height + 20);
        userCharacter2.getGraphics().setRotationAxis(Rotate.Z_AXIS);

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
                for (int i = 0; i < listOfTowers.size(); i++) {
                    if (listOfClouds.get(i).getX() < -listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX()) {
                        listOfClouds.get(i).setX(listOfClouds.get(i).getX() + width + listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX());
                    }
                    if (listOfLightnings.get(i).getTranslateX() < -listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleY()) {
                        listOfLightnings.get(i).setTranslateX(listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX());
                      //  listOfLightnings.get(i).setTranslateX((listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX()), 100);
                    }

                    listOfClouds.get(i).setX(listOfClouds.get(i).getX() - 1);
                   // listOfLightnings.get(i).setX(listOfLightnings.get(i).getX() -1);

                    listOfTowers.get(i).setTranslateX(listOfTowers.get(i).getTranslateX() - 2);
                }
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

    public static void updateFromServer(double x, double y, String fromWho) {
        String userovskyPostup = ("User sa pohol na klientovi  o x: " + x + " y: " + y + " islo to od: " + fromWho);
        System.out.println(userovskyPostup);

    }

    public void updateUser2UP() {
        user2jump.setByY(-50);
        user2jump.setCycleCount(1);
        userCharacter2.jumping = true;
        user2fall.stop();
        user2jump.stop();
        user2jump.play();
    }



}

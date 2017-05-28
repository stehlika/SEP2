package Client.Quidditch.GameSystem;

import Client.Quidditch.GameSystem.Resources.Resources;
import Client.Quidditch.HarryPotterMain;
import Server.Domain.Model.Player;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by terezamadova on 27/05/2017.
 */
public class Level {

    private final double width = 4000, height = 720;

    private Resources res = new Resources();
    private Pane root;
    private ArrayList<Tower> listOfTowers = new ArrayList<>();
    private ArrayList<Cloud> listOfClouds = new ArrayList<>();
    private ArrayList<Lightning> listOfLightnings = new ArrayList<>();

    public Level() {
        Stage primaryStage = HarryPotterMain.get_primaryStage();
        root = new Pane();Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        Cloud cloud = new Cloud(res.cloudImage, root);
        cloud.setX(20);
        listOfClouds.add(cloud);

//        for (int i = 0; i < 5; i++) {
//
//            Cloud cloud = new Cloud(res.cloudImage, root);
////             cloud.setX(Math.random() * width - cloud.get;
//            listOfClouds.add(cloud);
//            root.getChildren().add(cloud);
//            root.getChildren().add(cloud.getBounds());
//
//            Lightning lightning = new Lightning(res.lightningImage, root);
////            double xValue = Math.random() * width;
////            double yValue = Math.random() * height * 0.5 + 0.1;
////            lightning.setX(xValue);
////            lightning.setY(yValue);
////            lightning.setPosition(xValue, yValue);
//            listOfLightnings.add(lightning);
//            root.getChildren().add(lightning);
//            root.getChildren().add(lightning.getBounds());
//
//
//            SimpleDoubleProperty y = new SimpleDoubleProperty(0);
//            y.set(root.getHeight() * Math.random() / 2.0);
//            Tower tower = new Tower(res.towerImage, y, root,  false);
//            tower.setTranslateX(i * (width / 4 + 10) + 400);
//            listOfTowers.add(tower);
//            root.getChildren().add(tower);
//
//        }
//
//        if (listOfTowers.get(0).getTranslateX() <= -width / 12.3) {
//            listOfTowers.remove(0);
//            SimpleDoubleProperty y = new SimpleDoubleProperty(0);
//            y.set((root.getHeight() * Math.random() / 2.0));
//            Tower tube;
//            if (Math.random() < 0.4) {
//                tube = new Tower(res.towerImage, y, root,  false);
//            } else if (Math.random() > 0.85) {
//                tube = new Tower(res.towerImage,y, root,  true);
//            } else {
//                tube = new Tower(res.towerImage, y, root,  false);
//            }
//            tube.setTranslateX(listOfTowers.get(listOfTowers.size() - 1).getTranslateX() + (width / 4 + 10) + 0); // po prvom sete towerov + nieco nastavuje vzdialenost towerov
//            listOfTowers.add(tube);
//            root.getChildren().remove(7);
//            root.getChildren().add(tube);
//        }
//        for (int i = 0; i < listOfTowers.size(); i++) {
//            if (listOfClouds.get(i).getX() < -listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX()) {
//                listOfClouds.get(i).setX(listOfClouds.get(i).getX() + width + listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX());
//            }
//            if (listOfLightnings.get(i).getTranslateX() < -listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleY()) {
//                listOfLightnings.get(i).setTranslateX(listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX());
//                //  listOfLightnings.get(i).setTranslateX((listOfLightnings.get(i).getTranslateX() + width + listOfLightnings.get(i).frame.getWidth() * listOfLightnings.get(i).getScaleX()), 100);
//            }
//
//            listOfClouds.get(i).setX(listOfClouds.get(i).getX() - 1);
//            // listOfLightnings.get(i).setX(listOfLightnings.get(i).getX() -1);
//
//            listOfTowers.get(i).setTranslateX(listOfTowers.get(i).getTranslateX() - 2);
//        }
    }

    public ArrayList<Tower> getListOfTowers() {
        return listOfTowers;
    }

    public void setListOfTowers(ArrayList<Tower> listOfTowers) {
        this.listOfTowers = listOfTowers;
    }

    public ArrayList<Cloud> getListOfClouds() {
        return listOfClouds;
    }

    public void setListOfClouds(ArrayList<Cloud> listOfClouds) {
        this.listOfClouds = listOfClouds;
    }

    public ArrayList<Lightning> getListOfLightnings() {
        return listOfLightnings;
    }

    public void setListOfLightnings(ArrayList<Lightning> listOfLightnings) {
        this.listOfLightnings = listOfLightnings;
    }
}

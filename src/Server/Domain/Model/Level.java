package Server.Domain.Model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by terezamadova on 27/05/2017.
 */
public class Level implements Serializable {

    private final double width = 4000, height = 720;

    private ArrayList<Double> listOfTowersX = new ArrayList<>();
    private ArrayList<Double> listOfTowersY = new ArrayList<>();
    private ArrayList<Double> listOfCloudsX = new ArrayList<>();
    private ArrayList<Double> listOfCloudsY = new ArrayList<>();
    private ArrayList<Double> listOfLightningsX = new ArrayList<>();
    private ArrayList<Double> listOfLightningsY = new ArrayList<>();



    public Level() {


        for (int i = 1; i < 2000; i++) {
            listOfTowersX.add(i * 400.0);
            listOfTowersY.add(320.0 + Math.random() * 200);
            listOfCloudsX.add(i * 400.0);
            listOfCloudsY.add(Math.random() * 300.0);
            listOfLightningsX.add(i * 400.0 + 200);
            listOfLightningsY.add(Math.random() * 350);
        }
//        listOfTowersX.add(100.0);
//        listOfTowersX.add(300.0);
//        listOfTowersX.add(500.0);
//        listOfTowersX.add(700.0);
//        listOfTowersX.add(900.0);
//        listOfTowersY.add(1100.0);
//        listOfTowersY.add(200.0);
//        listOfTowersY.add(200.0);

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

    public ArrayList<Double> getListOfTowersX() {
        return listOfTowersX;
    }

    public void setListOfTowersX(ArrayList<Double> listOfTowersX) {
        this.listOfTowersX = listOfTowersX;
    }

    public ArrayList<Double> getListOfTowersY() {
        return listOfTowersY;
    }

    public void setListOfTowersY(ArrayList<Double> listOfTowersY) {
        this.listOfTowersY = listOfTowersY;
    }

    public ArrayList<Double> getListOfCloudsX() {
        return listOfCloudsX;
    }

    public void setListOfCloudsX(ArrayList<Double> listOfCloudsX) {
        this.listOfCloudsX = listOfCloudsX;
    }

    public ArrayList<Double> getListOfCloudsY() {
        return listOfCloudsY;
    }

    public void setListOfCloudsY(ArrayList<Double> listOfCloudsY) {
        this.listOfCloudsY = listOfCloudsY;
    }

    public ArrayList<Double> getListOfLightningsX() {
        return listOfLightningsX;
    }

    public void setListOfLightningsX(ArrayList<Double> listOfLightningsX) {
        this.listOfLightningsX = listOfLightningsX;
    }

    public ArrayList<Double> getListOfLightningsY() {
        return listOfLightningsY;
    }

    public void setListOfLightningsY(ArrayList<Double> listOfLightningsY) {
        this.listOfLightningsY = listOfLightningsY;
    }
}

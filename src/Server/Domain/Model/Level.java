package Server.Domain.Model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by terezamadova on 27/05/2017.
 */
public class Level implements Serializable {

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
    }

    public ArrayList<Double> getListOfTowersX() {
        return listOfTowersX;
    }

    public ArrayList<Double> getListOfTowersY() {
        return listOfTowersY;
    }

    public ArrayList<Double> getListOfCloudsX() {
        return listOfCloudsX;
    }

    public ArrayList<Double> getListOfCloudsY() {
        return listOfCloudsY;
    }

    public ArrayList<Double> getListOfLightningsX() {
        return listOfLightningsX;
    }

    public ArrayList<Double> getListOfLightningsY() {
        return listOfLightningsY;
    }

}

package Client.Quidditch.Controller;

import Server.Domain.Model.House;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class HouseCupController extends MasterController {
    public ListView gryffindorLV;
    public Label gryffindorTotalL;
    public Label gryffindorPlayerL;
    public ListView ravenclawLV;
    public Label ravenclawTotalL;
    public Label ravenclawPlayerL;
    public ListView hufflepuffLV;
    public Label hufflepuffTotalL;
    public Label hufflepuffPlayerL;
    public ListView slytherinLV;
    public Label slytherinTotalL;
    public Label slytherinPlayerL;
    public Button backBtn;
    private ClientRMI rmi = ClientRMI.getInstance();

    private House gryffindor = new House("", 0, "");
    private House ravenclaw = new House("", 0, "");
    private House hufflepuff = new House("", 0, "");
    private House slytherin = new House("", 0, "");
    private String gryffindorBest;
    private String ravenclawBest;
    private String hufflepuffBest;
    private String slytherinBest;
    private ArrayList<Pair<String, Integer>> scoresGryffindorArrayList = new ArrayList<>();
    private ArrayList<Pair<String, Integer>> scoresRavenclawArrayList = new ArrayList<>();
    private ArrayList<Pair<String, Integer>> scoresHufflepuffArrayList = new ArrayList<>();
    private ArrayList<Pair<String, Integer>> scoresSlytherinArrayList = new ArrayList<>();

    @FXML
    public void initialize() {
        backBtn.toFront();


        try {
            gryffindor = rmi.getHouse("gryffindor");
            ravenclaw = rmi.getHouse("ravenclaw");
            hufflepuff = rmi.getHouse("hufflepuff");
            slytherin = rmi.getHouse("slytherin");
            scoresGryffindorArrayList = rmi.getHouseLeaderBoard("gryffindor");
            scoresRavenclawArrayList = rmi.getHouseLeaderBoard("ravenclaw");
            scoresHufflepuffArrayList = rmi.getHouseLeaderBoard("hufflepuff");
            scoresSlytherinArrayList = rmi.getHouseLeaderBoard("slytherin");
            gryffindorBest = gryffindor.getBestplayer();
            ravenclawBest = ravenclaw.getBestplayer();
            hufflepuffBest = hufflepuff.getBestplayer();
            slytherinBest = slytherin.getBestplayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gryffindorPlayerL.setText(gryffindorBest);
        ravenclawPlayerL.setText(ravenclawBest);
        hufflepuffPlayerL.setText(hufflepuffBest);
        slytherinPlayerL.setText(slytherinBest);

        gryffindorTotalL.setText(String.valueOf(gryffindor.getTotalscore()));
        ravenclawTotalL.setText(String.valueOf(ravenclaw.getTotalscore()));
        hufflepuffTotalL.setText(String.valueOf(hufflepuff.getTotalscore()));
        slytherinTotalL.setText(String.valueOf(slytherin.getTotalscore()));

        manageList(gryffindorLV, scoresGryffindorArrayList, "gryffindorScores");
        manageList(ravenclawLV, scoresRavenclawArrayList, "ravenclawScores");
        manageList(hufflepuffLV, scoresHufflepuffArrayList, "hufflepuffScores");
        manageList(slytherinLV, scoresSlytherinArrayList, "slytherinScores");
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

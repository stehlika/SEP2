package Client.Quidditch.Controller;

import Server.Domain.Model.House;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;

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

    @FXML
    public void initialize() {
        backBtn.toFront();


        try {
            hufflepuff = rmi.getHouse("hufflepuff");
            slytherin = rmi.getHouse("slytherin");
            System.out.println("huff"+hufflepuff.toString());
            System.out.println("sly"+slytherin.toString());
//            gryffindorBest = rmi.getHouse("Gryffindor").getBestplayer();
//            ravenclawBest = rmi.getHouse("Ravenclaw").getBestplayer();
            hufflepuffBest = hufflepuff.getBestplayer();
            slytherinBest = slytherin.getBestplayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        gryffindorPlayerL.setText(gryffindorBest);
//        ravenclawPlayerL.setText(ravenclawBest);
//        hufflepuffPlayerL.setText(hufflepuffBest);
//        slytherinPlayerL.setText(slytherinBest);

//        gryffindorTotalL.setText(String.valueOf(gryffindor.getTotalscore()));
//        ravenclawTotalL.setText(String.valueOf(ravenclaw.getTotalscore()));
        hufflepuffTotalL.setText(String.valueOf(hufflepuff.getTotalscore()));
        slytherinTotalL.setText(String.valueOf(slytherin.getTotalscore()));

//        manageList(gryffindorLV, gryffindor, "sth");
//        manageList(ravenclawLV, ravenclaw, "sth");
//        manageList(hufflepuffLV, hufflepuff, "sth");
//        manageList(slytherinLV, slytherin, "sth");
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

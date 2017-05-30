package Client.Controller;

import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by terezamadova on 24/05/2017.
 */
public class SortingCeremonyController extends MasterController {

    public Button continueBtn;
    public Label houseChosenL;
    public ImageView houseIV;
    private String faculty;

    public static Player player;
    private Player _player;

    @FXML
    public void initialize() {
        _player = player;
        faculty = _player.getFaculty();
        continueBtn.toFront();
        houseChosenL.setText(faculty);
    }

    public void showMainView(ActionEvent actionEvent) {
        newView("MainView","../View/mainView.fxml", continueBtn, _player);
    }

}

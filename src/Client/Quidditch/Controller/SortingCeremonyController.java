package Client.Quidditch.Controller;

import Server.Domain.DatabaseAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by terezamadova on 24/05/2017.
 */
public class SortingCeremonyController extends MasterController {

    public Button continueBtn;
    public Label houseChosenL;
    public DatabaseAdapter db = new DatabaseAdapter();
    public ImageView houseIV;

    @FXML
    public void initialize() {
//        continueBtn.toFront();
//        try {
//            houseChosenL.setText(db.houseSelection());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void showMainView(ActionEvent actionEvent) {
        newView("../View/mainView.fxml", continueBtn);
    }

}

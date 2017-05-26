package Client.Quidditch.Controller;

import Server.Domain.DatabaseAdapter;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class SignInController extends MasterController {

    public Button signInBtn;
    public TextField usernameTF;
    public DatabaseAdapter db = new DatabaseAdapter();

    @FXML
    public void initialize() {

    }

    public void signIn(ActionEvent actionEvent) {
        String username = usernameTF.textProperty().getValue();
        boolean newPlayer = false;
        try {
            Player player = db.checkPlayer(username);
            if (player == null) {
                System.out.println("novy player");
                String house;
                house = db.houseSelection();
                db.createPlayer(username, house);
                System.out.println(house);
                newPlayer = true;
            } else {
                System.out.println("player existuje");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newPlayer) {
            newView("../View/sortingCeremonyView.fxml", signInBtn);
        } else {
            newView("../View/mainView.fxml", signInBtn);
        }
    }
}
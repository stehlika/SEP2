package Client.Quidditch.Controller;

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
    public ClientRMI rmi = ClientRMI.getInstance();
    public TextField serverIPTF;
    public TextField serverPortTF;

    @FXML
    public void initialize() {

    }

    public void signIn(ActionEvent actionEvent) {
        ClientRMI.getInstance().startClient(serverIPTF.textProperty().getValue(), Integer.parseInt(serverPortTF.textProperty().getValue()));
        String username = usernameTF.textProperty().getValue();
        Player player = null;
        try {
            player = rmi.checkPlayer(username);
            if (player == null) {
                System.out.println("novy player");
                String house;
                house = rmi.houseSelection();
                player = new Player(username, 0, 0, house);
                rmi.addPlayer(player);
                System.out.println(house);
                newView("../View/sortingCeremonyView.fxml", signInBtn, player);
            } else {
                System.out.println("player existuje");
                newView("../View/mainView.fxml", signInBtn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
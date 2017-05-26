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
    private ClientRMI rmi = ClientRMI.getInstance();
    public TextField serverIPTF;
    public TextField serverPortTF;

    @FXML
    public void initialize() {

    }

    public void signIn(ActionEvent actionEvent) {
        String serverIP = serverIPTF.textProperty().getValue();
        int serverPort = Integer.parseInt(serverPortTF.textProperty().getValue());
        String username = usernameTF.textProperty().getValue();
        Player player;


        if (serverIP.length() > 7 &&  serverIP.length() <= 15 && serverPort > 999 && serverPort < 10000) {
            ClientRMI.getInstance().startClient(serverIP, serverPort);
            System.out.println("Pusta sa pripojenie ");
        } else {
            MasterController.showAlertView("Sorry IP address and port wrong", 300);
            return;
        }

        if (username.length() > 3 && username.length() < 15) {
            try {
                player = rmi.checkPlayer(username);
                if (player == null) {
                    System.out.println("novy player");
                    String house;
                    house = rmi.houseSelection();
                    System.out.println("Toto je house: " + house);
                    player = new Player(username, 0, 0, house);
                    rmi.addPlayer(player);
                    System.out.println(house);
                    System.out.println("Toto je player v Sign In: " + player);
                    newView("SortingCeremony","../View/sortingCeremonyView.fxml", signInBtn, player);
                } else {
                    player = new Player(player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty());
                    System.out.println("player existuje: " + player);
                    newView("MainView","../View/mainView.fxml", signInBtn, player);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MasterController.showAlertView("Sorry username must be longer than 3", 400);
        }
    }
}
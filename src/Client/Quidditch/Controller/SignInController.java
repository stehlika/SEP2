package Client.Quidditch.Controller;

import Client.Quidditch.GameSystem.Resources.Resources;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
    private MediaPlayer mp;

    @FXML
    public void initialize() {
        usernameTF.setText("Tejka");
        serverIPTF.setText("localhost");
        serverPortTF.setText("9998");

        Resources resources = new Resources();

        Media media = resources.harryPotterThemeSong;
        mp = new MediaPlayer(media);
        mp.play();
    }

    public void signIn(ActionEvent actionEvent) {
        String serverIP = serverIPTF.textProperty().getValue();
        int serverPort = Integer.parseInt(serverPortTF.textProperty().getValue());
        String username = usernameTF.textProperty().getValue();
        Player player;


        if (serverIP.length() > 7 &&  serverIP.length() <= 15 && serverPort > 999 && serverPort < 10000) {
            ClientRMI.getInstance().startClient(serverIP, serverPort);
        } else {
            MasterController.showAlertView("Sorry IP address and port wrong", 300);
            return;
        }

        if (username.length() > 3 && username.length() < 15) {
            try {
                player = rmi.checkPlayer(username);
                if (player == null) {
                    String house;
                    house = rmi.houseSelection();
                    player = new Player(username, 0, 0, house);
                    rmi.addPlayer(player);
                    System.out.println(house);
                    mp.stop();
                    newView("SortingCeremony","../View/sortingCeremonyView.fxml", signInBtn, player);
                } else {
                    player = new Player(player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty());
                    mp.stop();
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
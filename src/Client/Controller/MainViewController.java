package Client.Controller;

import Client.GameSystem.GameSystem;
import Client.GameSystem.GameSystemSingle;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class MainViewController extends MasterController {
    public Button playBtnMS;
    public Button playMultiBtnMS;
    public Button leaderboardBtn;
    public Button houseCupBtn;
    public Button profileBtn;

    public static Player player;
    private Player _player = player;

    @FXML
    public void initialize() {
        _player = player;
    }

    public void showLeaderBoard(ActionEvent actionEvent) {
        newView("../View/leaderboardView.fxml", leaderboardBtn);
    }

    public void playGame(ActionEvent actionEvent) {
        newView("Singleplayer", "../View/instructionsView.fxml", playBtnMS, _player);
    }

    public void playMultiGame(ActionEvent actionEvent) {
        newView("Multiplayer", "../View/instructionsMultiView.fxml", playMultiBtnMS, _player);
    }

    public void showHouseCup(ActionEvent actionEvent) {
        newView("../View/houseCupView.fxml", houseCupBtn);
    }

    public void showProfile(ActionEvent actionEvent) {
        newView("Profile","../View/profileView.fxml", profileBtn, _player);
    }
}

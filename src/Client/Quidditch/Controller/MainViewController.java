package Client.Quidditch.Controller;

import Client.Quidditch.GameSystem.GameSystem;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class MainViewController extends MasterController {
    public Button playBtnMS;
    public Button leaderboardBtn;
    public Button houseCupBtn;
    public Button profileBtn;

    public static Player player;
    private Player _player = player;

    @FXML
    public void initialize() {
        System.out.println("player"+player);
        _player = player;
        System.out.println("_player"+_player);
    }

    public void showLeaderBoard(ActionEvent actionEvent) {
        newView("../View/leaderboardView.fxml", leaderboardBtn);
    }

    public void playGame(ActionEvent actionEvent) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.startGame(_player);
    }

    public void showHouseCup(ActionEvent actionEvent) {
        newView("../View/houseCupView.fxml", houseCupBtn);
    }

    public void showProfile(ActionEvent actionEvent) {
        newView("Profile","../View/profileView.fxml", profileBtn, _player);
    }
}

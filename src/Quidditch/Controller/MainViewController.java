package Quidditch.Controller;

import Quidditch.GameSystem.GameSystem;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class MainViewController extends MasterController {
    public Button playBtnMS;
    public Button leaderboardBtn;
    public Button houseCupBtn;

    public void showLeaderBoard(ActionEvent actionEvent) {
    }

    public void playGame(ActionEvent actionEvent) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.startGame();
    }

    public void showHouseCup(ActionEvent actionEvent) {
    }
}

package Client.Quidditch.Controller;

import Client.Quidditch.GameSystem.GameSystem;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class MainViewController extends MasterController {
    public Button playBtnMS;
    public Button leaderboardBtn;
    public Button houseCupBtn;
    public Button profileBtn;

    public void showLeaderBoard(ActionEvent actionEvent) {
        newView("../View/leaderboardView.fxml", leaderboardBtn);
    }

    public void playGame(ActionEvent actionEvent) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.startGame();
    }

    public void showHouseCup(ActionEvent actionEvent) {
        newView("../View/houseCupView.fxml", houseCupBtn);
    }

    public void showProfile(ActionEvent actionEvent) {
        newView("../View/profileView.fxml", profileBtn);
    }
}

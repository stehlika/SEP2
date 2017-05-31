package Client.Controller;

import Client.GameSystem.GameSystem;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class InstructionsMultiController extends MasterController {
    public Button backBtn;
    public Button playBtnIS;
    public Label instructionsL;

    public static Player player;
    private Player _player;

    @FXML
    public void initialize() {
        backBtn.toFront();
        _player = player;
        instructionsL.setText("You and your friend are about to have a little match.\n"+
                                "Make sure your flight is as smooth as possible.\n" +
                                "Use arrow keys to move up and down to avoid clouds,\n" +
                                "towers and lightning bolts.\n" +
                                "The first who bumps into any obstacle looses.\n" +
                                "Enjoy the game!");
    }

    public void playGame() {
        GameSystem.getInstance().startGame(_player);
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

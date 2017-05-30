package Client.Controller;

import Client.GameSystem.GameSystemSingle;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class InstructionsController extends MasterController {
    public Button backBtn;
    public Button playBtnIS;
    public Label instructionsL;

    public static Player player;
    private Player _player;


    @FXML
    public void initialize() {

        System.out.println("player zavolany v initialize Single"+player);
        backBtn.toFront();
        _player = player;
        instructionsL.setText("hatla Single");
        System.out.println("_player zavolany v initialize Single"+_player);
    }

    public void playGame() {
        GameSystemSingle.getInstance().startGame(_player);
        System.out.println("player zavolany v playGame Single"+_player);
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

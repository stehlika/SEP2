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

        backBtn.toFront();
        _player = player;
        instructionsL.setText("Move up and down using arrow keys.\n" +
                "Avoid clouds and towers (they slow you down)\n" +
                "and when you bump into lightning bolt, you will die.\n" +
                "Also the dementor is trying to catch you, so make sure\n" +
                "it won't get very close to you or else you will be kissed.\n" +
                "... And all your happy memories will slowly but surely\n" +
                "fade away. So you better fly quickly!\n" +
                "Grab your broomstick and FLY!");
    }

    public void playGame() {
        GameSystemSingle.getInstance().startGame(_player);
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

package Client.Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class InstructionsController extends MasterController {
    public Button backBtn;

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

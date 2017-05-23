package Client.Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class HouseCupController extends MasterController {
    public ListView gryffindorLV;
    public ListView ravenclawLV;
    public ListView hufflepuffLV;
    public ListView slytherinLV;
    public Button backBtn;

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

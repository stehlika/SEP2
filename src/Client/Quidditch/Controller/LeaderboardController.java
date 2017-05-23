package Client.Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class LeaderboardController extends MasterController {
    public Button backBtn;
    public ListView leaderboardLV;

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

package Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class ProfileController extends MasterController {
    public Button backBtn;
    public Label nicknameL;
    public Label playTimeL;
    public Label highScoreL;
    public Label houseL;
    public ImageView profileIV;

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

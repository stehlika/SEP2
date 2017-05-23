package Client.Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public class SignInController extends MasterController {
    public Button signInBtn;
    public TextField usernameTF;


    @FXML
    public void initialize() {
    }

    public void signIn(ActionEvent actionEvent) {
        //usernameTF.textProperty().getValue();
        newView("../View/mainView.fxml", signInBtn);
    }
}
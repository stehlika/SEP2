package Quidditch.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
        System.out.println("Stlacilo sa tlacidlo ");
        System.out.println(usernameTF.textProperty().getValue());
        try {
            newView("../View/mainView.fxml", signInBtn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package Client.Quidditch;

import Client.Quidditch.Controller.ClientRMI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by adamstehlik on 13/05/2017.
 */
public class HarryPotterMain extends Application {

    private static Stage _primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("View/signInView.fxml"));
            primaryStage.setTitle("Jiri Hrncir");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setResizable(false);
            this._primaryStage = primaryStage;
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        primaryStage.setOnCloseRequest(we -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("You are " +
                    "trying to close most epic Harry Potter Game");
            alert.setContentText("Please don't.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                primaryStage.close();
            } else {
                we.consume();
            }
        });
    }


    public static Stage get_primaryStage() {
        return _primaryStage;
    }
}

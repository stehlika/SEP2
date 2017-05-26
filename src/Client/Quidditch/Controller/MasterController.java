package Client.Quidditch.Controller;

import Server.Domain.Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by adamstehlik on 19/05/2017.
 */
public abstract class MasterController {

    /**
     * Method creating new window when adding new bus, chauffeur or when making reservation.
     *
     * @param source source - window that should be opened
     * @param name   - title
     * @param height - hight of the window
     * @throws IOException
     */
    public void newPopUpWindow(String source, String name, int height) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(source));
            Scene scene = new Scene(root, 400, height);
            Stage window = new Stage();
            window.setScene(scene);
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(name);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method returning the user back to main screen after he clicks on arrow button.
     *
     * @param backBtn     back - button
     * @param actionEvent a button click
     * @throws IOException
     */
    public void backToMainScreen(Button backBtn, ActionEvent actionEvent) {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) backBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../View/mainView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method transferring the user from main screen to manage sceens.
     *
     * @param source source - window (Manage Buses/Chauffeurs/Reservations)
     * @param button button for 'Reservations', 'Chauffeurs' and 'Buses'
     * @throws IOException
     */
    public void newView(String source, Button button)  {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) button.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(source));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newView(String vc, String source, Button button, Player player)  {
        try {
            if (vc.equals("SortingCeremony")) {
                SortingCeremonyController.player = player;
            } else if (vc.equals("Leaderboard")) {
                LeaderboardController.player = player;
            }  else if (vc.equals("Profile")) {
                ProfileController.player = player;
            } else if (vc.equals("MainView")) {
                MainViewController.player = player;
            }
            Stage stage;
            Parent root;
            stage = (Stage) button.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(source));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method managing the lists on main screen.
     *
     * @param listView  list displaying the arraylist
     * @param dataArray arraylist to display
     * @param type      type of objects stored in arraylists (reservations, chauffeurs, buses)
     */
    public void manageList(ListView listView, ArrayList dataArray, String type) {
        if (dataArray.isEmpty()) {
            listView.setPlaceholder(new javafx.scene.control.Label("0 " + type + " created"));
        } else {
            ObservableList<Object> data = FXCollections.observableArrayList(dataArray);
            listView.setItems(data);
        }
    }

    /**
     * Method displaying pop-up window indicating to the incorrectly filled field.
     *
     * @param mistake kind of the mistake to be displayed in alert window (eg. date)
     */
    public void showAlertView(String mistake) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong " + mistake);
        alert.setHeaderText(null);
        alert.setContentText("Sorry, you have entered wrong " + mistake);
        alert.showAndWait();
    }

    /**
     * Method displaying confirmation pop-up window.
     *
     * @param title   title of the window
     * @param header  header of the window
     * @param content content of the window
     * @return if true, action is confirmed; otherwise nothing happens
     */
    public boolean confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Static method displaying pop-up window indicating to the server error.
     *
     * @param mistake kind of the mistake to be displayed in alert window (eg. date)
     */
    public static void showAlertView(String mistake, int errorCode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong " + mistake);
        alert.setHeaderText(null);
        alert.setContentText("Sorry, you have entered wrong " + mistake);
        alert.showAndWait();
    }

}

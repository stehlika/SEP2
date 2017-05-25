package Client.Quidditch.Controller;

import Server.Domain.DatabaseAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class LeaderboardController extends MasterController {
    public Button backBtn;
    public ListView leaderboardLV;
    public DatabaseAdapter db = new DatabaseAdapter();

    private ArrayList<Pair<String, Integer>> scoresArrayList = new ArrayList<>();

    @FXML
    public void initialize() {
        backBtn.toFront();
        try {
            scoresArrayList = db.getLeaderBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
        manageList(leaderboardLV, scoresArrayList, "scores");
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

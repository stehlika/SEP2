package Client.Controller;

import Server.Controller.RmiService;
import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class ProfileController extends MasterController {

    public Button backBtn;
    public Label nicknameL;
    public Label playTimeL;
    public ListView scoresLV;
    public Label houseL;
    public ImageView profileIV;
    public Image image;
    private ClientRMI rmi = ClientRMI.getInstance();

    public static Player player;
    private Player _player;
    private int playTime = 0;

    private ArrayList<Integer> scoresArrayList = new ArrayList<>();

    @FXML
    public void initialize() {
        backBtn.toFront();
        _player = player;

        nicknameL.setText(_player.getNickname());
        playTimeL.setText(Integer.toString(_player.getPlaytime()) + " s");
        houseL.setText(_player.getFaculty());
        try {
            scoresArrayList = rmi.getHighScoreForPlayer(_player.getNickname());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (_player.getFaculty().equals("gryffindor")) {
            image = new Image(getClass().getResourceAsStream("../GameSystem/Resources/harry.png"));
        } else if (_player.getFaculty().equals("ravenclaw")) {
            image = new Image(getClass().getResourceAsStream("../GameSystem/Resources/cho.png"));
        } else if (_player.getFaculty().equals("hufflepuff")) {
            image = new Image(getClass().getResourceAsStream("../GameSystem/Resources/cedric.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("../GameSystem/Resources/draco.png"));
        }
        profileIV.setImage(image);
        for (Integer score: scoresArrayList) {
            playTime += score;
        }
        playTimeL.setText(playTime + " s");
        manageList(scoresLV, scoresArrayList, "scores");
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

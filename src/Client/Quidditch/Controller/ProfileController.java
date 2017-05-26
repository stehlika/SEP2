package Client.Quidditch.Controller;

import Server.Domain.Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by adamstehlik on 22/05/2017.
 */
public class ProfileController extends MasterController {

    public Button backBtn;
    public Label nicknameL;
    public Label playTimeL;
    public Label winRatioL;
    public Label houseL;
    public ImageView profileIV;
    public Image image;

    private String nickname;
    private String faculty;
    private int playTime;
    private double winRatio;

    public static Player player;
    private Player _player;

    @FXML
    public void initialize() {
        backBtn.toFront();
        _player = player;

        nicknameL.setText(_player.getNickname());
        playTimeL.setText(Integer.toString(_player.getPlaytime()) + " s");
        winRatioL.setText(Double.toString(_player.getWinratio()));
        houseL.setText(_player.getFaculty());

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
    }

    public void back(ActionEvent actionEvent) {
        backToMainScreen(backBtn, actionEvent);
    }
}

package Client.GameSystem.Resources;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Resources {

    public Image cloudImage;
    public Image lightningImage;
    public Image deatheaterImage;
    public Image userImage;
    public Image userImageGryf;
    public Image userImageRave;
    public Image userImageSlyt;
    public Image userImageHuff;
    public Image towerImage;
    public Image towerImageGryf;
    public Image towerImageRave;
    public Image towerImageSlyt;
    public Image towerImageHuff;

    public Media harryPotterThemeSong;


    public Resources() {
        try {
            deatheaterImage = new Image(getClass().getResourceAsStream("dementor.png"));
            cloudImage = new Image(getClass().getResourceAsStream("cloud.png"));
            lightningImage = new Image(getClass().getResourceAsStream("bolt.png"));

            userImage = new Image(getClass().getResourceAsStream("harry.png"));
            userImageGryf = new Image(getClass().getResourceAsStream("harry.png"));
            userImageRave = new Image(getClass().getResourceAsStream("cho.png"));
            userImageSlyt = new Image(getClass().getResourceAsStream("draco.png"));
            userImageHuff = new Image(getClass().getResourceAsStream("cedric.png"));

            towerImage = new Image(getClass().getResourceAsStream("hufflepuff_tower.png"));
            towerImageGryf = new Image(getClass().getResourceAsStream("gryffindor_tower.png"));
            towerImageRave = new Image(getClass().getResourceAsStream("ravenclaw_tower.png"));
            towerImageHuff = new Image(getClass().getResourceAsStream("hufflepuff_tower.png"));
            towerImageSlyt = new Image(getClass().getResourceAsStream("slytherin_tower.png"));


        } catch (Exception e) {
            e.printStackTrace();
        }


        String path =  getClass().getResource("Harry-Potter-Theme-Song.mp3").toString();
        harryPotterThemeSong = new Media(path);

    }
}

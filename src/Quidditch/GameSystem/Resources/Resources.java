package Quidditch.GameSystem.Resources;

import javafx.scene.image.Image;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Resources {

    public Image userImage;
    public Image  towerImage;

    public Resources() {
        try {
            userImage = new Image(getClass().getResourceAsStream("cedric.png"));
            towerImage = new Image(getClass().getResourceAsStream("griffindor_tower.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

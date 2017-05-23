package Client.Quidditch.GameSystem.Resources;

import javafx.scene.image.Image;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Resources {

    public Image userImage;
    public Image  towerImage;
    public Image cloudImage;
    public Image lightningImage;

    public Resources() {
        try {
            userImage = new Image(getClass().getResourceAsStream("cedric.png"));
            towerImage = new Image(getClass().getResourceAsStream("griffindor_tower.png"));
            cloudImage = new Image(getClass().getResourceAsStream("cloud.png"));
            lightningImage = new Image(getClass().getResourceAsStream("bolt.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package GameSystem.Resources;

import javafx.scene.image.Image;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Resources {

    public Image userImage;

    public Resources() {
        try {
            userImage = new Image(getClass().getResourceAsStream("player.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

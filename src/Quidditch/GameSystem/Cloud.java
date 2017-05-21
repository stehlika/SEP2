package Quidditch.GameSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Cloud extends ImageView {


    /**
     *  No-argument constructor. Gets image from resources package and sets W and H for each cloud and opacity.
     */
    public Cloud() {
        setImage(new Image(Cloud.class.getResourceAsStream("Resources/cloud.png")));
        setScaleX(Math.random() / 2.0 + 0.5);
        setScaleY(Math.random() / 2.0 + 0.5);
        setOpacity(0.5);
    }
}

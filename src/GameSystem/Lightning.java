package GameSystem;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Lightning extends ImageView {

    public Lightning() {
        setImage(new Image(Lightning.class.getResourceAsStream("Resources/bolt.png")));
        setScaleX(Math.random() / 1.0 + 0.5);
        setScaleY(Math.random() / 1.0 + 0.5);
        setFitWidth(20);
        setFitHeight(30);
        setOpacity(0.9);
    }
}

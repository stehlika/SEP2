package Client.Quidditch.GameSystem;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Lightning extends ImageView {

    private Rectangle bounds;

    /*
    * No-argument construcotr get's image from resources package and than sets random H and W with 1 opacity
    *
    */

    public Lightning() {
        setImage(new Image(Lightning.class.getResourceAsStream("Resources/bolt.png")));
        setFitWidth(20);
        setFitHeight(30);
        setOpacity(1.0);
        bounds = new Rectangle(20, 30);
        bounds.setStroke(Color.TRANSPARENT);
        bounds.setFill(Color.TRANSPARENT);
    }

    public void setPosition(double xValue, double yValue) {
        bounds.setX(xValue);
        bounds.setY(yValue);
    }


    public Rectangle getBounds() {
        return bounds;
    }
}

package Client.Quidditch.GameSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class UserCharacter {

    private ImageView graphics = new ImageView();
    private Image frame;
    public boolean jumping = false;
    private Ellipse bounds;

    public ImageView getGraphics() {
        return graphics;
    }

    public Ellipse getBounds() {
        return bounds;
    }

    /*
    * Creates new user object with bounds
    * Bounds are used for intersection with another objects
    * Ellipse is used for more precise collision
    */

    public UserCharacter(Image frame) {
        this.frame = frame;
        this.bounds = new Ellipse(frame.getWidth() / 2.0, 11.5);
        graphics.setImage(frame);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.BLACK);
        // vycentruje graficky prvok hraca s tou elipsou aby bol v strede elipsy
        bounds.centerXProperty().bind(graphics.translateXProperty().add(frame.getWidth() / 2.0));
        bounds.centerYProperty().bind(graphics.translateYProperty().add(12.0));
        bounds.rotateProperty().bind(graphics.rotateProperty());
        bounds.setFill(Color.BLACK);
    }
}

package Quidditch.GameSystem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Cloud extends ImageView {

    private Rectangle bounds;
    private Image frame;


    /**
     *  No-argument constructor. Gets image from resources package and sets W and H for each cloud and opacity.
     */
    public Cloud() {
        setImage(new Image(Cloud.class.getResourceAsStream("Resources/cloud.png")));
        this.frame = (new Image(Cloud.class.getResourceAsStream("Resources/cloud.png")));
        setScaleX(Math.random() / 2.0 + 0.5);
        setScaleY(Math.random() / 2.0 + 0.5);
        setOpacity(0.5);
        bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setY(this.getY());
        bounds.setX(this.getX());
        bounds.setStroke(Color.BLACK);
        bounds.setFill(Color.BLACK);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

package Client.GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Cloud extends Group {
    /**
     *  No-argument constructor. Gets image from resources package and sets W and H for each cloud and opacity.
     */

    private ImageView cloudBody = new ImageView();
    private Rectangle bounds;
    private Image frame;

    public Cloud(Image frame, Pane root) {
        this.frame = frame;
        cloudBody.setImage(this.frame);
        cloudBody.setX(2.5);
        cloudBody.setY(2.5);
        cloudBody.setOpacity(0.8);
//        cloudBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        this.bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setStroke(Color.TRANSPARENT);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setX(cloudBody.getX());
        bounds.setY(cloudBody.getY());
        bounds.rotateProperty().bind(cloudBody.rotateProperty());

        getChildren().addAll(cloudBody);
        getChildren().addAll(bounds);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public String toString() {
        return "Cloud " + cloudBody.getX() + ", " + cloudBody.getY();
    }
}

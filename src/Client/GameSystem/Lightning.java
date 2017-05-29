package Client.GameSystem;

import javafx.scene.Group;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Lightning extends Group {

    private Rectangle bounds;
    public ImageView lightningBody = new ImageView();
    public Image frame;

    /*
    * No-argument construcotr get's image from resources package and than sets random H and W with 1 opacity
    *
    */

    public Lightning(Image frame, Pane root) {
        this.frame = frame;
//        setImage(new Image(Lightning.class.getResourceAsStream("Resources/bolt.png")));
//        setFitWidth(20);
//        setFitHeight(30);

        lightningBody.setImage(this.frame);
        lightningBody.setX(2.5);
        lightningBody.setY(30);
//        lightningBody.yProperty().bind(root.heightProperty().divide(2));
        setOpacity(1.0);
        bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setStroke(Color.BLACK);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setX(lightningBody.getX());
        bounds.setY(lightningBody.getY());

        getChildren().addAll(lightningBody);
        getChildren().addAll(bounds);
    }




    public Rectangle getBounds() {
        return bounds;
    }
}

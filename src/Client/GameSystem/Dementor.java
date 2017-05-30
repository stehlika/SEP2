package Client.GameSystem;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by terezamadova on 25/05/2017.
 */
public class Dementor extends Group {

    private ImageView dementorBody = new ImageView();
    private Image frame;
    private Rectangle bounds;

    public Dementor(Image frame, Pane root) {
        this.frame = frame;
        dementorBody.setImage(this.frame);
        this.bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        dementorBody.setX(2.5);
        dementorBody.setY(2.5);
        dementorBody.setImage(frame);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.TRANSPARENT);
        bounds.setX(dementorBody.getX());
        bounds.setY(dementorBody.getY());

        getChildren().addAll(dementorBody);
        getChildren().addAll(bounds);
    }

    public ImageView getDementorBody() {
        return dementorBody;
    }

    public Rectangle getBounds() {
        return bounds;
    }


}

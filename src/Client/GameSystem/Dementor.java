package Client.GameSystem;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 * Created by terezamadova on 25/05/2017.
 */
public class Dementor extends Group {

    private ImageView graphics = new ImageView();
    private Image frame;
    private Rectangle bounds;

    public ImageView getGraphics() {
        return graphics;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Dementor(Image frame, Pane root) {
        this.frame = frame;
        graphics.setImage(this.frame);
        this.bounds = new Rectangle(100, 700);
        graphics.setX(2.5);
        graphics.yProperty().bind(root.heightProperty().divide(2));
        graphics.setImage(frame);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.BLACK);
        bounds.setX(graphics.getX());
        bounds.setY(graphics.getY());

        getChildren().addAll(graphics);
        getChildren().addAll(bounds);


    }


}

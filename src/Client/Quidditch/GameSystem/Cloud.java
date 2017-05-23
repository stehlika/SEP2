package Client.Quidditch.GameSystem;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Cloud extends Group {

    private Rectangle bounds;
    private Image frame;
    private ImageView cloudBody = new ImageView();


    /**
     *  No-argument constructor. Gets image from resources package and sets W and H for each cloud and opacity.
     */
    public Cloud(Image frame, Pane root) {
        this.frame = frame;
        cloudBody.setImage(this.frame);
      //  setScaleX(Math.random() / 2.0 + 0.5);
      //  setScaleY(Math.random() / 2.0 + 0.5);
        setOpacity(0.9);

        cloudBody.setX(Math.random());

        bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setStroke(Color.BLACK);
        bounds.setFill(Color.BLACK);

        getChildren().addAll(cloudBody);
        getChildren().addAll(bounds);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Image getImage() {
        return frame;
    }

    public double getX() {
        return 22.0;
    }

    public void setX(double x) {
    }
}

package Client.GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
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

//        cloudBody.setX(Math.random() * root.getWidth());
//        cloudBody.setY(Math.random() * root.getHeight() / 3);

    public ImageView cloudBody = new ImageView();
    private double GAP = 120;
    private double oscillationCenter;
    private Timeline animateTube;
    private int frames = 0;
    private Rectangle bounds;
    private Image frame;

    public Cloud(Image frame, Pane root, boolean animate) {
        this.frame = frame;
//        oscillationCenter = gapLocation.get();
        if (animate) {
            animateTube = new Timeline(new KeyFrame(Duration.millis(33), e -> {
//                gapLocation.set(25 * Math.cos(Math.PI / 60 * frames) + oscillationCenter);
                frames = (frames + 1) % 120;
            }));
            animateTube.setCycleCount(-1);
            animateTube.play();
        }
        cloudBody.setImage(this.frame);
        cloudBody.setX(2.5);
        cloudBody.setY(2.5);
//        cloudBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        this.bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setStroke(Color.BLACK);
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
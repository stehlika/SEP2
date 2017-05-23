package Client.Quidditch.GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import javafx.scene.image.ImageView;
/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Tower extends Group {

    public ImageView towerBody = new ImageView();
    private double GAP = 120;
    private double oscillationCenter;
    private Timeline animateTube;
    private int frames = 0;
    private Rectangle bounds;
    private Image frame;

    public Tower(Image frame, SimpleDoubleProperty gapLocation, Pane root, boolean animate) {
        this.frame = frame;
        oscillationCenter = gapLocation.get();
        if (animate) {
            animateTube = new Timeline(new KeyFrame(Duration.millis(33), e -> {
                gapLocation.set(25 * Math.cos(Math.PI / 60 * frames) + oscillationCenter);
                frames = (frames + 1) % 120;
            }));
            animateTube.setCycleCount(-1);
            animateTube.play();
        }
        towerBody.setImage(this.frame);
        towerBody.setX(2.5);
        towerBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        this.bounds = new Rectangle(frame.getWidth(), frame.getHeight());
        bounds.setStroke(Color.TRANSPARENT);
        bounds.setFill(Color.TRANSPARENT);
        bounds.setX(towerBody.getX());
        bounds.setY(towerBody.getY());
        bounds.rotateProperty().bind(towerBody.rotateProperty());

        getChildren().addAll(towerBody);
        getChildren().addAll(bounds);

    }


    public Rectangle getBounds() {
        return bounds;
    }
}

package Client.GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    private ImageView towerBody = new ImageView();
    private double GAP = 120;
    private double oscillationCenter;
    private Timeline animateTube;
    private int frames = 0;
    private Rectangle bounds;
    private Image frame;

    public Tower(Image frame,Pane root, boolean animate) {
        this.frame = frame;
        if (animate) {
            animateTube = new Timeline(new KeyFrame(Duration.millis(33), e -> {
                frames = (frames + 1) % 120;
            }));
            animateTube.setCycleCount(-1);
            animateTube.play();
        }
        towerBody.setImage(this.frame);
        towerBody.setX(2.5);
        towerBody.setY(2.5);
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

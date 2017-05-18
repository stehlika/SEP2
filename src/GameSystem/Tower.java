package GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.util.Duration;
import javafx.scene.shape.Ellipse;


import javafx.scene.image.ImageView;
/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Tower extends Group {

    public ImageView towerBody = new ImageView();
    private double GAP = 120;
    private Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTGRAY), new Stop(1, Color.GRAY)};
    private double oscillationCenter;
    private Timeline animateTube;
    private int frames = 0;
    private Ellipse bounds;
    private Image frame;

    public Tower(Image frame, SimpleDoubleProperty gapLocation, Pane root, boolean animate) {
        this.frame = frame;
        this.bounds = new Ellipse(frame.getWidth(), 50);

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

        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.BLACK);
        bounds.centerXProperty().bind(towerBody.translateXProperty().add(frame.getWidth() / 2.0));
        bounds.centerYProperty().bind(towerBody.translateYProperty().add(12.0));
        bounds.rotateProperty().bind(towerBody.rotateProperty());

        getChildren().addAll(towerBody);

    }


    public Ellipse getBounds() {
        return bounds;
    }
}

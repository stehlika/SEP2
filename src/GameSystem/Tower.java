package GameSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class Tower extends Group {

    public Rectangle lowerBody;
    private double GAP = 120;
    private Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN)};
    private LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    private Color c2 = new Color(84 / 255.0, 56 / 255.0, 71 / 255.0, 1.0);
    private double oscillationCenter;
    private Timeline animateTube;
    private int frames = 0;
    private int rotateOffset = 0;

    public Tower(SimpleDoubleProperty gapLocation, Pane root, boolean animate, boolean rotate) {
        if (rotate) {
            setRotationAxis(Rotate.Z_AXIS);
            setRotate(-25 + 50 * Math.random());
            rotateOffset = 80;
            setTranslateY(-40);
        }
        oscillationCenter = gapLocation.get();
        if (animate) {
            animateTube = new Timeline(new KeyFrame(Duration.millis(33), e -> {
                gapLocation.set(25 * Math.cos(Math.PI / 60 * frames) + oscillationCenter);
                frames = (frames + 1) % 120;
            }));
            animateTube.setCycleCount(-1);
            animateTube.play();
        }


        lowerBody = new Rectangle();
        lowerBody.widthProperty().bind(root.widthProperty().divide(12.3));
        lowerBody.heightProperty().bind(root.heightProperty().add(-GAP - 50 + rotateOffset).subtract(gapLocation));
        lowerBody.setX(2.5);
        lowerBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        lowerBody.setFill(lg1);
        lowerBody.setStroke(c2);

        getChildren().addAll(lowerBody);
    }

}

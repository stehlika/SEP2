package Client.Quidditch.GameSystem;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by adamstehlik on 17/05/2017.
 */
public class ScoreLabel extends Pane {

    Text status = new Text("Score: 0");

    public ScoreLabel(double x, double y) {
        setPrefHeight(100);
        setPrefWidth(300);
        setTranslateX(x - 310);
        setTranslateY(y + 10);
        setStyle("-fx-background-color: #CCC;"
                + "-fx-background-radius:20px");
        setOpacity(0.8);
        status.setTranslateY(50);
        status.setTranslateX(100);
        getChildren().addAll(status);
        status.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 22));
        status.setFill(new Color(107 / 255.0, 162 / 255.0, 252 / 255.0, 1.0));
    }

    public void setText(String message) {
        status.setText(message);

    }

}

class GameOverLabel extends ScoreLabel {

    public GameOverLabel(double x, double y) {
        super(x, y);
        setPrefWidth(400);
        setTranslateX(x - 150);
        status.setTranslateX(100);
    }
}

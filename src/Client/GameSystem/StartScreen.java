package Client.GameSystem;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by adamstehlik on 28/05/2017.
 */
public class StartScreen extends Pane {

    Text informationText = new Text("For start press ENTER");

    public StartScreen(double x, double y) {
        setPrefWidth(300);
        setPrefHeight(100);
        setTranslateX(x - 310);
        setTranslateY(y);
        setStyle("-fx-background-color: #2c3e50;" + "-fx-background-radius: 20px");
        setOpacity(1.0);
        informationText.setTranslateY(50);
        informationText.setTranslateX(100);
        informationText.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 22));
        informationText.setFill(new Color(236 / 255.0, 240 / 255.0, 241 / 255.0, 1.0));
        getChildren().add(informationText);

    }

    public void setText(String message) {
        informationText.setText(message);
    }


}

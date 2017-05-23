package me.chaseking.advancedjava.finalproject.pane;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;

/**
 * @author Chase King
 */
public class CarsPaneTextBox extends Pane {
    public static final CarsPaneTextBox LOADING = new CarsPaneTextBox("Loading cars...", 18, Color.GRAY);
    public static final CarsPaneTextBox NONE_TO_SHOW = new CarsPaneTextBox("No cars to show!", 16, Color.GRAY);
    public static final CarsPaneTextBox FAILED = new CarsPaneTextBox("Failed to load cars!", 16, FinalProject.RED);

    private CarsPaneTextBox(String text, int size, Paint fill){
        Rectangle rectangle = new Rectangle(0, 0, Car.WIDTH, Car.HEIGHT / 2);

        rectangle.setFill(fill);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);

        Label label = new Label(text);

        label.setFont(Font.font(size));
        label.setTextFill(Color.WHITE);

        getChildren().addAll(new StackPane(rectangle, label));
    }
}
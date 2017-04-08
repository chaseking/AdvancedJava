package me.chaseking.advancedjava.assignments.midterm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Chase King
 */
public class Midterm extends Application {
    public static void main(String[] args){
        Application.launch(Midterm.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Circle circle1 = new Circle(30, 30, 10, Color.WHITE);
        Circle circle2 = new Circle(90, 110, 10, Color.WHITE);
        Line line = new Line(50, 50, 100, 100);
        Text text = new Text();

        text.xProperty().bind(circle1.centerXProperty().add(circle2.centerXProperty()).divide(2));
        text.yProperty().bind(circle1.centerYProperty().add(circle2.centerYProperty()).divide(2).subtract(10)); //Slightly above the line

        Runnable updatePane = () -> {
            //Update label
            int dist = (int) Math.sqrt(Math.pow(circle1.getCenterX() - circle2.getCenterX(), 2) + Math.pow(circle1.getCenterY() - circle2.getCenterY(), 2));
            text.setText("Distance: " + dist);
        };

        updatePane.run();

        circle1.setOnMouseDragged(event -> {
            circle1.setCenterX(event.getX());
            circle1.setCenterY(event.getY());
            updatePane.run();
        });

        circle2.setOnMouseDragged(event -> {
            circle2.setCenterX(event.getX());
            circle2.setCenterY(event.getY());
            updatePane.run();
        });

        circle1.setStrokeWidth(1);
        circle1.setStroke(Color.BLACK);
        circle2.setStrokeWidth(1);
        circle2.setStroke(Color.BLACK);

        line.startXProperty().bind(circle1.centerXProperty());
        line.startYProperty().bind(circle1.centerYProperty());
        line.endXProperty().bind(circle2.centerXProperty());
        line.endYProperty().bind(circle2.centerYProperty());

        pane.getChildren().addAll(line, circle1, circle2, text);

        Scene scene = new Scene(pane, 400, 400);

        primaryStage.setTitle("Midterm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
package me.chaseking.advancedjava.labs.lab5;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Chase King
 */
public class Lab5 extends Application {
    public static void main(String[] args){
        Application.launch(Lab5.class, args);
    }

    private static final int FAN_RADIUS = 100;
    private static final int FAN_PADDING = 10;
    private static final int FAN_BLADES = 4;

    @Override
    public void start(Stage primaryStage) throws Exception {
        part4(primaryStage);
    }

    private void part1(Stage primaryStage){
        final int size = 400;
        Pane pane = new Pane();
        char[] chars = "Welcome to Java".toUpperCase().toCharArray();
        Point2D center = new Point2D(size / 2, size / 2);
        Font font = Font.font("Arial", FontWeight.EXTRA_BOLD, 42);

        for(int i = 0; i < chars.length; i++){
            int radius = 130;
            double angle = Math.toRadians(i * (360 / (chars.length + 1)));
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);
            Text text = new Text(x, y, String.valueOf(chars[i]));

            text.setFont(font);
            text.setRotate(Math.toDegrees((Math.PI / 2) + angle));
            pane.getChildren().add(text);
        }

        Scene scene = new Scene(pane, size, size);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 5 (Text)");
        primaryStage.show();
    }

    private void part2(Stage primaryStage){
        final int size = 350;

        GridPane pane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                TextField text = new TextField(Integer.toString((int)(Math.random() * 2)));
                text.setMinWidth(size / 10.0);
                text.setMaxWidth(size / 10.0);
                text.setMinHeight(size / 10.0);
                text.setMaxHeight(size / 10.0);
                pane.add(text, j, i);
            }
        }
        Scene scene = new Scene(pane, size, size);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(size);
        primaryStage.setMinHeight(size);
        primaryStage.setTitle("Lab Exercise 5 (Matrix)");
        primaryStage.show();
    }

    private void part3(Stage primaryStage){
        Pane pane = new Pane();
        int angle = 0;

        for(int row = 0; row < 2; row++){
            for(int column = 0; column < 2; column++){
                angle += ThreadLocalRandom.current().nextInt(30) + 10;

                Circle fan = new Circle(FAN_RADIUS);
                int x = FAN_PADDING * (row + 1) + FAN_RADIUS * (1 + row * 2);
                int y = FAN_PADDING * (column + 1) + FAN_RADIUS * (1 + column * 2);

                fan.setCenterX(x);
                fan.setCenterY(y);
                fan.setStroke(Color.BLACK);
                fan.setFill(Color.WHITE);
                pane.getChildren().add(fan);

                for(int i = 0; i < FAN_BLADES; i++){
                    Arc arc = new Arc(x, y, FAN_RADIUS - 15, FAN_RADIUS - 15, (angle + (i * 360 / FAN_BLADES)) % 360,360 / (FAN_BLADES * 3));

                    arc.setFill(Color.RED);
                    arc.setType(ArcType.ROUND);
                    pane.getChildren().add(arc);
                }
            }
        }

        //Apply the scene
        int size = FAN_PADDING + FAN_RADIUS*2 + FAN_PADDING + FAN_RADIUS*2 + FAN_PADDING;
        Scene scene = new Scene(pane, size, size);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 5 (Fan)");
        primaryStage.show();
    }

    private void part4(Stage primaryStage){
        BallDropPane pane = new BallDropPane();
        Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());

        primaryStage.setMinWidth(pane.getWidth() / 2);
        primaryStage.setMinHeight(pane.getWidth() / 2);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 5 (Bean Machine)");
        primaryStage.show();
    }
}
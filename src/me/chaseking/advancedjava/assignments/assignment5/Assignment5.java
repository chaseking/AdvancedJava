package me.chaseking.advancedjava.assignments.assignment5;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Chase King
 */
public class Assignment5 extends Application {
    private static List<Point2D> vertices = new ArrayList<>();
    private static Point2D checkerPoint;

    public static void main(String[] args){
        Application.launch(Assignment5.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //polygon(primaryStage);
        fan(primaryStage);
    }

    private void polygon(Stage primaryStage){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.print("Enter five points: ");

            //TEST VALUES: 100 50 200 150 30 150 40 250 100 100
            String[] split = scanner.nextLine().split(" ", 10);

            for(int i = 0; i < split.length; i += 2){
                Point2D point = new Point2D(Double.parseDouble(split[i]), Double.parseDouble(split[i + 1]));

                if(i == split.length - 2){
                    checkerPoint = point;
                } else {
                    vertices.add(point);
                }
            }
        } catch(Exception e){
            System.out.println("Invalid input!");
            polygon(primaryStage);
            return;
        }

        Pane pane = new Pane();

        //Polygon
        Polygon polygon = new Polygon();

        polygon.setFill(Color.WHITE);
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(5);

        for(Point2D vertex : vertices){
            polygon.getPoints().add(vertex.getX());
            polygon.getPoints().add(vertex.getY());
        }

        pane.getChildren().add(polygon);

        //Circle checker point
        Circle circle = new Circle(checkerPoint.getX(), checkerPoint.getY(), 5, Color.RED);

        circle.setFill(Color.RED);
        pane.getChildren().add(circle);

        //Text
        Text text = new Text();

        if(polygon.contains(checkerPoint)){
            text.setText("Polygon contains (" + checkerPoint.getX() + ", " + checkerPoint.getY() + ").");
        } else {
            text.setText("Polygon does not contain (" + checkerPoint.getX() + ", " + checkerPoint.getY() + ").");
        }

        text.setX(100);
        text.setY(250);
        text.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(text);

        //Apply the scene
        Scene scene = new Scene(pane, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Assignment 5");
        primaryStage.show();
    }

    private static final int FAN_RADIUS = 100;
    private static final int FAN_PADDING = 10;
    private static final int FAN_BLADES = 4;

    private void fan(Stage primaryStage){
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
        primaryStage.setTitle("Assignment 5 - Part 2");
        primaryStage.show();
    }
}
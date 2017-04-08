package me.chaseking.advancedjava.assignments.assignment6;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Chase King
 */
public class Assignment6 extends Application {
    public static void main(String[] args){
        Application.launch(Assignment6.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        part1(primaryStage);
    }

    private void part1(Stage primaryStage){
        FanPane fan = new FanPane();

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            fan.run();
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        Button pause = new Button("Pause");
        Button resume = new Button("Resume");
        Button reverse = new Button("Reverse");

        pause.setOnAction(event -> animation.pause());
        resume.setOnAction(event -> animation.play());
        reverse.setOnAction(event -> fan.reverse());

        HBox controlBox = new HBox(20, pause, resume, reverse);

        controlBox.setAlignment(Pos.CENTER);

        //Build the window
        GridPane pane = new GridPane();

        pane.setVgap(50);
        pane.setAlignment(Pos.CENTER);
        pane.add(fan, 0, 0);
        pane.add(controlBox, 0, 1);

        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setTitle("Assignment 6.1 (Fan)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void part2(Stage primaryStage){
        Pane pane = new Pane();
        Polygon polygon = new Polygon(40, 20,
                70, 40,
                60, 80,
                200, 200,
                20, 60);

        polygon.setFill(Color.LIGHTGRAY);
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        pane.getChildren().add(polygon);

        Text text = new Text();

        pane.getChildren().add(text);

        pane.setOnMouseMoved(event -> {
            text.setX(event.getX());
            text.setY(event.getY());
            text.setText("Mouse point is " + (polygon.contains(event.getX(), event.getY()) ? "inside" : "outside") + " the polygon.");
        });

        Scene scene = new Scene(pane, 400, 300);

        primaryStage.setTitle("Assignment 6.2 (Polygon)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class FanPane extends Pane implements Runnable {
        private static final int RADIUS = 100;
        private static final int BLADES = 4;

        private Arc[] arcs = new Arc[BLADES];

        private int speed = 1;

        public FanPane(){
            super();

            Circle fan = new Circle(RADIUS);

            fan.setCenterX(RADIUS);
            fan.setCenterY(RADIUS);
            fan.setStroke(Color.BLACK);
            fan.setFill(Color.WHITE);
            getChildren().add(fan);

            for(int i = 0; i < BLADES; i++){
                Arc arc = new Arc(fan.getCenterX(), fan.getCenterY(), RADIUS * 0.9, RADIUS * 0.9, (i * 360 / BLADES) % 360, 360 / (BLADES * 3));

                arc.setType(ArcType.ROUND);
                arc.setFill(Color.RED);
                arc.setStrokeWidth(1);
                arc.setStroke(Color.GRAY);
                arcs[i] = arc;
                getChildren().add(arc);
            }
        }

        @Override
        public void run(){
            for(Arc arc : arcs){
                arc.setStartAngle(arc.getStartAngle() + speed);
            }
        }

        public void reverse(){
            speed *= -1;
        }
    }
}
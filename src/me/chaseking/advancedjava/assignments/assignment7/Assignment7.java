package me.chaseking.advancedjava.assignments.assignment7;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public class Assignment7 extends Application {
    public static void main(String[] args){
        Application.launch(Assignment7.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        part1(primaryStage);
    }

    private void part1(Stage primaryStage){
        FanControlPane fan = new FanControlPane();
        Scene scene = new Scene(fan, 500, 300);

        primaryStage.setTitle("Assignment 7.1 (Fan Slider)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void part2(Stage primaryStage){
        List<FanControlPane> fans = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            fans.add(new FanControlPane());
        }

        Button startAll = new Button("Start All");
        Button stopAll = new Button("Stop All");

        startAll.setOnAction(event -> {
            for(FanControlPane fan : fans){
                fan.start();
            }
        });

        stopAll.setOnAction(event -> {
            for(FanControlPane fan : fans){
                fan.stop();
            }
        });

        HBox fansBox = new HBox(10);
        fansBox.getChildren().addAll(fans);
        fansBox.setAlignment(Pos.CENTER);

        HBox masterControlBox = new HBox(20, startAll, stopAll);
        masterControlBox.setAlignment(Pos.CENTER);

        VBox pane = new VBox(30, fansBox, masterControlBox);
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane, 800, 400);

        primaryStage.setTitle("Assignment 7.2 (Multiple Fans)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class FanControlPane extends VBox {
        private static final int RADIUS = 100;
        private static final int BLADES = 4;

        private Arc[] arcs = new Arc[BLADES];

        private boolean reversed = false;

        private Timeline animation;

        public FanControlPane(){
            super(30);

            Pane fanPane = new Pane();
            Circle fan = new Circle(RADIUS);

            fan.setCenterX(RADIUS);
            fan.setCenterY(RADIUS);
            fan.setStroke(Color.BLACK);
            fan.setFill(Color.WHITE);
            fanPane.getChildren().add(fan);

            for(int i = 0; i < BLADES; i++){
                Arc arc = new Arc(fan.getCenterX(), fan.getCenterY(), RADIUS * 0.9, RADIUS * 0.9, (i * 360 / BLADES) % 360, 360 / (BLADES * 3));

                arc.setType(ArcType.ROUND);
                arc.setFill(Color.RED);
                arc.setStrokeWidth(1);
                arc.setStroke(Color.GRAY);
                arcs[i] = arc;
                fanPane.getChildren().add(arc);
            }

            Slider slider = new Slider(1, 10, 1);

            slider.setMaxWidth(300);

            animation = new Timeline(new KeyFrame(Duration.millis(10), event -> {
                for(Arc arc : arcs){
                    arc.setStartAngle(arc.getStartAngle() + (reversed ? -1 : 1) * slider.getValue());
                }
            }));

            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();

            Button pause = new Button("Pause");
            Button resume = new Button("Resume");
            Button reverse = new Button("Reverse");

            pause.setOnAction(event -> animation.pause());
            resume.setOnAction(event -> animation.play());
            reverse.setOnAction(event -> reversed = !reversed);

            HBox controlBox = new HBox(20, pause, resume, reverse);

            controlBox.setAlignment(Pos.CENTER);

            setAlignment(Pos.CENTER);
            setPadding(new Insets(5));
            setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
            getChildren().addAll(fanPane, controlBox, slider);
        }

        public void start(){
            animation.play();
        }

        public void stop(){
            animation.stop();
        }
    }
}
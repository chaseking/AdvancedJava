package me.chaseking.advancedjava.labs.lab6;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.chaseking.advancedjava.labs.lab5.BallDropPane;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.BiFunction;

/**
 * @author Chase King
 */
public class Lab6 extends Application {
    public static void main(String[] args){
        Application.launch(Lab6.class, args);
    }

    enum MathFunction {
        ADD("Add", (a, b) -> a + b),
        SUBTRACT("Subtract", (a, b) -> a - b),
        MULTIPLY("Multiply", (a, b) -> a * b),
        DIVIDE("Divide", (a, b) -> a / b);

        private final String name;
        private final BiFunction<Double, Double, Double> executor;

        MathFunction(String name, BiFunction<Double, Double, Double> executor){
            this.name = name;
            this.executor = executor;
        }

        public String getName(){
            return name;
        }

        public double execute(double a, double b){
            return executor.apply(a, b);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        part4(primaryStage);
    }

    private void part1(Stage primaryStage){
        TextField number1 = new TextField("0");
        TextField number2 = new TextField("0");
        TextField result = new TextField();

        result.setEditable(false);

        //Input box
        HBox boxInput = new HBox(10,
                new Label("Number 1:"),
                number1,
                new Label("Number 2:"),
                number2);

        boxInput.setAlignment(Pos.CENTER);

        //Calculate box
        HBox boxCalculate = new HBox(20);

        boxCalculate.setAlignment(Pos.CENTER);

        DecimalFormat doubleFormat = new DecimalFormat("#.##");

        doubleFormat.setRoundingMode(RoundingMode.CEILING);

        for(MathFunction function : MathFunction.values()){
            Button button = new Button(function.getName());

            button.setOnAction(event -> {
                double value = function.execute(Double.parseDouble(number1.getText()), Double.parseDouble(number2.getText()));

                if(value == (int) value){
                    //It is an integer
                    result.setText(String.valueOf((int) value));
                } else {
                    //Round up
                    result.setText(doubleFormat.format(value));
                }
            });

            boxCalculate.getChildren().add(button);
        }

        HBox boxResult = new HBox(20, new Label("Result:"), result);

        boxResult.setAlignment(Pos.CENTER);

        //Put them all together
        GridPane pane = new GridPane();

        pane.setAlignment(Pos.CENTER);
        pane.setVgap(20);
        pane.add(boxInput, 0, 0);
        pane.add(boxCalculate, 0, 1);
        pane.add(boxResult, 0, 2);

        Scene scene = new Scene(pane, 500, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 6.1 (Calculator)");
        primaryStage.show();
    }

    private void part2(Stage primaryStage){
        Pane pane = new Pane();

        pane.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                Circle circle = new Circle(event.getX(), event.getY(), 8);

                circle.setOnMouseClicked(circleEvent -> {
                    if(circleEvent.getButton() == MouseButton.SECONDARY){
                        pane.getChildren().remove(circle);
                    }
                });

                pane.getChildren().add(circle);
            }
        });

        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 6.2 (Circles)");
        primaryStage.show();
    }

    private void part3(Stage primaryStage){
        Pane pane = new Pane();
        Text text = new Text(300, 200, "Testing");

        pane.getChildren().add(text);

        final int speed = 5;
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            text.setX(text.getX() == pane.getWidth() ? 0 : text.getX() + speed);
            //text.setY(text.getY() == pane.getHeight() ? 0 : text.getY() + (speed / 2));
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        pane.setOnMouseClicked(event -> {
            if(animation.getStatus() == Animation.Status.RUNNING){
                animation.pause();
            } else {
                animation.play();
            }
        });

        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 6.3 (Moving Text)");
        primaryStage.show();
    }

    private void part4(Stage primaryStage){
        BallDropPane pane = new BallDropPane();
        Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());

        pane.dropBall();

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 6.4 (Ball Drop)");
        primaryStage.show();
    }
}
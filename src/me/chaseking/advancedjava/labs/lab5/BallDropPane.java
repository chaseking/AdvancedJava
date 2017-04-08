package me.chaseking.advancedjava.labs.lab5;

import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import me.chaseking.advancedjava.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public class BallDropPane extends Pane {
    private static final int SLOTS = 8;

    private List<Circle> pegs = new ArrayList<>();
    private List<DroppedBall> balls = new ArrayList<>();

    private Line baseLine;
    private DoubleProperty distanceProperty;

    public BallDropPane(){
        setWidth(600);
        setHeight(600);
        paintBeanMachine();

        /*
        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), event -> {

        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
        */
    }

    private void paintBeanMachine(){
        // Draw lower line
        baseLine = new Line();
        baseLine.startXProperty().bind(widthProperty().multiply(0.2));
        baseLine.startYProperty().bind(heightProperty().multiply(0.8));
        baseLine.endXProperty().bind(widthProperty().multiply(0.8));
        baseLine.endYProperty().bind(heightProperty().multiply(0.8));

        // distance gap per circle
        distanceProperty = new SimpleDoubleProperty();
        distanceProperty.bind(baseLine.endXProperty().subtract(baseLine.startXProperty()).divide(SLOTS));

        // Draw triangle circles
        pegs = new ArrayList<>(factorialSum(SLOTS - 1));
        int index = 0;

        for(int row = 1; row < SLOTS; row++){
            double x = baseLine.getStartX() + (row * distanceProperty.get() * 0.50) + distanceProperty.get() / 2;
            double y = baseLine.getStartY() - (distanceProperty.get() * row) - distanceProperty.get() / 2;

            for(int column = 0; column < SLOTS - row; column++){
                Circle peg = new Circle(x, y, 1);

                //peg.centerXProperty().bind();
                //peg.centerYProperty().bind();
                peg.radiusProperty().bind(widthProperty().multiply(0.012));
                peg.setFill(Color.GREEN);
                pegs.add(peg);

                peg.setOnMouseClicked(e -> System.out.println(e.getX()));
                x += distanceProperty.get();
            }
        }

        double distance = (distanceProperty.get() * 1.5) - pegs.get(0).getRadius();
        // Connect the base of the triangle with lowerLine
        // NOT including left most and right most line
        Line[] lines = new Line[SLOTS - 1];

        for(int i = 0; i < SLOTS - 1; i++){
            double x1 = pegs.get(i).getCenterX() + pegs.get(i).getRadius() * Math.sin(Math.PI);
            double y1 = pegs.get(i).getCenterY() - pegs.get(i).getRadius() * Math.cos(Math.PI);
            lines[i] = new Line(
                    x1, y1, // start point
                    x1, y1 + distance); // end point

        }
        // Draw right most and left most most line
        Line[] sides = new Line[6];
        sides[0] = new Line(
                baseLine.getEndX(), baseLine.getEndY(),
                baseLine.getEndX(), baseLine.getEndY() - distance);
        sides[1] = new Line(
                baseLine.getStartX(), baseLine.getStartY(),
                baseLine.getStartX(), baseLine.getStartY() - distance);

        // Draw left side line
        for(int i = 2; i < 4; i++){
            double x = pegs.get(pegs.size() - i).getCenterX();
            double y = pegs.get(pegs.size() - i).getCenterY() - distance;
            sides[i] = new Line(x, y, sides[i - 2].getEndX(), sides[i - 2].getEndY());
        }

        // Draw the upper 2 lines on top of the triangle
        for(int i = 4; i < sides.length; i++){
            sides[i] = new Line(
                    sides[i-2].getStartX(), sides[i-2].getStartY(),
                    sides[i-2].getStartX(), sides[i-2].getStartY() - (distance * 0.6)
            );
        }

        getChildren().add(baseLine);
        getChildren().addAll(pegs);
        getChildren().addAll(lines);
        getChildren().addAll(sides);
    }

    private static int factorialSum(int num){
        int sum = 0;

        for(int i = 1; i <= num; i++){
            sum += i;
        }

        return sum;
    }

    private double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void dropBall(){
        int x = (int) (widthProperty().get() / 2);
        int y = 80;
        DroppedBall ball = new DroppedBall(x, y);

        balls.add(ball);
        getChildren().add(ball);

        //Animation
        Polyline path = new Polyline();

        //Start point
        path.getPoints().addAll((double) x, (double) y);

        Circle lastCollided = null;
        int targetX = 0;

        while(true){
            if(lastCollided == null){
                for(Circle peg : pegs){
                    if(dist(x, y, peg.getCenterX(), peg.getCenterY()) <= peg.getRadius() + ball.getRadius()){
                        //Collided with this peg; choose a random direction to go
                        lastCollided = peg;
                        double offset = Math.floor(distanceProperty.get() / 2.0);

                        if(Main.RANDOM.nextBoolean()){
                            x += 1;
                            targetX = (int) (x + offset);
                        } else {
                            x -= 1;
                            targetX = (int) (x - offset);
                        }
                    }
                }
            } else {
                if(x < targetX){
                    x += 1;
                } else if(x > targetX){
                    x -= 1;
                } else {
                    lastCollided = null;
                    targetX = 0;
                }
            }

            y += 1;

            boolean stop = false;

            for(DroppedBall b : balls){
                if(!b.equals(ball)){
                    if(dist(x, y, b.doneX, b.doneY) <= b.getRadius() + ball.getRadius()){
                        stop = true;
                    }
                }
            }

            if(y == baseLine.getEndY() - ball.getRadius()){
                stop = true;
            }

            if(stop){
                break;
            }

            //Add point
            path.getPoints().addAll((double) x, (double) y);
        }

        ball.doneX = x;
        ball.doneY = y;

        PathTransition animation = new PathTransition(Duration.seconds(2), path, ball);

        animation.play();

        animation.setOnFinished(event -> {
            if(balls.size() < 20){
                dropBall();
            }
        });
    }

    static class DroppedBall extends Circle {
        private int doneX;
        private int doneY;

        public DroppedBall(int x, int y){
            super(x, y, 5, Color.RED);

            setStroke(Color.BLACK);
            setStrokeWidth(2);
        }
    }
}
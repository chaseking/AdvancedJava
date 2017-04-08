package me.chaseking.advancedjava.labs.lab5;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

/**
 * @author Chase King
 */
public class BallDropPane_2 extends Pane {
    private static final int SLOTS = 8;

    private double mWidth;
    private double mHeight;

    /* positions */
    private double centerTopX;
    private double centerTopY;

    private double ballRadius;
    private ArrayList<Circle> balls = new ArrayList<>();

    public BallDropPane_2(){
        super();
        mWidth = 800;
        mHeight = 800;
        paintBeanMachine();
        dropBall();
    }

    public double getW() {
        return mWidth;
    }

    public void setW(double width) {
        paintBeanMachine();
        mWidth = width;
    }

    public double getH() {
        return mHeight;
    }

    public void setH(double height){
        paintBeanMachine();
        mHeight = height;
    }

    private void paintBeanMachine(){
        // Draw lower line
        //Line baseLine = new Line(mWidth * 0.2, mHeight * 0.8, mWidth * 0.8, mHeight * 0.8);
        Line baseLine = new Line();

        baseLine.startXProperty().bind(widthProperty().multiply(0.2));
        baseLine.startYProperty().bind(heightProperty().multiply(0.8));
        baseLine.endXProperty().bind(widthProperty().multiply(0.8));
        baseLine.endYProperty().bind(heightProperty().multiply(0.8));

        // distance gap per circle
        double distance = (baseLine.getEndX() - baseLine.getStartX()) / SLOTS;
        ballRadius = distance / 2;

        //Draw pegs
        DoubleBinding dist = baseLine.endXProperty().subtract(baseLine.startXProperty()).divide(SLOTS);
        Circle[] pegs = new Circle[factorialSum(SLOTS)];
        int index = 0;

        for(int row = 0; row < SLOTS; row++){
            DoubleBinding y = baseLine.startYProperty().subtract(heightProperty().multiply(0.2).divide(row + 1));

            for(int col = 0; col < SLOTS - row; col++){
                Circle peg = new Circle(5, Color.BLUE);

                peg.centerXProperty().bind(baseLine.startXProperty()
                        .add(dist.multiply(row).multiply(0.5))
                        .add(dist.divide(2))
                        .add(dist.multiply(row)));
                peg.centerYProperty().bind(y);

                //peg = new Circle(x, y, mWidth * 0.012, Color.BLUE);
                System.out.println(index);
                pegs[index++] = peg;

                Line line = new Line();
                line.startXProperty().bind(peg.centerXProperty());
                line.startYProperty().bind(peg.centerYProperty());
                line.endXProperty().bind(peg.centerXProperty());
                line.endYProperty().bind(baseLine.startYProperty());
                getChildren().add(line);
            }
        }

        /*
        for(int i = 1; i <= SLOTS; i++){
            double x = baseLine.getStartX() + (i * distance * 0.50) + distance / 2;
            double y = baseLine.getStartY() - (distance * i) - distance / 2;

            for(int j = 0; j <= SLOTS - i; j++){
                Circle peg = new Circle(5, Color.BLUE);
                DoubleBinding dist = baseLine.endXProperty().subtract(baseLine.startXProperty()).divide(SLOTS);

                peg.centerXProperty().bind(baseLine.startXProperty()
                        .add(dist.multiply(i).multiply(0.5))
                        .add(dist.divide(2)));
                peg.centerYProperty().bind(baseLine.startYProperty()
                        .subtract(dist.multiply(i))
                        .subtract(dist.divide(2)));

                //peg = new Circle(x, y, mWidth * 0.012, Color.BLUE);
                System.out.println(index);
                pegs[index++] = peg;
                x += distance;
            }
        }
        */

        distance = distance + (distance / 2) - pegs[0].getRadius();
        // Connect the base of the triangle with lowerLine
        // NOT including left most and right most line
        Line[] lines = new Line[SLOTS - 1];
        for (int i = 0; i < SLOTS - 1; i++) {
            double x1 = pegs[i].getCenterX() + pegs[i].getRadius() * Math.sin(Math.PI);
            double y1 = pegs[i].getCenterY() - pegs[i].getRadius() * Math.cos(Math.PI);
            lines[i] = new Line(x1, y1, x1, y1 + distance);

        }
        // Draw right most and left most most line
        Line[] sides = new Line[6];
        sides[0] = new Line(
                baseLine.getEndX(), baseLine.getEndY(),
                baseLine.getEndX(), baseLine.getEndY() - distance);
        sides[1] = new Line(
                baseLine.getStartX(), baseLine.getStartY(),
                baseLine.getStartX(), baseLine.getStartY() - distance);

        //Draw left side line
        /*
        for(int i = 2; i < 4; i++){
            double x = pegs[pegs.length - i].getCenterX();
            double y = pegs[pegs.length - i].getCenterY() - distance;
            sides[i] = new Line(x, y, sides[i - 2].getEndX(), sides[i - 2].getEndY());
        }
        */

        // Draw the upper 2 lines on top of the triangle
        /*
        for (int i = 4; i < sides.length; i++){
            sides[i] = new Line(
                    sides[i-2].getStartX(), sides[i-2].getStartY(),
                    sides[i-2].getStartX(), sides[i-2].getStartY() - (distance * 0.6)
            );
        }
        */

        getChildren().addAll(baseLine);
        getChildren().addAll(pegs);
        //getChildren().addAll(lines);
        //getChildren().addAll(sides);
    }

    private void dropBall(){
        balls.add(new Circle(420, 180, ballRadius / 2 / 2));
        getChildren().addAll(balls);
    }

    private void generatePath() {
        Polyline linePath = new Polyline();

        for(int i = 0; i < SLOTS * 2; i++){

        }
    }

    private static int factorialSum(int num){
        int sum = 0;

        for(int i = 1; i <= num; i++){
            sum += i;
        }

        return sum;
    }
}
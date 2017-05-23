package me.chaseking.advancedjava.assignments.assignment12;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chase King
 *
 * Write a program that displays the number of students in
 * each department in a pie chart and a bar chart.
 *
 * The number of students for each department can be obtained from the Student table using the following SQL statement:
 * select deptId, count(*) from Student where deptId is not null group by deptId;
 */
public class Assignment12 extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane pane = new FlowPane();

        pane.setAlignment(Pos.CENTER);

        Label label = new Label("Loading information...");
        pane.getChildren().add(label);

        Scene scene = new Scene(pane, 1000, 450);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Assignment 12");
        primaryStage.show();

        //Connect to database
        Connection connection;

        try{
            //Load driver
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
        } catch(Exception e){
            e.printStackTrace();
            label.setText("Status: Error connecting. (" + e.getClass().getName() + ": " + e.getMessage() + ")");
            return;
        }

        //Load data
        Map<String, Integer> departments = new HashMap<>();
        int total = 0;

        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT deptId, count(*) FROM Student WHERE deptId is not null GROUP by deptId;");

            while(rs.next()){
                int count = rs.getInt("count(*)");

                departments.put(rs.getString("deptId"), count);
                total += count;
            }
        } catch(Exception e){
            e.printStackTrace();
            label.setText("Status: Error loading data. (" + e.getClass().getName() + ": " + e.getMessage() + ")");
            return;
        }

        //Display data
        PieChart pieChart = new PieChart();
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis("Count", 0, 6, 1));
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
        barChartSeries.setName("Student");
        barChart.getData().add(barChartSeries);

        for(Map.Entry<String, Integer> entry : departments.entrySet()){
            pieChart.getData().add(new PieChart.Data(entry.getKey(), (int) (entry.getValue() / (double) total * 100)));
            barChartSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        pane.getChildren().clear();
        pane.getChildren().addAll(pieChart, barChart);
    }
}
package me.chaseking.advancedjava.labs.lab12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Chase King
 */
public class Lab12 extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(new DBConnectionPane(), 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 12-2");
        primaryStage.show();
    }
}
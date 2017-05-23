package me.chaseking.advancedjava.labs.lab14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.chaseking.advancedjava.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Chase King
 */
public class Lab14_Client extends Application {
    private Socket socket;
    private Label label;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        label = new Label("Connecting to server...");

        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font(18));
        Scene scene = new Scene(label, 420, 200);

        primaryStage.setTitle("Lab14 - Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(1));

        //Start server
        connect();
        /*
        new Thread(() -> {
            int time = -1;

            while(true){
                if(socket == null){
                    //No connection
                    if(time == -1){
                        if(connect()){
                            //Successfully connected
                            continue;
                        } else {
                            //Failed to connect
                            time = 5;
                        }
                    }

                    setMessage("Failed to connect. Trying again in " + time + " seconds.");
                    time -= 1;

                    if(time == 0){
                        time = -1;
                    }
                } else {
                    //Currently a connection
                    //Check if it is still active
                    try{
                        if(socket.getInputStream().read() == -1){
                            socket = null;
                            setMessage("Dropped connection.");
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }

                Main.sleep(1000);
            }
        }).start();
        */
    }

    public boolean connect(){
        try{
            socket = new Socket("localhost", Lab14.PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());

            setMessage("Connected: " + in.readUTF());
            return true;
        } catch(IOException e){
            //e.printStackTrace();
            socket = null;
            return false;
        }
    }

    public void setMessage(String message){
        Platform.runLater(() -> this.label.setText(message));
    }
}
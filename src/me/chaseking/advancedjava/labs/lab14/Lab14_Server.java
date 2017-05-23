package me.chaseking.advancedjava.labs.lab14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author Chase King
 */
public class Lab14_Server extends Application {
    private ServerSocket server;
    private TextArea logMessages;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        logMessages = new TextArea();

        Scene scene = new Scene(logMessages, 400, 300);

        primaryStage.setTitle("Lab14 - Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try{
                server.close();
                server = null;
            } catch(IOException e){
                e.printStackTrace();
            }

            System.exit(0);
        });

        //Start server
        new Thread(() -> {
            try{
                server = new ServerSocket(Lab14.PORT);
                log("Server started at " + new Date().toString());

                while(server != null){
                    Socket socket = server.accept();
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    //Update clients
                    int numClients = 0;

                    try(RandomAccessFile file = getFile()){
                        if(file.length() > 0){
                            file.seek(0);
                            numClients = file.readInt();
                        }

                        numClients++;
                        file.seek(0);
                        file.writeInt(numClients);
                        file.close();
                    }

                    out.writeUTF("Client #" + numClients + ".");
                    log("New client (#" + numClients + "): " + socket.getInetAddress().getHostAddress());

                    new Thread(() -> {
                        while(socket.isConnected());
                        System.out.println("Socket closed.");
                    }).start();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }, "Server Thread").start();
    }

    private RandomAccessFile getFile(){
        try{
            return new RandomAccessFile(new File("/Users/chase/Desktop/lab14.txt"), "rw");
        } catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private void log(String message){
        Platform.runLater(() -> {
            if(!logMessages.getText().isEmpty()){
                logMessages.appendText("\n");
            }

            logMessages.appendText(message);
        });
    }
}
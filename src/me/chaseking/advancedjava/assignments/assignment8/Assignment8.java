package me.chaseking.advancedjava.assignments.assignment8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Chase King
 */
public class Assignment8 extends Application {
    public static void main(String[] args){
        launch(args);
    }

    private RandomAccessFile inout;
    private AddressBookPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File(System.getProperty("user.home") + "/Desktop", "addressbook.dat");

        if(!file.exists()){
            file.createNewFile();
        }

        inout = new RandomAccessFile(file, "rw");
        pane = new AddressBookPane(this);

        Scene scene = new Scene(pane, 515, 200);

        primaryStage.setTitle("Assignment 8 - Address Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Contact getFirst(){
        try{
            if(inout.length() > 0){
                inout.seek(0);

                return new Contact(inout);
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public Contact getLast(){
        try{
            long length = inout.length();

            if(length > 0){
                inout.seek(length - Contact.TOTAL_SIZE); //Position before the last element

                return new Contact(inout);
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public Contact getPrevious(){
        try{
            if(inout.length() > 0 && inout.getFilePointer() > Contact.TOTAL_SIZE){
                inout.seek(inout.getFilePointer() - Contact.TOTAL_SIZE * 2);

                return new Contact(inout);
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public Contact getNext(){
        try{
            if(inout.length() > 0){
                return new Contact(inout);
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public void addContact(){
        try{
            Contact.serialize(inout, pane.name.getText(), pane.street.getText(), pane.city.getText(), pane.state.getText(), pane.zip.getText());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void updateContact(){
        try{
            inout.seek(inout.getFilePointer() - Contact.TOTAL_SIZE);
            Contact.serialize(inout, pane.name.getText(), pane.street.getText(), pane.city.getText(), pane.state.getText(), pane.zip.getText());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
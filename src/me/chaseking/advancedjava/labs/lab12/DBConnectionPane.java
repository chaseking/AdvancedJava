package me.chaseking.advancedjava.labs.lab12;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Chase King
 */
public class DBConnectionPane extends BorderPane {
    private Connection connection;

    public DBConnectionPane(){
        Label connectionLabel = new Label("Status: No connection.");
        setTop(connectionLabel);

        GridPane gridPane = new GridPane();
        setCenter(gridPane);
        gridPane.setVgap(10);

        //JDBC driver
        gridPane.add(new Label("JDBC Driver"), 0, 0);
        ComboBox<String> driverDropdown = new ComboBox<>();
        driverDropdown.getItems().add("com.mysql.jdbc.Driver");
        driverDropdown.getItems().add("sun.jdbc.odbc.JdbcOdbcDriver");
        driverDropdown.getItems().add("oracle.jdbc.driver.OracleDriver");
        gridPane.add(driverDropdown, 1, 0);

        //Database URL
        gridPane.add(new Label("Database URL"), 0, 1);
        ComboBox<String> urlDropdown = new ComboBox<>();
        urlDropdown.getItems().add("jdbc:mysql://localhost/javabook");
        gridPane.add(urlDropdown, 1, 1);

        //Username
        gridPane.add(new Label("Username"), 0, 2);
        TextField username = new TextField();
        username.setPromptText("Enter username.");
        gridPane.add(username, 1, 2);

        //Password
        gridPane.add(new Label("Username"), 0, 3);
        PasswordField password = new PasswordField();
        password.setPromptText("Enter password.");
        gridPane.add(password, 1, 3);

        //Connect button
        Button connectButton = new Button("Connect to Database");
        setBottom(connectButton);
        setAlignment(connectButton, Pos.CENTER);
        connectButton.setOnAction(event -> {
            try{
                //Load driver
                Class.forName(driverDropdown.getValue());

                connection = DriverManager.getConnection(urlDropdown.getValue(), username.getText(), password.getText());

                connectionLabel.setText("Status: Connected to " + urlDropdown.getValue());
            } catch(Exception e){
                e.printStackTrace();
                connectionLabel.setText("Status: Error connecting. (" + e.getClass().getName() + ": " + e.getMessage() + ")");
            }
        });
    }

    public Connection getConnection(){
        return connection;
    }
}
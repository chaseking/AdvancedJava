package me.chaseking.advancedjava.finalproject.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.RentInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Chase King
 */
public class LookupUserPane extends BorderPane {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    private TextField name;
    private Label info;

    public LookupUserPane(Stage stage){
        setPadding(new Insets(FinalProject.PADDING));
        setBackground(FinalProject.GRAY_BG);

        //Top
        name = new TextField();
        name.setPromptText("Enter name.");

        Button searchButton = FinalProject.button("Search", event -> search());
        HBox top = new HBox(20, FinalProject.label("Enter name:"), name, searchButton);

        top.setAlignment(Pos.CENTER);
        setTop(top);

        Button closeButton = FinalProject.button("Close", event -> stage.close());
        FlowPane bottom = new FlowPane(closeButton);
        bottom.setPadding(getPadding());
        bottom.setHgap(20);
        bottom.setAlignment(Pos.CENTER);
        setBottom(bottom);
    }

    public void search(){
        if(name.getText() == null || name.getText().isEmpty()){
            setCenter(FinalProject.label("Please enter a valid name!"));
        } else {
            String driversLicense = "???";

            //Lookup user
            try(PreparedStatement statement = FinalProject.get().getDatabase().getConnection().prepareStatement("select * from Users" +
                    " where name = ?")){
                statement.setString(1, name.getText());
                ResultSet rs = statement.executeQuery();

                if(rs.next()){
                    driversLicense = rs.getString("driversLicense");
                }
            } catch(SQLException e){
                e.printStackTrace();
            }

            VBox infoBox = new VBox(5);
            setCenter(infoBox);

            infoBox.getChildren().add(FinalProject.label("Name: " + name.getText()));
            infoBox.getChildren().add(FinalProject.label("Drivers license: " + driversLicense));

            //Lookup rent history
            infoBox.getChildren().add(FinalProject.label("Rent history:"));
            try(PreparedStatement statement = FinalProject.get().getDatabase().getConnection().prepareStatement("select * from Transactions" +
                    " where name = ?" +
                    " order by dateRented desc, dateReturned desc")){
                statement.setString(1, name.getText());
                ResultSet rs = statement.executeQuery();

                if(rs.isLast()){
                    infoBox.getChildren().add(FinalProject.label("   Nothing to show!"));
                } else {
                    while(rs.next()){
                        RentInfo rent = new RentInfo(rs);
                        Car car = FinalProject.get().getCar(rs.getInt("id"));

                        infoBox.getChildren().add(FinalProject.label("   " + (car == null ? "Unknown Car" : car.getType().getName()) + ": " + rent.getDateString(), 12));
                    }
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
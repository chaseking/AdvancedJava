package me.chaseking.advancedjava.finalproject.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.RentInfo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Chase King
 */
public class ManageCarPane extends BorderPane {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    private final Car car;

    private TextField rentedTo;

    public ManageCarPane(Stage stage, Car car){
        this.car = car;
        setPadding(new Insets(FinalProject.PADDING));
        setBackground(FinalProject.GRAY_BG);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 0, 10, 0));
        grid.setVgap(5);
        grid.setHgap(30);
        grid.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        grid.setAlignment(Pos.TOP_CENTER);

        int row = 0;

        grid.add(FinalProject.label("Car type:"), 0, row);
        grid.add(FinalProject.label(car.getType().getName()), 1, 0);

        grid.add(FinalProject.label("Rented to:"), 0, ++row);
        grid.add(rentedTo = new TextField(car.getRent() == null ? "" : car.getRent().getName()), 1, 1);
        rentedTo.setPromptText("Leave blank for none.");

        //Load rent history
        grid.add(FinalProject.label("Rent history:"), 0, ++row);

        try(PreparedStatement statement = FinalProject.get().getDatabase().getConnection().prepareStatement("select * from Transactions" +
                " where id = ?" +
                " order by dateRented desc, dateReturned desc")){
            statement.setInt(1, car.getCarId());
            ResultSet rs = statement.executeQuery();

            if(rs.isLast()){
                grid.add(FinalProject.label("None to show!"), 1, row);
            } else {
                int num = 0;

                while(rs.next()){
                    RentInfo rent = new RentInfo(rs);

                    grid.add(FinalProject.label("To: " + rent.getName(), 12), 0, ++row);
                    grid.add(FinalProject.label("Date: " + rent.getDateString(), 12), 1, row);
                    num++;
                }

                grid.add(FinalProject.label("(total rents: " + num + ")"), 1, row - num);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        Button saveButton = FinalProject.button("Save", event -> {
            if(rentedTo.getText() == null || rentedTo.getText().isEmpty()){
                car.setRent(null);
            } else {
                if(car.getRent() == null){
                    car.setRent(new RentInfo(rentedTo.getText()));
                } else {
                    car.getRent().setName(rentedTo.getText());
                }
            }

            FinalProject.get().getDatabase().updateRental(car);
            stage.close();
        });

        Button deleteButton = FinalProject.button("Delete", event -> {
            FinalProject.get().getDatabase().deleteCar(car);
            FinalProject.get().loadCars();
            stage.close();
        });

        Button cancelButton = FinalProject.button("Close", event -> stage.close());

        Label topLabel = FinalProject.label("Editing car #" + car.getCarId() + ":", 22);
        topLabel.setAlignment(Pos.CENTER);
        setTop(topLabel);

        setAlignment(grid, Pos.CENTER);
        setCenter(grid);

        FlowPane bottom = new FlowPane(saveButton, cancelButton, deleteButton);
        bottom.setPadding(getPadding());
        bottom.setHgap(20);
        bottom.setAlignment(Pos.CENTER);
        setBottom(bottom);
    }
}
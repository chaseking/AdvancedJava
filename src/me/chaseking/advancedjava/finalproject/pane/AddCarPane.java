package me.chaseking.advancedjava.finalproject.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;
import me.chaseking.advancedjava.finalproject.car.RentInfo;

/**
 * @author Chase King
 */
public class AddCarPane extends BorderPane {
    public static final int WIDTH = 420;
    public static final int HEIGHT = 220;

    private ComboBox<CarType> carType;
    private TextField rentedTo;

    public AddCarPane(Stage stage){
        setPadding(new Insets(FinalProject.PADDING));
        setBackground(FinalProject.GRAY_BG);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 0, 10, 0));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        grid.setAlignment(Pos.TOP_CENTER);
        int row = 0;
        int id = FinalProject.get().getDatabase().getNextId();

        grid.add(FinalProject.label("ID:"), 0, row);
        TextField idField = new TextField(String.valueOf(id));
        idField.setEditable(false);
        grid.add(idField, 1, row);

        grid.add(FinalProject.label("Car type:"), 0, ++row);
        grid.add(carType = new ComboBox<>(), 1, row);

        for(CarType type : CarType.values()){
            carType.getItems().add(type);
        }

        carType.setValue(carType.getItems().get(0));

        grid.add(FinalProject.label("Rented to:"), 0, ++row);
        grid.add(rentedTo = new TextField(), 1, row);
        rentedTo.setPromptText("Leave blank for none.");

        //Add button
        Button addButton = FinalProject.button("Add Car", event -> {
            CarType type = carType.getValue();
            Car car = type.createNew(id);

            if(rentedTo.getText() != null && !rentedTo.getText().isEmpty()){
                car.setRent(new RentInfo(rentedTo.getText()));
            }

            FinalProject.get().getDatabase().addCar(car);
            FinalProject.get().loadCars();
            stage.close();
        });

        Button cancelButton = FinalProject.button("Cancel", event -> stage.close());

        Label topLabel = FinalProject.label("Adding new car to inventory:", 22);
        topLabel.setAlignment(Pos.CENTER);
        setTop(topLabel);

        setAlignment(grid, Pos.CENTER);
        setCenter(grid);

        FlowPane bottom = new FlowPane(addButton, cancelButton);
        bottom.setPadding(getPadding());
        bottom.setHgap(20);
        bottom.setAlignment(Pos.CENTER);
        setBottom(bottom);
    }
}
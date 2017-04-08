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

/**
 * @author Chase King
 */
public class AddCarPane extends BorderPane {
    public static final int WIDTH = 420;
    public static final int HEIGHT = 190;

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

        grid.add(FinalProject.label("Car type:"), 0, 0);
        grid.add(carType = new ComboBox<>(), 1, 0);

        for(CarType type : CarType.values()){
            carType.getItems().add(type);
        }

        carType.setValue(carType.getItems().get(0));

        grid.add(FinalProject.label("Rented to:"), 0, 1);
        grid.add(rentedTo = new TextField(), 1, 1);
        rentedTo.setPromptText("Leave blank for none.");

        //Add button
        Button addButton = FinalProject.button("Add Car", event -> {
            CarType type = carType.getValue();
            Car car = type.createNew();

            if(rentedTo.getText() != null && !rentedTo.getText().isEmpty()){
                car.setRentedTo(rentedTo.getText());
            }

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
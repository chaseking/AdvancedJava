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
public class EditCarPane extends BorderPane {
    public static final int WIDTH = 420;
    public static final int HEIGHT = 190;

    private Car car;

    private Label carType;
    private TextField rentedTo;

    public EditCarPane(Stage stage, Car car){
        this.car = car;
        setPadding(new Insets(FinalProject.PADDING));
        setBackground(FinalProject.GRAY_BG);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 0, 10, 0));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        grid.setAlignment(Pos.TOP_CENTER);

        grid.add(FinalProject.label("Car type:"), 0, 0);
        grid.add(carType = FinalProject.label(car.getType().getName()), 1, 0);

        grid.add(FinalProject.label("Rented to:"), 0, 1);
        grid.add(rentedTo = new TextField(car.getRentedTo()), 1, 1);
        rentedTo.setPromptText("Leave blank for none.");

        //Add button
        Button saveButton = FinalProject.button("Save", event -> {
            if(rentedTo.getText() == null || rentedTo.getText().isEmpty()){
                car.setRentedTo(null);
            } else {
                car.setRentedTo(rentedTo.getText());
            }

            stage.close();
        });

        Button cancelButton = FinalProject.button("Cancel", event -> stage.close());

        Label topLabel = FinalProject.label("Editing car #" + car.getCarId() + ":", 22);
        topLabel.setAlignment(Pos.CENTER);
        setTop(topLabel);

        setAlignment(grid, Pos.CENTER);
        setCenter(grid);

        FlowPane bottom = new FlowPane(saveButton, cancelButton);
        bottom.setPadding(getPadding());
        bottom.setHgap(20);
        bottom.setAlignment(Pos.CENTER);
        setBottom(bottom);
    }
}
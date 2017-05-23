package me.chaseking.advancedjava.finalproject.user;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarFilter;
import me.chaseking.advancedjava.finalproject.car.CarStatus;
import me.chaseking.advancedjava.finalproject.car.CarType;
import me.chaseking.advancedjava.finalproject.database.LoadCarsTask;
import me.chaseking.advancedjava.finalproject.pane.AddCarPane;
import me.chaseking.advancedjava.finalproject.pane.CarsPaneTextBox;
import me.chaseking.advancedjava.finalproject.pane.LookupUserPane;

/**
 * @author Chase King
 */
public class Employee extends User {
    private VBox centerPane; //The central pane that has all the information
    private TilePane carsPane; //Boxes for car information
    private ChoiceBox<CarFilter> carsPaneFilter; //Filter for cars pane

    public Employee(){
        super("Employee");

        FinalProject.get().updateCallback = () -> {
            carsPane.getChildren().clear();

            if(false /*LoadCarsTask.INSTANCE.isRunning()*/){
                carsPane.getChildren().add(CarsPaneTextBox.LOADING);
            } else if(FinalProject.get().getCars() == null){
                carsPane.getChildren().add(CarsPaneTextBox.FAILED);
            } else {
                for(Car car : FinalProject.get().getCars()){
                    if(carsPaneFilter.getValue().willShow(car)){
                        carsPane.getChildren().add(car);
                    }
                }

                if(carsPane.getChildren().isEmpty()){
                    carsPane.getChildren().add(CarsPaneTextBox.NONE_TO_SHOW);
                }
            }
        };

        //Top section
        Button addButton = FinalProject.button("Add", event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //Block other windows
            stage.initOwner(FinalProject.get().getPrimaryStage());
            stage.setResizable(false);
            stage.setTitle("Add Rental Car");

            Scene scene = new Scene(new AddCarPane(stage), AddCarPane.WIDTH, AddCarPane.HEIGHT);

            stage.setScene(scene);
            stage.show();
        });

        Button refreshButton = FinalProject.button("Refresh", event -> FinalProject.get().loadCars());

        Button lookupUserButton = FinalProject.button("Lookup User", event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //Block other windows
            stage.initOwner(FinalProject.get().getPrimaryStage());
            stage.setResizable(false);
            stage.setTitle("Lookup User");

            Scene scene = new Scene(new LookupUserPane(stage), LookupUserPane.WIDTH, LookupUserPane.HEIGHT);

            stage.setScene(scene);
            stage.show();
        });

        carsPane = new TilePane(Orientation.HORIZONTAL, 20, 20);
        carsPane.setAlignment(Pos.TOP_LEFT);
        carsPane.setPadding(new Insets(5, 0, 0, 0)); //A little extra space from menu above
        //carsPane.hgapProperty().bind(carsPane.widthProperty().divide(10)); //TODO - Alter spacing based on window size?

        carsPaneFilter = new ChoiceBox<>();

        carsPaneFilter.getItems().add(CarFilter.ALL);
        //carsPaneFilter.getItems().add(new Separator())
        carsPaneFilter.getItems().addAll(CarType.values());
        carsPaneFilter.getItems().addAll(CarStatus.values());
        carsPaneFilter.setValue(carsPaneFilter.getItems().get(0));

        carsPaneFilter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> FinalProject.get().updatePane());

        centerPane = new VBox(5,
                FinalProject.label("Viewing rental cars:", 18),
                new HBox(10, FinalProject.label("Filter:"), carsPaneFilter, addButton, refreshButton, lookupUserButton),
                carsPane);

        centerPane.setPadding(new Insets(FinalProject.PADDING / 2, FinalProject.PADDING, FinalProject.PADDING, FinalProject.PADDING));
        centerPane.setBorder(BORDER);
    }

    @Override
    public VBox getPane(){
        return centerPane;
    }
}
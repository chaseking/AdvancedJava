package me.chaseking.advancedjava.finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.database.CarUpdateService;
import me.chaseking.advancedjava.finalproject.user.Customer;
import me.chaseking.advancedjava.finalproject.user.Employee;
import me.chaseking.advancedjava.finalproject.user.User;

import java.util.List;

/**
 * @author Chase King
 */
public class FinalProject extends Application {
    public static final Color DARK_GREEN = Color.rgb(39, 174, 96);
    public static final Color LIGHT_GREEN = Color.rgb(46, 204, 113);
    public static final Color GRAY = Color.rgb(52, 73, 94);
    public static final Color BLUE = Color.rgb(41, 128, 185);
    public static final Color PURPLE = Color.rgb(142, 68, 173);
    public static final Color RED = Color.rgb(231, 76, 60);
    public static final Background GRAY_BG = new Background(new BackgroundFill(GRAY, CornerRadii.EMPTY, Insets.EMPTY));
    public static final int PADDING = 16;

    private static FinalProject instance;

    public static void main(String[] args){
        Application.launch(FinalProject.class, args);
    }

    public static FinalProject get(){
        return instance;
    }

    private List<Car> cars;
    private CarUpdateService updateService;

    private Stage primaryStage;
    private BorderPane pane;

    private ChoiceBox<User> userOption;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        pane = new BorderPane();

        pane.setBackground(GRAY_BG);

        userOption = new ChoiceBox<>();
        userOption.getItems().addAll(new Employee(), new Customer());
        userOption.setValue(userOption.getItems().get(0));
        userOption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pane.setCenter(newValue.getPane()));
        pane.setCenter(userOption.getValue().getPane());

        HBox userSelection = new HBox(10, label("Select user:"), userOption);

        userSelection.setPadding(new Insets(PADDING, PADDING, PADDING / 2, PADDING));
        pane.setTop(userSelection);

        //Build the scene
        Scene scene = new Scene(pane, 780, 640);

        primaryStage.setTitle("Rental Cars");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Load cars
        updateService = new CarUpdateService();

        updateService.setOnFailed(event -> {
            cars = null;
            updateService.reset();
            updatePane();
        });

        updateService.setOnSucceeded(event -> {
            cars = updateService.getValue();
            updateService.reset();
            updatePane();
        });

        loadCars();
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public List<Car> getCars(){
        return cars;
    }

    public CarUpdateService getUpdateService(){
        return updateService;
    }

    public void updatePane(){
        User user = userOption.getValue();

        if(user instanceof Employee){
            ((Employee) user).updateCarsPane();
        }
    }

    public void loadCars(){
        if(!updateService.isRunning()){
            updateService.start();
        }
    }

    public static Label label(String text, int size){
        Label label = new Label(text);

        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(size));
        return label;
    }

    public static Label label(String text){
        return label(text, 16);
    }

    public static Button button(String text, EventHandler<ActionEvent> action){
        Button button = new Button(text);

        button.setBackground(new Background(new BackgroundFill(FinalProject.BLUE, new CornerRadii(5), Insets.EMPTY)));
        button.setFont(Font.font(14));
        button.setTextFill(Color.WHITE);
        button.setOnAction(action);
        return button;
    }
}
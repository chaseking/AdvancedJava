package me.chaseking.advancedjava.finalproject.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarStatus;
import me.chaseking.advancedjava.finalproject.car.CarType;

/**
 * @author Chase King
 */
public class Customer extends User {
    private BorderPane pane;

    private GridPane detailsPane;
    private ComboBox<String> carType;
    private TextField firstName;
    private TextField lastName;
    private Label message;
    private FlowPane bottom;
    private VBox centerBox;
    private HBox yesNoButtons;

    public Customer(){
        super("Customer");

        pane = new BorderPane();
        pane.setPadding(new Insets(FinalProject.PADDING));
        pane.setBackground(FinalProject.GRAY_BG);

        detailsPane = new GridPane();

        detailsPane.setPadding(new Insets(10, 0, 10, 0));
        detailsPane.setVgap(5);
        detailsPane.setHgap(10);
        //detailsPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        detailsPane.setAlignment(Pos.TOP_CENTER);

        detailsPane.add(FinalProject.label("Car type:"), 0, 0);
        detailsPane.add(carType = new ComboBox<>(), 1, 0);

        carType.getItems().add("No Preference");
        carType.setValue(carType.getItems().get(0));

        for(CarType type : CarType.VALUES){
            carType.getItems().add(type.getName());
        }

        detailsPane.add(FinalProject.label("First name:"), 0, 1);
        detailsPane.add(firstName = new TextField(), 1, 1);
        firstName.setPromptText("Enter your first name.");

        detailsPane.add(FinalProject.label("Last name:"), 0, 2);
        detailsPane.add(lastName = new TextField(), 1, 2);
        lastName.setPromptText("Enter your last name.");

        //Add button
        Button rentButton = FinalProject.button("Rent", event -> {
            if(yesNoButtons != null){
                return;
            }

            String firstName = this.firstName.getText().trim();
            String lastName = this.lastName.getText().trim();

            if(firstName.isEmpty() || lastName.isEmpty()){
                message.setText("Please enter a valid first and last name!");
                return;
            }

            String name = firstName + " " + lastName;
            String typeStr = carType.getValue();
            CarType type = typeStr.equals(carType.getItems().get(0)) ? null : CarType.fromName(typeStr);
            Car car = null;
            Car alternativeCar = null;

            for(Car c : FinalProject.get().getCars()){
                if(c.getStatus() == CarStatus.AVAILABLE){
                    if(type == null || c.getType() == type){
                        car = c;
                        break;
                    } else {
                        alternativeCar = c;
                    }
                }
            }

            if(car == null){
                //No available car
                if(alternativeCar == null){
                    message.setText("There are no cars available!\nPlease check back later.");
                } else {
                    message.setText("There are no " + typeStr + " cars available, but there is a " + alternativeCar.getType().getName() + " car available.\nWould you like to rent that instead?");

                    Car finalAlternativeCar = alternativeCar;
                    Button yes = FinalProject.button("Yes", e -> {
                        rent(finalAlternativeCar, name);
                        centerBox.getChildren().remove(yesNoButtons);
                        yesNoButtons = null;
                    });

                    Button no = FinalProject.button("No", e -> {
                        message.setText("Rental cancelled!");
                        centerBox.getChildren().remove(yesNoButtons);
                        yesNoButtons = null;
                    });

                    yesNoButtons = new HBox(20, yes, no);
                    yesNoButtons.setAlignment(Pos.CENTER);
                    centerBox.getChildren().add(yesNoButtons);
                }
            } else {
                rent(car, name);
            }
        });

        Button clearButton = FinalProject.button("Clear", event -> clearDetails());
        Label topLabel = FinalProject.label("Enter details:", 22);
        topLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        pane.setTop(topLabel);

        message = FinalProject.label("", 20);
        message.setAlignment(Pos.CENTER);
        message.setTextAlignment(TextAlignment.CENTER);

        centerBox = new VBox(50, detailsPane, message);
        centerBox.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(centerBox);

        bottom = new FlowPane(rentButton, clearButton);
        bottom.setPadding(pane.getPadding());
        bottom.setHgap(20);
        bottom.setAlignment(Pos.CENTER);
        pane.setBottom(bottom);
        pane.setBorder(BORDER);
    }

    @Override
    public BorderPane getPane(){
        return pane;
    }

    private void clearDetails(){
        carType.setValue(carType.getItems().get(0));
        firstName.clear();
        lastName.clear();
    }

    private void rent(Car car, String name){
        car.setRentedTo(name);
        message.setText("You have rented out a " + car.getType().getName() + " car!\nThank you for your purchase!");
    }
}
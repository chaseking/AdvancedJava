package me.chaseking.advancedjava.finalproject.car;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.SimpleObservableObject;
import me.chaseking.advancedjava.finalproject.pane.EditCarPane;

import java.util.function.Supplier;

/**
 * @author Chase King
 */
public abstract class Car extends Pane {
    public static final int WIDTH = 160;
    public static final int HEIGHT = 120;

    private int id;

    private final CarType type;

    private CarStatus status = CarStatus.AVAILABLE;
    private String rentedTo;

    private Rectangle rectangle;
    private Rectangle topBar;
    private Label labelTitle;
    private Label labelStatus;
    private Label labelRentedTo;
    private Label clickToManageText;

    protected Car(CarType type, int id){
        this.id = id;
        this.type = type;

        rectangle = new Rectangle(0, HEIGHT * 0.1, WIDTH, HEIGHT);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFill(status.getColor());

        //Top bar
        topBar = new Rectangle(0, 0, WIDTH, HEIGHT * 0.2);
        topBar.setArcHeight(rectangle.getArcHeight());
        topBar.setArcWidth(rectangle.getArcWidth());
        topBar.setFill(status.getColor().darker());

        labelTitle = label(() -> "Car " + (id == 0 ? "???" : "#" + id), 16);

        //Info text
        VBox infoBox = new VBox(5,
                FinalProject.label("Type: " + type.getName(), 12),
                labelStatus = FinalProject.label("Status: " + status.getName(), 12),
                labelRentedTo = FinalProject.label(rentedTo != null ? "Rented to: " + rentedTo : "", 12)
                );

        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setPadding(new Insets(topBar.getHeight() + 5, 5, 5, 5));

        clickToManageText = FinalProject.label("\n\n\n\n\n\nClick to manage.", 12); //Couldn't figure out how to get it to stick at the bottom
        clickToManageText.setAlignment(Pos.BOTTOM_CENTER);
        clickToManageText.setOpacity(0);

        getChildren().addAll(new StackPane(rectangle, infoBox, clickToManageText), new StackPane(topBar, labelTitle));

        //TODO - Blur background and show "Click to manage." text when hovered
        setOnMouseEntered(event -> {
            rectangle.setFill(status.getColor().deriveColor(0, 1.0, 1.1, 1.0));

            FadeTransition transition = new FadeTransition(Duration.millis(200), clickToManageText);

            transition.setFromValue(0);
            transition.setToValue(1);
            transition.play();
        });

        setOnMouseExited(event -> {
            rectangle.setFill(status.getColor());

            FadeTransition transition = new FadeTransition(Duration.millis(250), clickToManageText);

            transition.setFromValue(1);
            transition.setToValue(0);
            transition.play();
        });

        setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //Block other windows
            stage.initOwner(FinalProject.get().getPrimaryStage());
            stage.setResizable(false);
            stage.setTitle("Editing Car #" + id);

            Scene scene = new Scene(new EditCarPane(stage, this), EditCarPane.WIDTH, EditCarPane.HEIGHT);

            stage.setScene(scene);
            stage.show();
        });
    }

    public int getCarId(){
        return id;
    }

    public void setCarId(int id){
        this.id = id;
    }

    public CarType getType(){
        return type;
    }

    public CarStatus getStatus(){
        return status;
    }

    public String getRentedTo(){
        return rentedTo;
    }

    public void setRentedTo(String rentedTo){
        this.rentedTo = rentedTo;
        this.status = rentedTo == null ? CarStatus.AVAILABLE : CarStatus.RENTED;
        rectangle.setFill(status.getColor());
        topBar.setFill(status.getColor().darker());
        labelStatus.setText("Status: " + status.getName());
        labelRentedTo.setText(rentedTo != null ? "Rented to: " + rentedTo : "");
        //FinalProject.get().updatePane();
    }

    private static Label label(Supplier<String> getter, int size){
        Label label = FinalProject.label("", size);

        label.textProperty().bind(new SimpleObservableObject<>(getter));

        return label;
    }
}
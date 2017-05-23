package me.chaseking.advancedjava.finalproject.user.customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.user.Customer;
import me.chaseking.advancedjava.finalproject.utils.PaneHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Chase King
 */
public class LookupRent implements PaneHolder {
    private BorderPane pane;
    private GridPane detailsPane;
    private TextField firstName;
    private TextField lastName;
    private Label message;

    public LookupRent(Customer customer){
        pane = new BorderPane();

        detailsPane = new GridPane();
        pane.setCenter(detailsPane);
        detailsPane.setPadding(new Insets(10, 0, 10, 0));
        detailsPane.setVgap(5);
        detailsPane.setHgap(10);
        detailsPane.setAlignment(Pos.TOP_CENTER);

        Label topLabel = FinalProject.label("Enter details:", 22);
        topLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        pane.setTop(topLabel);

        detailsPane.add(FinalProject.label("First name:"), 0, 0);
        detailsPane.add(firstName = new TextField(), 1, 0);
        firstName.setPromptText("Enter your first name.");

        detailsPane.add(FinalProject.label("Last name:"), 0, 1);
        detailsPane.add(lastName = new TextField(), 1, 1);
        lastName.setPromptText("Enter your last name.");

        detailsPane.add(FinalProject.button("Get Details", event -> {
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty()){
                message.setText("Please enter valid details!");
                return;
            }

            String fullName = firstName.getText().trim() + " " + lastName.getText().trim();

            try(PreparedStatement statement = FinalProject.get().getDatabase().getConnection().prepareStatement("select * from Transactions" +
                    " where name = ? and dateRented <= curdate() and dateReturned > curdate()")){
                statement.setString(1, fullName);

                ResultSet rs = statement.executeQuery();

                if(rs.isLast()){
                    message.setText("You have no cars rented!");
                } else {
                    StringBuilder text = new StringBuilder().append("Showing all your rented cars:");

                    while(rs.next()){
                        int id = rs.getInt("id");

                        text.append('\n').append("#" + id + " (" + FinalProject.get().getCar(id).getType().getName() + ")");
                    }

                    message.setText(text.toString());
                }
            } catch(SQLException e){
                e.printStackTrace();
            }

            /*
            for(Car car : FinalProject.get().getCars()){
                if(car.getRentedTo() != null && car.getRentedTo().equals(fullName)){
                    if(rented == null) rented = new ArrayList<>();
                    rented.add(car);
                }
            }
            */
        }), 0, 2);

        detailsPane.add(FinalProject.button("Clear", event -> clearDetails()), 1, 2);

        message = FinalProject.label("", 20);
        message.setAlignment(Pos.CENTER);
        message.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(message, Pos.CENTER);
        pane.setBottom(message);
    }

    @Override
    public Pane getPane(){
        return pane;
    }

    private void clearDetails(){
        firstName.clear();
        lastName.clear();
        message.setText("");
    }
}
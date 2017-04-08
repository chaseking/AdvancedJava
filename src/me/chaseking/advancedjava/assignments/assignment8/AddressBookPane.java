package me.chaseking.advancedjava.assignments.assignment8;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * @author Chase King
 */
public class AddressBookPane extends BorderPane {
    protected TextField name = new TextField();
    protected TextField street = new TextField();
    protected TextField city = new TextField();
    protected TextField state = new TextField();
    protected TextField zip = new TextField();

    public AddressBookPane(Assignment8 main){
        super();

        name.setPrefColumnCount(Contact.LENGTH_NAME);
        street.setPrefColumnCount(Contact.LENGTH_STREET);
        city.setPrefColumnCount(Contact.LENGTH_CITY);
        state.setPrefColumnCount(Contact.LENGTH_STATE);
        zip.setPrefColumnCount(Contact.LENGTH_ZIP);

        FlowPane infoPane = new FlowPane(5, 5, new Label("Name "), name,
                new Label("Street "), street, new Label("City"), city,
                new Label("State"), state, new Label("Zip"), zip);

        infoPane.setPadding(new Insets(10, 10, 0, 10));
        //infoPane.setAlignment(Pos.CENTER);

        setCenter(infoPane);

        HBox buttonsPane = new HBox(5,
                button("Add", e -> main.addContact()),
                button("First", e -> setContact(main.getFirst())),
                button("Previous", e -> setContact(main.getPrevious())),
                button("Next", e -> setContact(main.getNext())),
                button("Last", e -> setContact(main.getLast())),
                button("Update", e -> main.updateContact()));

        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setPadding(new Insets(20));
        setBottom(buttonsPane);
    }

    public void setContact(Contact contact){
        name.setText(contact == null ? "" : contact.getName());
        street.setText(contact == null ? "" : contact.getStreet());
        city.setText(contact == null ? "" : contact.getCity());
        state.setText(contact == null ? "" : contact.getState());
        zip.setText(contact == null ? "" : contact.getZip());
    }

    private static Button button(String text, EventHandler<ActionEvent> action){
        Button button = new Button(text);

        button.setOnAction(action);
        return button;
    }
}
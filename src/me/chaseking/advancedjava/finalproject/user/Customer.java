package me.chaseking.advancedjava.finalproject.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.user.customer.LookupRent;
import me.chaseking.advancedjava.finalproject.user.customer.RentCar;
import me.chaseking.advancedjava.finalproject.utils.PaneHolder;

/**
 * @author Chase King
 */
public class Customer extends User {
    private BorderPane pane;

    private RentCar rentCar;
    private LookupRent lookupRent;

    public Customer(){
        super("Customer");

        pane = new BorderPane();
        pane.setPadding(new Insets(FinalProject.PADDING));
        pane.setBackground(FinalProject.GRAY_BG);
        pane.setBorder(BORDER);

        rentCar = new RentCar(this);
        lookupRent = new LookupRent(this);

        HBox navButtons = new HBox(
                FinalProject.button("Rent Car", e -> menu(e.getSource(), rentCar)),
                FinalProject.button("Lookup Rent", e -> menu(e.getSource(), lookupRent))
        );

        navButtons.setAlignment(Pos.CENTER);
        navButtons.setSpacing(10);
        navButtons.setPadding(new Insets(0, 0, 10, 0));
        pane.setTop(navButtons);
        menu(navButtons.getChildren().get(0), rentCar);
    }

    private void menu(Object source, PaneHolder paneHolder){
        for(Node child : ((HBox) pane.getTop()).getChildren()){
            Button button = (Button) child;

            if(button == source){
                button.setBackground(new Background(new BackgroundFill(FinalProject.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                button.setBackground(FinalProject.GRAY_BG);
            }
        }

        this.pane.setCenter(paneHolder.getPane());
    }

    @Override
    public BorderPane getPane(){
        return pane;
    }
}
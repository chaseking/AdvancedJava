package me.chaseking.advancedjava.labs.lab7;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.chaseking.advancedjava.Main;
import me.chaseking.advancedjava.labs.lab1.Account;
import me.chaseking.advancedjava.labs.lab2.CDAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public class Lab7 extends Application {
    public static void main(String[] args){
        Application.launch(Lab7.class, args);
    }

    private BorderPane pane;
    private List<Account> accounts = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new BorderPane();
        ComboBox<String> menuSelection = new ComboBox<>();

        menuSelection.getItems().addAll("Open New Account",
                "Open CD Account",
                "Deposit",
                "Withdraw",
                "Check Balance",
                "Close Account");

        menuSelection.setValue(menuSelection.getItems().get(0));

        HBox top = new HBox(10, new Label("Select a service:"), menuSelection);

        top.setAlignment(Pos.CENTER);
        pane.setTop(top);

        menuSelection.setOnAction(event -> {
            pane.setBottom(null);

            switch(menuSelection.getItems().indexOf(menuSelection.getValue())){
                case 0:
                    pane.setCenter(paneOpenNewAccount());
                    break;
                case 1:
                    pane.setCenter(paneOpenCDAccount());
                    break;
                case 2:
                    pane.setCenter(paneMakeDeposit());
                    break;
                case 3:
                    pane.setCenter(paneWithdraw());
                    break;
                case 4:
                    pane.setCenter(paneCheckBalance());
                    break;
                case 5:
                    pane.setCenter(paneCloseAccount());
                    break;
            }
        });

        //Open new account
        pane.setCenter(paneOpenNewAccount());

        Scene scene = new Scene(pane, 700, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Exercise 5 (Text)");
        primaryStage.show();
    }

    private void bottomText(String text){
        Label label = new Label(text);

        label.setFont(Font.font(20));
        label.setAlignment(Pos.CENTER);
        pane.setBottom(new StackPane(label));
    }

    private Pane paneOpenNewAccount(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        RadioButton saving = new RadioButton("Saving");
        RadioButton checking = new RadioButton("Checking");
        ToggleGroup group = new ToggleGroup();
        saving.setToggleGroup(group);
        checking.setToggleGroup(group);
        group.selectToggle(saving);

        pane.add(saving, 0, 0);
        pane.add(checking, 1, 0);

        TextField initialDeposit = new TextField("0");
        TextField interestRate = new TextField("2.5");
        TextField balance = new TextField();

        balance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 1);
        int id = Account.getNextID();
        TextField accountNumber = new TextField(String.valueOf(id));
        accountNumber.setEditable(false);
        pane.add(accountNumber, 1, 1);
        pane.add(new Label("Initial deposit:"), 0, 2);
        pane.add(initialDeposit, 1, 2);
        pane.add(new Label("Annual interest rate (%):"), 0, 3);
        pane.add(interestRate, 1, 3);
        pane.add(new Label("Account balance:"), 0, 4);
        pane.add(balance, 1, 4);

        Button openAccount = new Button("Open Account");
        Button cancel = new Button("Cancel");

        openAccount.setOnAction(event -> {
            Account account = new Account(id, Double.parseDouble(initialDeposit.getText()));

            accounts.add(account);
            account.setAnnualInterestRate(Double.parseDouble(interestRate.getText()) / 100);
            balance.setText("$" + Main.formatDouble(account.getBalance()));
            bottomText("Account #" + id + " created!");
        });

        cancel.setOnAction(event -> {
            initialDeposit.clear();
            interestRate.clear();
            balance.clear();
        });

        pane.add(openAccount, 0, 5);
        pane.add(cancel, 1, 5);

        return pane;
    }

    private Pane paneOpenCDAccount(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        TextField initialDeposit = new TextField("100");
        TextField duration = new TextField("9");
        TextField interestRate = new TextField();
        TextField matureBalance = new TextField();

        interestRate.setEditable(false);
        matureBalance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 0);
        int id = Account.getNextID();
        TextField accountNumber = new TextField(String.valueOf(id));
        accountNumber.setEditable(false);
        pane.add(accountNumber, 1, 0);
        pane.add(new Label("Initial deposit:"), 0, 1);
        pane.add(initialDeposit, 1, 1);
        pane.add(new Label("Duration (months):"), 0, 2);
        pane.add(duration, 1, 2);
        pane.add(new Label("Annual interest rate (%):"), 0, 3);
        pane.add(interestRate, 1, 3);
        pane.add(new Label("Mature balance:"), 0, 4);
        pane.add(matureBalance, 1, 4);

        Button openAccount = new Button("Open Account");
        Button cancel = new Button("Cancel");

        openAccount.setOnAction(event -> {
            CDAccount account = new CDAccount(id, Double.parseDouble(initialDeposit.getText()), Integer.parseInt(duration.getText()));

            accounts.add(account);
            interestRate.setText(Main.formatDouble(account.getCDannualInterestRate() * 100));
            matureBalance.setText("$" + Main.formatDouble(account.getMatureBalance()));
            bottomText("CD Account #" + id + " created!");
        });

        cancel.setOnAction(event -> {
            initialDeposit.clear();
            interestRate.clear();
            initialDeposit.clear();
        });

        pane.add(openAccount, 0, 6);
        pane.add(cancel, 1, 6);

        return pane;
    }

    private Pane paneMakeDeposit(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        TextField accountNumber = new TextField("0");
        TextField depositAmount = new TextField("100");
        TextField balance = new TextField();

        balance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 0);
        pane.add(accountNumber, 1, 0);
        pane.add(new Label("Deposit amount ($):"), 0, 1);
        pane.add(depositAmount, 1, 1);
        pane.add(new Label("Balance:"), 0, 2);
        pane.add(balance, 1, 2);

        Button done = new Button("Make Deposit");
        Button cancel = new Button("Cancel");

        done.setOnAction(event -> {
            int id = Integer.parseInt(accountNumber.getText());
            Account account = accounts.stream().filter(a -> a.getID() == id).findAny().orElse(null);

            if(account == null){
                bottomText("Could not find an account with id #" + id + "!");
            } else if(account instanceof CDAccount){
                bottomText("You may not deposit money into CD accounts!");
            } else {
                double amount = Double.parseDouble(depositAmount.getText());
                account.deposit(amount);
                balance.setText("$" + Main.formatDouble(account.getBalance()));
                bottomText("Deposited $" + Main.formatDouble(amount) + " into account #" + id + "!");
            }
        });

        cancel.setOnAction(event -> {
            accountNumber.clear();
            depositAmount.clear();
            balance.clear();
        });

        pane.add(done, 0, 3);
        pane.add(cancel, 1, 3);

        return pane;
    }

    private Pane paneWithdraw(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        TextField accountNumber = new TextField("0");
        TextField withdrawAmount = new TextField("100");
        TextField balance = new TextField();

        balance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 0);
        pane.add(accountNumber, 1, 0);
        pane.add(new Label("Withdraw amount ($):"), 0, 1);
        pane.add(withdrawAmount, 1, 1);
        pane.add(new Label("Balance:"), 0, 2);
        pane.add(balance, 1, 2);

        Button done = new Button("Make Deposit");
        Button cancel = new Button("Cancel");

        done.setOnAction(event -> {
            int id = Integer.parseInt(accountNumber.getText());
            Account account = accounts.stream().filter(a -> a.getID() == id).findAny().orElse(null);

            if(account == null){
                bottomText("Could not find an account with id #" + id + "!");
            } else if(account instanceof CDAccount){
                bottomText("You may not withdraw money from CD accounts!");
            } else {
                double amount = Double.parseDouble(withdrawAmount.getText());
                account.withdraw(amount);
                balance.setText("$" + Main.formatDouble(account.getBalance()));
                bottomText("Withdrawed $" + Main.formatDouble(amount) + " from account #" + id + "!");
            }
        });

        cancel.setOnAction(event -> {
            accountNumber.clear();
            withdrawAmount.clear();
            balance.clear();
        });

        pane.add(done, 0, 3);
        pane.add(cancel, 1, 3);

        return pane;
    }

    private Pane paneCheckBalance(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        TextField accountNumber = new TextField("0");
        TextField balance = new TextField();

        balance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 0);
        pane.add(accountNumber, 1, 0);
        pane.add(new Label("Balance ($):"), 0, 1);
        pane.add(balance, 1, 1);

        Button done = new Button("Check Balance");
        Button cancel = new Button("Cancel");

        done.setOnAction(event -> {
            int id = Integer.parseInt(accountNumber.getText());
            Account account = accounts.stream().filter(a -> a.getID() == id).findAny().orElse(null);

            if(account == null){
                bottomText("Could not find an account with id #" + id + "!");

                for(Account a : accounts){
                    System.out.println(a.getID());
                }
            } else {
                if(account instanceof CDAccount){
                    balance.setText("$" + Main.formatDouble(((CDAccount) account).getMatureBalance()));
                } else {
                    balance.setText("$" + Main.formatDouble(account.getBalance()));
                }
            }
        });

        cancel.setOnAction(event -> {
            accountNumber.clear();
            balance.clear();
        });

        pane.add(done, 0, 2);
        pane.add(cancel, 1, 2);

        return pane;
    }

    private Pane paneCloseAccount(){
        GridPane pane = new GridPane();

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        TextField accountNumber = new TextField("0");
        TextField balance = new TextField();

        balance.setEditable(false);

        pane.add(new Label("Account number:"), 0, 0);
        pane.add(accountNumber, 1, 0);
        pane.add(new Label("Balance ($):"), 0, 1);
        pane.add(balance, 1, 1);

        Button done = new Button("Close Account");
        Button cancel = new Button("Cancel");

        done.setOnAction(event -> {
            int id = Integer.parseInt(accountNumber.getText());
            Account account = accounts.stream().filter(a -> a.getID() == id).findAny().orElse(null);

            if(account == null){
                bottomText("Could not find an account with id #" + id + "!");
            } else {
                balance.setText("$" + Main.formatDouble(account.getBalance()));

                if(done.getText().equals("Close Account")){
                    done.setText("Confirm");
                    bottomText("Please click confirm to close account.");
                } else {
                    done.setText("Close Account");
                    accounts.remove(account);
                    balance.clear();
                    bottomText("Account #" + id + " closed.");
                }
            }
        });

        cancel.setOnAction(event -> {
            accountNumber.clear();
            balance.clear();
        });

        pane.add(done, 0, 2);
        pane.add(cancel, 1, 2);

        return pane;
    }
}
package me.chaseking.advancedjava.labs.lab4;

import me.chaseking.advancedjava.Main;
import me.chaseking.advancedjava.labs.lab1.Account;

/**
 * @author Chase King
 */
public class CheckingAccount extends Account {
    private double accountInterestRate;

    public CheckingAccount(double balance){
        super(balance);
    }

    @Override
    public void withdraw(double amount){
        if(getBalance() < amount){
            System.out.println("[" + getID() + "] Unable to perform withdraw: Amount cannot go below minimum balance ($" + 0 + ")");
            return;
        }

        super.withdraw(amount);
        System.out.println("[" + getID() + "] Withdraw of $" + amount + " complete.");
    }

    @Override
    public String toString(){
        return Main.format("ID: " + getID(), "Type: CheckingAccount", "Balance: $" + Main.formatDouble(getBalance()));
    }
}
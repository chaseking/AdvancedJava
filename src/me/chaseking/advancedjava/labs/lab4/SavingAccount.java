package me.chaseking.advancedjava.labs.lab4;

import me.chaseking.advancedjava.Main;
import me.chaseking.advancedjava.labs.lab1.Account;

/**
 * @author Chase King
 */
public class SavingAccount extends Account {
    private double accountAnnualInterestRate;

    private double minBalance = 300;
    private int numberWithdraws = 0;
    private int maxWithdraws = 3;

    public SavingAccount(double balance){
        super(balance);
    }

    @Override
    public void withdraw(double amount){
        if(numberWithdraws >= maxWithdraws){
            System.out.println("[" + getID() + "] Unable to perform withdraw: Maximum number of withdraws reached (" + maxWithdraws + ")");
            return;
        } else if(getBalance() - amount < minBalance){
            System.out.println("[" + getID() + "] Unable to perform withdraw: Amount cannot go below minimum balance ($" + minBalance + ")");
            return;
        }

        super.withdraw(amount);
        numberWithdraws++;
        System.out.println("[" + getID() + "] Withdraw of $" + amount + " complete.");
    }

    @Override
    public String toString(){
        return Main.format("ID: " + getID(), "Type: SavingAccount", "Balance: $" + Main.formatDouble(getBalance()), "Min. balance: $" + Main.formatDouble(minBalance),"Withdraws: " + numberWithdraws + "/" + maxWithdraws);
    }
}
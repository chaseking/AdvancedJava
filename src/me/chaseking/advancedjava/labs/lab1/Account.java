package me.chaseking.advancedjava.labs.lab1;

import me.chaseking.advancedjava.Main;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;

/**
 * @author Chase King

+ displayAccount( account: Account)
+ displayAccounts( account_array: Account[])
 */
public class Account {
    private double annualInterestRate = 0.01;

    private static int NEXT_ID = 0;

    public static int getNextID(){
        return NEXT_ID++;
    }

    private int id;
    private final Date dateCreated;
    private double balance;

    public Account(int id, Date dateCreated, double balance){
        this.id = id;
        this.dateCreated = dateCreated;
        this.balance = balance;
    }

    public Account(int id, double balance){
        this(id, new Date(), balance);
    }

    public Account(double balance){
        this(getNextID(), balance);
    }

    public Account(){
        this(0);
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getAnnualInterestRate(){
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate){
        this.annualInterestRate = annualInterestRate;
    }

    public double getMonthlyInterestRate(){
        return annualInterestRate / 12.0;
    }

    public double getMonthlyInterest(){
        return balance * getMonthlyInterestRate();
    }

    public void withdraw(double amount){
        balance -= amount;
    }

    public void deposit(double amount){
        balance += amount;
    }

    @Override
    public String toString(){
        /*
        String spacing = "   ";
        return "Type: " + getClass().getSimpleName()
                + spacing + "ID: #" + id
                + spacing + "Balance: $" + String.format("%.2f", balance)
                + spacing + "Monthly interest: $" + String.format("%.2f", getMonthlyInterest())
                + spacing + "Date created: " + dateCreated;
                */

        return Main.format("ID: " + id, "Type: Account", "Balance: " + String.format("%.2f", balance));
    }

    @Override
    public boolean equals(Object obj){
        if(obj != null && obj instanceof Account){
            return ((Account) obj).getID() == id;
        }

        return super.equals(obj);
    }

    public static void displayAccount(PrintWriter writer, Iterable<Account> accounts){
        writer.println(Main.format("Account ID", "Account Type", "Balance", "Annual Interest Rate"));

        for(Account account : accounts){
            writer.println(Main.format(String.valueOf(account.getID()), account.getClass().getSimpleName(), String.format("%.2f", account.getBalance()), String.format("%.2f", account.getAnnualInterestRate() * 100) + "%"));
        }
    }

    public static Account findById(Collection<Account> accounts, int id){
        for(Account account : accounts){
            if(account.getID() == id){
                return account;
            }
        }

        return null;
    }
}
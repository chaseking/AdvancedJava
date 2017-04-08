package me.chaseking.advancedjava.assignments.assignment3;

import me.chaseking.advancedjava.Main;
import me.chaseking.advancedjava.labs.lab1.Account;

import java.util.Date;

/**
 * @author Chase King
 */
public class ComparableAccount extends Account implements Comparable<Account>, Cloneable {
    public ComparableAccount(int id, Date dateCreated, double balance){
        super(id, dateCreated, balance);
    }

    public ComparableAccount(int id, double balance){
        super(id, balance);
    }

    @Override
    public int compareTo(Account o){
        return Double.compare(getBalance(), o.getBalance());
    }

    @Override
    public ComparableAccount clone(){
        return new ComparableAccount(getID(), (Date) getDateCreated().clone(), getBalance());
    }

    @Override
    public String toString(){
        return String.format("%8s%28s%22s%40s", "ID: " + getID(), "Type: ComparableAccount", "Balance: $" + Main.formatDouble(getBalance()), "Date: " + getDateCreated());
    }
}
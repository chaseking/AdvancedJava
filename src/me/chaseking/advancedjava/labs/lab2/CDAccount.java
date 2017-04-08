package me.chaseking.advancedjava.labs.lab2;

import me.chaseking.advancedjava.Main;
import me.chaseking.advancedjava.labs.lab1.Account;

/**
 * @author Chase King
 *
 * You will add two private variables, duration (in months) and CDannualInterestRate,
 * and four member functions, getDuration(), setDuration(), getCDannualInterestRate(), and setCDannualInterestRate().
 */
public class CDAccount extends Account {
    private final double balanceStart;
    private int months;
    private double CDannualInterestRate;

    public CDAccount(int id, double balance, int months){
        super(id, balance);
        this.balanceStart = balance;
        this.months = months;
    }

    public CDAccount(double balance, int months){
        this(getNextID(), balance, months);
    }

    public CDAccount(int months){
        super();
        this.balanceStart = getBalance();
        this.months = months;
    }

    public CDAccount(){
        this(1);
    }

    public int getMonths(){
        return months;
    }

    public void setMonths(int months){
        this.months = months;
    }

    public double getCDannualInterestRate(){
        if(CDannualInterestRate == 0){
            return CDannualInterestRate = 0.03 + getInterestAdd(months);
        }

        return CDannualInterestRate;
    }

    @Override
    public double getAnnualInterestRate(){
        return getCDannualInterestRate();
    }

    public static double getInterestAdd(int months){
        return Math.floor(months / 3.0) * 0.005;
    }

    public void setCDannualInterestRate(double CDannualInterestRate){
        this.CDannualInterestRate = CDannualInterestRate;
    }

    @Override
    public double getBalance(){
        return getMatureBalance();
    }

    public double getMatureBalance(){
        double bal = balanceStart;

        for(int month = 1; month <= months; month++){
            double interest = bal * CDannualInterestRate / 12;

            bal += interest;
        }

        return bal;
    }

    @Override
    public double getMonthlyInterestRate(){
        return getCDannualInterestRate() / 12;
    }

    @Override
    public String toString(){
        /*
        return super.toString() + "   Months: " + months
                + "   CD Annual Interest Rate: " + String.format("%.2f", getCDannualInterestRate() * 100) + "%"
                + "   Mature Balance: $" + String.format("%.2f", getMatureBalance());
                */

        return Main.format("ID: " + getID(), "Type: CDAccount", "Mature Balance: $" + String.format("%.2f", getMatureBalance()), "Annual Interest Rate: " + String.format("%.2f", getCDannualInterestRate() * 100) + "%");
    }
}
package me.chaseking.advancedjava.labs.lab1;

import java.util.Date;

/**
 * @author Chase King
 */
public class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double amount;
    private final Date date;

    public Loan(double annualInterestRate, int numberOfYears, double amount){
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.amount = amount;
        this.date = new Date();
    }

    public Loan(){
        this(2.5, 1, 1000);
    }

    public double getAnnualInterestRate(){
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate){
        this.annualInterestRate = annualInterestRate;
    }

    public int getNumberOfYears(){
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears){
        this.numberOfYears = numberOfYears;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public Date getDate(){
        return date;
    }

    public double getMonthlyPayment(){
        double monthlyInterestRate = annualInterestRate / 1200.0;

        return amount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
    }

    public double getTotalPayment(){
        return getMonthlyPayment() * 12 * numberOfYears;
    }
}
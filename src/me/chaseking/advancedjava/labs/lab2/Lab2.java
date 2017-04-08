package me.chaseking.advancedjava.labs.lab2;

import me.chaseking.advancedjava.labs.lab1.Account;

/**
 * @author Chase King
 *
 * Write a driver program to test class CDAccount.
 * Create an array contains five objects of class CDAccount with balances of $1000.00 to $5000.00,
 * and duration from 3- month to 15-months respectively.
 * Set the annualInterestRate to 3%. Calculate and print the monthly interest of every month during the saving period for each CDAccount.
 */
public class Lab2 {
    public static void run(){
        Account[] accounts = new Account[5];

        for(int i = 0; i < 5; i++){
            CDAccount account = new CDAccount(i + 1, (i + 1) * 1000, (i + 1) * 3);

            account.setCDannualInterestRate(0.03 + CDAccount.getInterestAdd(account.getMonths()));
            System.out.println(account);

            for(int month = 1; month <= account.getMonths(); month++){
                double interest = account.getBalance() * account.getCDannualInterestRate() / 12;

                account.deposit(interest);
                System.out.println("  Month " + month + " - Interest: $" + String.format("%.2f", interest) + "    Balance: $" + String.format("%.2f", account.getBalance()));
            }

            System.out.println();
            accounts[i] = account;
        }
    }
}
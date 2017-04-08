package me.chaseking.advancedjava.labs.lab1;

/**
 * @author Chase King
 */
public class Lab1 {
    public static void run(){
        Account[] accounts = new Account[5];


        for(int i = 0; i < accounts.length; i++){
            int id = (i + 1) * 1000;
            int balance = (i + 1) * 1000;
            Account account = new Account(id, balance);

            accounts[i] = account;
            account.setAnnualInterestRate(3);
            account.withdraw(500);
            account.deposit(1000);
        }

        //Account.displayAccount(accounts);
    }
}
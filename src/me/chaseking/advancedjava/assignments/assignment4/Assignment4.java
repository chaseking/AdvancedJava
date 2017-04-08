package me.chaseking.advancedjava.assignments.assignment4;

import me.chaseking.advancedjava.assignments.assignment3.ComparableAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Chase King
 */
public class Assignment4 {
    public static void main(String[] args){
        List<ComparableAccount> accounts = new ArrayList<>();

        for(int i = 1; i <= 5; i++){
            ComparableAccount account = new ComparableAccount(1000 + i, 1000);

            account.deposit(500);
            account.withdraw(i * 100);
            accounts.add(account);
        }

        List<ComparableAccount> clonedAccounts = new ArrayList<>(accounts.size());

        for(ComparableAccount account : accounts){
            clonedAccounts.add(account.clone());
        }

        System.out.println("Cloned array elements:");

        for(int i = 0; i < clonedAccounts.size(); i++){
            System.out.println(" " + i + ". " + clonedAccounts.get(i).toString());
        }

        System.out.println();

        for(int i = 0; i < accounts.size(); i++){
            ComparableAccount account = accounts.get(i);
            ComparableAccount clonedAccount = clonedAccounts.get(i);

            System.out.println("accounts[" + i + "] == clonedAccounts[" + i + "] --> " + (account == clonedAccount));
            System.out.println("accounts[" + i + "].compareTo(clonedAccounts[" + i + "]) --> " + account.compareTo(clonedAccount));
            System.out.println();
        }

        System.out.println("Before sorting:");

        for(int i = 0; i < accounts.size(); i++){
            ComparableAccount account = accounts.get(i);

            System.out.println(" " + i + ". " + account.toString());
        }

        System.out.println();
        Collections.sort(accounts);
        System.out.println("After sorting:");

        for(int i = 0; i < accounts.size(); i++){
            ComparableAccount account = accounts.get(i);

            System.out.println(" " + i + ". " + account.toString());
        }
    }
}
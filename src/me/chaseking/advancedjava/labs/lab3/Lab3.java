package me.chaseking.advancedjava.labs.lab3;

import me.chaseking.advancedjava.labs.lab1.Account;
import me.chaseking.advancedjava.labs.lab2.CDAccount;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class Lab3 {
    public static void main(String[] args){
        List<Account> accounts = new ArrayList<>();
        Random random = new Random();

        for(int id = 1; id <= 100; id++){
            if(random.nextBoolean()){ //Should there be an account with this id?
                double balance = random.nextDouble() * 10000;

                if(random.nextBoolean()){
                    accounts.add(new Account(id, balance));
                } else {
                    accounts.add(new CDAccount(id, balance, 3 * (random.nextInt(4) + 1)));
                }
            }
        }

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("Enter account number: ");
            int id = scanner.nextInt();
            Account account = Account.findById(accounts, id);

            if(account == null){
                System.out.println("Account #" + id + " does not exist!");
            } else {
                System.out.println("Account #" + id + ": " + account.toString());
            }

            System.out.println();
        }
    }

    /**
     * Prompt in the console to get users’ input to create Accounts (or CDAccount if the duration is 0).
     * Write two methods to validate users’ input,
     *  int getInt(Scanner sc, String prompt)
     *
     * Write the account number, balance, annual interest rate and account type into the file that the user specify.
     * Then format and print out the Accounts info reading from the file in the console.
     * @param args
     */
    public static void main_part2(String[] args){
        Scanner scanner = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();

        while(true){
            double balance = getDouble(scanner, "Enter initial deposit amount ($): ");
            int duration = getInt(scanner, "Enter account duration (months) (0 for regular account): ");
            Account account;

            if(duration == 0){
                account = new Account(balance);
            } else {
                account = new CDAccount(balance, duration);
            }

            accounts.add(account);
            System.out.println();
            System.out.println("Successfully queued account for saving!");
            System.out.print("Would you like to add another account? (Y/N): ");
            String next = scanner.next();
            System.out.println();

            if(!next.equalsIgnoreCase("y")){
                //Break out of this loop if no more accounts are wanted
                break;
            }
        }

        System.out.print("Enter file name on desktop: ");
        File file = new File(System.getProperty("user.home") + "/Desktop", scanner.next());

        try{
            try(PrintWriter writer = new PrintWriter(file)){
                Account.displayAccount(writer, accounts);
            }

            System.out.println("Successfully wrote " + accounts.size() + " account" + (accounts.size() == 1 ? "" : "s") + " to file: " + file.getPath());
            System.out.println();
            System.out.println("Showing file contents:");

            //Read file
            try(Scanner reader = new Scanner(file)){
                while(reader.hasNext()){
                    System.out.println(reader.nextLine());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Failed to write account info to file!");
        }
    }

    public static double getDouble(Scanner scanner, String prompt){
        while(true){
            System.out.print(prompt);

            if(scanner.hasNextDouble()){
                return scanner.nextDouble();
            } else {
                System.out.println("Incorrect input: please enter a valid number");
                scanner.nextLine(); //Remove from the buffer because otherwise we would be stuck on the same invalid value
            }
        }
    }

    public static int getInt(Scanner scanner, String prompt){
        while(true){
            System.out.print(prompt);

            if(scanner.hasNextInt()){
                return scanner.nextInt();
            } else {
                System.out.println("Incorrect input: please enter a valid number");
                scanner.nextLine(); //Remove from the buffer because otherwise we would be stuck on the same invalid value
            }
        }
    }
}
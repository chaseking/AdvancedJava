package me.chaseking.advancedjava.labs.lab4;

/**
 * @author Chase King
 */
public class Lab4 {
    public static void main(String[] args){
        SavingAccount saving1 = new SavingAccount(2000);
        SavingAccount saving2 = new SavingAccount(800);
        CheckingAccount checking1 = new CheckingAccount(1000);

        for(int i = 0; i < 5; i++){
            saving1.withdraw(300);
            saving2.withdraw(300);
            checking1.withdraw(300);
            System.out.println(saving1);
            System.out.println(saving2);
            System.out.println(checking1);
            System.out.println();
        }
    }
}
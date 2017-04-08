package me.chaseking.advancedjava.assignments.assignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class Assignment3 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a binary string: ");
        String binary = scanner.nextLine();
        System.out.println("Decimal number: " + bin2Dec(binary));
    }

    public static int bin2Dec(String binary){
        /*
        try{
            return Integer.parseInt(binary, 2);
        } catch(NumberFormatException e){
            throw new BinaryFormatException(e);
        }
        */

        int result = 0;
        char[] chars = binary.toCharArray();

        for(int i = 0; i < chars.length; i++){
            if(Character.isDigit(chars[i])){
                int digit = Character.getNumericValue(chars[i]);

                result += digit * Math.pow(2, chars.length - 1 - i);
            } else {
                throw new BinaryFormatException(chars[i]);
            }
        }

        return result;
    }

    /**
     * Ideally would be created in a different class, but nested for sake of simplicity.
     */
    static class BinaryFormatException extends NumberFormatException {
        public BinaryFormatException(char illegalChar){
            super("Illegal character: " + illegalChar);
        }

        public BinaryFormatException(NumberFormatException source){
            this(source.getMessage().split(": \"")[1].charAt(0)); //Get the invalid character from the exception message
        }
    }

    /**
     * The old assignment 3.
     */
    public static void main_perfectSquare(String[] args){
        System.out.print("Enter an integer m: ");

        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        List<Integer> factors = primeFactors(m);
        int n = 1;

        while(!factors.isEmpty()){
            int factor = factors.remove(0);
            int timesUsed = 1;

            //Call the remove(Object) method instead of remove(int index) method.
            //Alternatively, could use contains() and remove(), but this saves an unneeded loop
            while(factors.remove((Object) factor)){
                timesUsed++;
            }

            if(timesUsed % 2 == 1){
                //If it appears an odd number of times, then multiply n
                n *= factor;
            }
        }

        System.out.println("The smallest number n for m * n to be perfect square is " + n);
        System.out.println("m * n = " + (m * n));


        //Re-run for testing purposes
        System.out.println();
        main(args);
    }

    public static List<Integer> primeFactors(int number){
        List<Integer> factors = new ArrayList<>();

        for(int factor = 2; factor <= number; factor++){
            while(number % factor == 0){
                factors.add(factor);
                number /= factor;
            }
        }

        return factors;
    }
}
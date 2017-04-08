package me.chaseking.advancedjava;

import me.chaseking.advancedjava.finalproject.FinalProject;

import java.util.Random;

/**
 * @author Chase King
 */
public class Main {
    public static final Random RANDOM = new Random();

    public static void main(String[] args){
        //Assignment0.main(args);
        //Lab1.run();
        //Assignment1.main(args);
        //Lab2.run();
        //Assignment2.main(args);
        //Lab3.main(args);
        //Lab3.main_part2(args);
        //Assignment3.main(args);
        //Lab4.main(args);
        //Assignment4.main(args);
        //Lab5.main(args);
        //Assignment5.main(args);
        //Lab6.main(args);
        //Assignment6.main(args);
        //Assignment7.main(args);
        //Lab7.main(args);
        //Midterm.main(args);
        //Assignment8.main(args);
        FinalProject.main(args);
        //Testing.main(args);
    }

    public static String format(int width, String... values){
        StringBuilder builder = new StringBuilder();
        String singleFormat = "%" + width + "s";

        for(int i = 0; i < values.length; i++){
            builder.append(singleFormat);
        }

        String str = String.format(builder.toString(), values);

        //Remove beginning spaces
        /*
        while(str.charAt(0) == ' '){
            str = str.substring(1);
        }
        */

        return str;
    }

    public static String format(String... values){
        return format(22, values);
    }

    public static String formatDouble(double value){
        return String.format("%.2f", value);
    }
}
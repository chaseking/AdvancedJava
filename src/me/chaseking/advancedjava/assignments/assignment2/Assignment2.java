package me.chaseking.advancedjava.assignments.assignment2;

import java.util.Scanner;

/**
 * @author Chase King
 */
public class Assignment2 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter matrix size: ");

        int size = scanner.nextInt();
        int[][] matrix = new int[size][size];
        int[] rowTotals = new int[size];
        int[] columnTotals = new int[size];
        int largestRowSize = 0;
        int largestColumnSize = 0;

        for(int row = 0; row < matrix.length; row++){
            for(int column = 0; column < matrix[row].length; column++){
                int value = (int) Math.round(Math.random());

                matrix[row][column] = value;
                rowTotals[row] += value;
                columnTotals[column] += value;

                if(rowTotals[row] > largestRowSize){
                    largestRowSize = rowTotals[row];
                }

                if(columnTotals[column] > largestColumnSize){
                    largestColumnSize = columnTotals[column];
                }
            }
        }

        for(int row = 0; row < matrix.length; row++){
            for(int column = 0; column < matrix[row].length; column++){
                System.out.print(matrix[row][column]);
                System.out.print(" ");
            }

            if(rowTotals[row] == largestRowSize){
                System.out.print("< largest row (" + largestRowSize + " total)");
            }

            System.out.println();
        }

        for(int column = 0; column < size; column++){
            if(columnTotals[column] == largestColumnSize){
                System.out.print("^");
            } else {
                System.out.print(" ");
            }
            System.out.print(" ");
        }

        System.out.println(" largest column(s) (" + largestColumnSize + " total)");
    }
}
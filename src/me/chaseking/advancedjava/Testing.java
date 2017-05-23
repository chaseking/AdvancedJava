package me.chaseking.advancedjava;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class Testing {
    public static void main(String[] args){

    }

    public static void encryptFiles(String[] args){
        if(args.length == 2){
            try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(args[0]));
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(args[1]))){
                int data;

                while((data = in.read()) != -1){
                    out.write(data + 5);
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Please input source file and target file!");
        }
    }

    public static void numberOccurrences(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter numbers and enter 0 when finished: ");

        Map<Integer, Integer> occurrences = new HashMap<>();
        int input;

        while((input = scanner.nextInt()) != 0){
            if(occurrences.containsKey(input)){
                occurrences.put(input, occurrences.get(input) + 1);
            } else{
                occurrences.put(input, 1);
            }
        }

        //Get the highest occurrence
        int max = Collections.max(occurrences.values());

        for(Map.Entry<Integer, Integer> entry : occurrences.entrySet()){
            if(entry.getValue() == max){
                System.out.println("Number " + entry.getKey() + " occurred " + entry.getValue() + " time" + (entry.getValue() == 1 ? "" : "s") + ".");
            }
        }
    }

    //System.out.println(getHint("HARPS", "HEART"));
    private static String getHint(String word, String guess){
        StringBuilder hint = new StringBuilder();

        for(int i = 0; i < guess.length(); i++){
            String guessChar = guess.substring(i, i + 1);
            int index = word.indexOf(guessChar);

            if(index == i){
                hint.append(guessChar);
            } else if(index == -1){
                hint.append("*");
            } else {
                hint.append("+");
            }
        }

        return hint.toString();
    }

    /**
     * Processes this image in row-major order and decreases the value of each pixel at
     * position (row, col) by the value of the pixel at position (row + 2, col + 2) if it exists.
     * Resulting values that would be less than BLACK are replaced by BLACK.
     * Pixels for which there is no pixel at position (row + 2, col + 2) are unchanged.
     */
    private static void pixels(){
        final int BLACK = 0;
        final int WHITE = 255;
        int[][] pixelValues = new int[][]{
                { 221, 184, 178, 0, 0 },
                { 84, 255, 255, 130, 0 },
                { 78, 255, 0, 0, 78 },
                { 84, 130, 255, 130, 84 },
        };

        for(int row = 0; row < pixelValues.length; row++){
            for(int col = 0; col < pixelValues[row].length; col++){
                System.out.printf("%5s", pixelValues[row][col]);
            }
            System.out.println();
        }

        System.out.println();

        for(int row = 0; row < pixelValues.length; row++){
            for(int col = 0; col < pixelValues[row].length; col++){
                if(row + 2 < pixelValues.length && col + 2 < pixelValues[row].length){
                    pixelValues[row][col] -= pixelValues[row + 2][col + 2];

                    if(pixelValues[row][col] < BLACK){
                        pixelValues[row][col] = BLACK;
                    }
                }
            }
        }

        for(int row = 0; row < pixelValues.length; row++){
            for(int col = 0; col < pixelValues[row].length; col++){
                System.out.printf("%5s", pixelValues[row][col]);
            }
            System.out.println();
        }
    }

    private static void consolidate(){
        String[] spaces = new String[]{ "A", null, "B", "C", null, null, "D" };
        int lastEmpty = -1;

        for(int i = 0; i < spaces.length; i++){
            if(spaces[i] == null){
                if(lastEmpty == -1){
                    lastEmpty = i;
                }
            } else if(lastEmpty != -1){
                spaces[lastEmpty] = spaces[i];
                spaces[i] = null;
                lastEmpty = i;
            }
        }

        for(int i = 0; i < spaces.length; i++){
            System.out.print(spaces[i]);
            System.out.print(", ");
        }
    }
}
package me.chaseking.advancedjava.labs.lab9;

/**
 * @author Chase King
 */
public class Lab9 {
    public static void main(String[] args){
        Integer[] array = new Integer[]{ 1, 4, 5, 6, 9, 16 };

        System.out.println(binarySearch(array, 0)); //Prints "-1"
        System.out.println(binarySearch(array, array[3])); //Prints "3"

        String[] names = new String[]{ "albert", "brian", "david", "emily", "jackie" };

        System.out.println(binarySearch(names, "chase"));
        System.out.println(binarySearch(names, names[2]));
    }

    public static <E extends Comparable<E>> int binarySearch(E[] array, E key){
        int start = 0;
        int end = array.length - 1;

        while(start <= end){
            int mid = start + (end - start) / 2;
            int compare = array[mid].compareTo(key);

            if(compare > 0){
                end = mid - 1;
            } else if(compare < 0){
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
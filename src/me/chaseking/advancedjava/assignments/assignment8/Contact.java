package me.chaseking.advancedjava.assignments.assignment8;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Chase King
 */
public class Contact {
    public static final int LENGTH_NAME = 32;
    public static final int LENGTH_STREET = 32;
    public static final int LENGTH_CITY = 20;
    public static final int LENGTH_STATE = 2;
    public static final int LENGTH_ZIP = 5;
    public static final int TOTAL_SIZE = LENGTH_NAME + LENGTH_STREET + LENGTH_CITY + LENGTH_STATE + LENGTH_ZIP;

    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;

    public Contact(){

    }

    public Contact(RandomAccessFile inout) throws IOException {
        this();

        this.name = read(inout, LENGTH_NAME);
        this.street = read(inout, LENGTH_STREET);
        this.city = read(inout, LENGTH_CITY);
        this.state = read(inout, LENGTH_STATE);
        this.zip = read(inout, LENGTH_ZIP);
    }

    private static String read(RandomAccessFile inout, int length) throws IOException {
        byte[] data = new byte[length];
        inout.read(data);

        return new String(data);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getStreet(){
        return street;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }


    public String getZip(){
        return zip;
    }

    @Override
    public String toString(){
        return name + " " + street + " " + city + " " + state + " " + " zip";
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    public static void serialize(RandomAccessFile inout, String name, String street, String city, String state, String zip) throws IOException {
        inout.write(fixedLength(name.getBytes(), LENGTH_NAME));
        inout.write(fixedLength(street.getBytes(), LENGTH_STREET));
        inout.write(fixedLength(city.getBytes(), LENGTH_CITY));
        inout.write(fixedLength(state.getBytes(), LENGTH_STATE));
        inout.write(fixedLength(zip.getBytes(), LENGTH_ZIP));
    }

    private static byte[] fixedLength(byte[] array, int length){
        if(array.length == length){
            return array;
        }

        byte[] newArray = new byte[length];

        for(int i = 0; i < Math.min(array.length, length); i++){
            newArray[i] = array[i];
        }

        return newArray;
    }
}
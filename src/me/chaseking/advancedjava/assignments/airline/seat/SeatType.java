package me.chaseking.advancedjava.assignments.airline.seat;

/**
 * @author Chase King
 */
public enum SeatType {
    FIRST_CLASS("First Class"),
    BUSINESS_CLASS("Business Class"),
    DEFAULT("Default");

    private final String name;

    SeatType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
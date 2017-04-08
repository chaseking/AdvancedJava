package me.chaseking.advancedjava.assignments.airline.seat;

/**
 * @author Chase King
 */
public enum SeatLocation {
    LEFT_AISLE("Left Aisle"),
    LEFT_MIDDLE("Left Middle"),
    LEFT_WINDOW("Left Window"),
    RIGHT_AISLE("Right Aisle"),
    RIGHT_MIDDLE("Right Middle"),
    RIGHT_WINDOW("Right Window");

    public static final SeatLocation[] VALUES = values();

    private final String name;

    SeatLocation(String name){
        this.name = name;
        values();
    }

    public String getName(){
        return name;
    }
}
package me.chaseking.advancedjava.assignments.airline.seat;

import me.chaseking.advancedjava.assignments.airline.Person;
import me.chaseking.advancedjava.assignments.airline.plane.Plane;

/**
 * @author Chase King
 */
public class Seat {
    private final Plane plane;

    private final SeatType type;

    private final int row;
    private final SeatLocation location;

    private Person holder;

    public Seat(Plane plane, SeatType type, int row, SeatLocation location){
        this.plane = plane;
        this.type = type;
        this.row = row;
        this.location = location;
    }

    public Plane getPlane(){
        return plane;
    }

    public SeatType getType(){
        return type;
    }

    public int getRow(){
        return row;
    }

    public SeatLocation getLocation(){
        return location;
    }

    public Person getHolder(){
        return holder;
    }

    public void setHolder(Person holder){
        this.holder = holder;
    }

    public String getName(){
        return type.getName() + " - Row " + row + " (" + location.getName() + ")";
    }
}
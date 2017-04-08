package me.chaseking.advancedjava.assignments.airline;

import me.chaseking.advancedjava.assignments.airline.seat.Seat;

import java.util.List;

/**
 * @author Chase King
 */
public class Person {
    private final String name;

    private List<Seat> bookedSeats;

    public Person(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<Seat> getBookedSeats(){
        return bookedSeats;
    }
}
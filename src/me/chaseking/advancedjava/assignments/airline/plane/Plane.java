package me.chaseking.advancedjava.assignments.airline.plane;

import me.chaseking.advancedjava.assignments.airline.Person;
import me.chaseking.advancedjava.assignments.airline.seat.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public abstract class Plane {
    private final String name;

    private final List<Seat> seats;

    public Plane(String name){
        this.name = name;
        this.seats = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<Seat> getSeats(){
        return seats;
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public void bookSeat(Seat seat, Person person){
        seat.setHolder(person);
        person.getBookedSeats().add(seat);
    }
}
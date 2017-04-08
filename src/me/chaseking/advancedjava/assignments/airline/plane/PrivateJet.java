package me.chaseking.advancedjava.assignments.airline.plane;

import me.chaseking.advancedjava.assignments.airline.seat.Seat;
import me.chaseking.advancedjava.assignments.airline.seat.SeatLocation;
import me.chaseking.advancedjava.assignments.airline.seat.SeatType;

/**
 * @author Chase King
 */
public class PrivateJet extends Plane {
    public PrivateJet(){
        super("Private Jet");

        for(int row = 1; row <= 10; row++){
            if(row <= 4){
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.LEFT_WINDOW));
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.LEFT_AISLE));
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.RIGHT_WINDOW));
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.RIGHT_AISLE));
            } else {
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.LEFT_WINDOW));
                addSeat(new Seat(this, SeatType.FIRST_CLASS, row, SeatLocation.RIGHT_WINDOW));
            }
        }
    }
}
package me.chaseking.advancedjava.assignments.airline.plane;

import me.chaseking.advancedjava.assignments.airline.seat.Seat;
import me.chaseking.advancedjava.assignments.airline.seat.SeatLocation;
import me.chaseking.advancedjava.assignments.airline.seat.SeatType;

/**
 * @author Chase King
 */
public class Boeing777 extends Plane {
    public Boeing777(){
        super("Boeing 777");

        for(int row = 1; row <= 50; row++){
            SeatType type;

            if(row <= 6){
                type = SeatType.FIRST_CLASS;
            } else if(row <= 15){
                type = SeatType.BUSINESS_CLASS;
            } else {
                type = SeatType.DEFAULT;
            }

            for(SeatLocation location : SeatLocation.VALUES){
                addSeat(new Seat(this, type, row, location));
            }
        }
    }
}
package me.chaseking.advancedjava.finalproject.car.type;

import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;

/**
 * @author Chase King
 */
public class LuxuryCar extends Car {
    public LuxuryCar(int id){
        super(CarType.LUXURY, id);
    }
}
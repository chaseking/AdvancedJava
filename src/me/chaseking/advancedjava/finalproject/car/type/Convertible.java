package me.chaseking.advancedjava.finalproject.car.type;

import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;

/**
 * @author Chase King
 */
public class Convertible extends Car {
    public Convertible(int id){
        super(CarType.CONVERTIBLE, id);
    }
}
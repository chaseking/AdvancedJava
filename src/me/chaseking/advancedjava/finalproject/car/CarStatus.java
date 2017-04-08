package me.chaseking.advancedjava.finalproject.car;

import javafx.scene.paint.Color;
import me.chaseking.advancedjava.finalproject.FinalProject;

/**
 * @author Chase King
 */
public enum CarStatus implements CarFilter {
    AVAILABLE("Available", FinalProject.LIGHT_GREEN){
        @Override
        public boolean willShow(Car car){
            return car.getRentedTo() == null;
        }
    },

    RENTED("Rented", FinalProject.PURPLE){
        @Override
        public boolean willShow(Car car){
            return car.getRentedTo() != null;
        }
    };

    private final String name;

    private final Color color;

    CarStatus(String name, Color color){
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }
}
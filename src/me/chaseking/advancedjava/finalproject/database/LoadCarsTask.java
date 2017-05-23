package me.chaseking.advancedjava.finalproject.database;

import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Chase King
 */
public class LoadCarsTask implements Callable<Void> {
    @Override
    public Void call() throws Exception {
        try{
            synchronized(FinalProject.get().getCars()){
                FinalProject.get().getCars().clear();

                FinalProject.get().getDatabase().loadCars(car -> FinalProject.get().getCars().add(car));
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
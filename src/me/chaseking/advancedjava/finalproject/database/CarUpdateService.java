package me.chaseking.advancedjava.finalproject.database;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Chase King
 */
public class CarUpdateService extends Service<List<Car>> {
    @Override
    protected Task<List<Car>> createTask(){
        return new Task<List<Car>>(){
            @Override
            protected List<Car> call() throws Exception {
                try{
                    List<Car> cars = new ArrayList<>();

                    //TODO - Pull from database
                    for(int i = 0; i < 6; i++){
                        CarType type = CarType.values()[ThreadLocalRandom.current().nextInt(CarType.values().length)];
                        Car car = type.createNew(i + 1);

                        cars.add(car);

                        if(ThreadLocalRandom.current().nextInt(3) == 0){
                            car.setRentedTo("Chase King");
                        }
                    }

                    Thread.sleep(1000);
                    return cars;
                } catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
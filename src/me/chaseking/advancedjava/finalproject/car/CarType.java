package me.chaseking.advancedjava.finalproject.car;

import me.chaseking.advancedjava.finalproject.FinalProject;
import me.chaseking.advancedjava.finalproject.car.type.Convertible;
import me.chaseking.advancedjava.finalproject.car.type.LuxuryCar;
import me.chaseking.advancedjava.finalproject.car.type.Minivan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chase King
 */
public enum CarType implements CarFilter {
    MINIVAN("Minivan", Minivan.class),
    LUXURY("Luxury", LuxuryCar.class),
    CONVERTIBLE("Convertible", Convertible.class);

    public static final CarType[] VALUES = values();
    private static final Map<String, CarType> fromName = new HashMap<>();

    static {
        for(CarType type : VALUES){
            fromName.put(type.getName(), type);
        }
    }

    public static CarType fromName(String name){
        return fromName.get(name);
    }

    private final String name;

    private final Class<? extends Car> carClass;

    CarType(String name, Class<? extends Car> carClass){
        this.name = name;
        this.carClass = carClass;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean willShow(Car car){
        return car.getType() == this;
    }

    public static Car createNew(ResultSet rs) throws SQLException {
        try{
            int id = rs.getInt("id");
            CarType type = valueOf(rs.getString("type").toUpperCase());

            return type.createNew(id);
        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public Car createNew(int id){
        try{
            return carClass.getConstructor(int.class).newInstance(id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Car createNew(){
        Car car = createNew(FinalProject.get().getCars().size() + 1);

        FinalProject.get().getCars().add(car);
        FinalProject.get().updatePane();

        return car;
    }
}
package me.chaseking.advancedjava.finalproject.database;

import me.chaseking.advancedjava.finalproject.car.Car;
import me.chaseking.advancedjava.finalproject.car.CarType;
import me.chaseking.advancedjava.finalproject.car.RentInfo;

import java.sql.*;
import java.util.function.Consumer;

/**
 * @author Chase King
 */
public class Database {
    private static final String HOST = "localhost";
    private static final String DATABASE = "finalproject";
    private static final String USERNAME = "chase";
    private static final String PASSWORD = "password";

    private Connection connection;

    public Database(){

    }

    public Connection getConnection(){
        return connection;
    }

    public void connect() throws Exception {
        //Load driver
        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE, USERNAME, PASSWORD);

        System.out.println("Successfully connected to MySQL database: " + connection.getMetaData().getURL());
    }

    public void loadCars(Consumer<Car> consumer) throws SQLException {
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from Cars");

            while(rs.next()){
                Car car = CarType.createNew(rs);

                //Load rental status
                loadRental(car);

                consumer.accept(car);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void loadRental(Car car){
        try(PreparedStatement statement = connection.prepareStatement("select * from Transactions" +
                " where id = ? and dateReturned > curdate()" +
                " limit 1")){
            statement.setInt(1, car.getCarId());

            ResultSet r = statement.executeQuery();

            if(r.next()){
                RentInfo rent = new RentInfo(r.getString("name"), r.getDate("dateRented"), r.getDate("dateReturned"));

                car.setRent(rent);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addCar(Car car){
        try(PreparedStatement statement = connection.prepareStatement("insert into Cars values (?, ?)")){
            statement.setInt(1, car.getCarId());
            statement.setString(2, car.getType().toString());

            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }

        if(car.getRent() != null){
            updateRental(car);
        }
    }

    public void updateRental(Car car){
        if(car.getRent() == null){
            try(PreparedStatement statement = connection.prepareStatement("update Transactions" +
                    " set dateReturned = curdate()" +
                    " where id = ? and dateReturned > curdate()" +
                    " limit 1")){
                statement.setInt(1, car.getCarId());
                statement.executeUpdate();
            } catch(SQLException e){
                e.printStackTrace();
            }
        } else {
            try(PreparedStatement statement = connection.prepareStatement("insert into Transactions values (?, ?, ?, ?)")){
                statement.setInt(1, car.getCarId());
                statement.setString(2, car.getRent().getName());
                statement.setDate(3, car.getRent().getDateRented());
                statement.setDate(4, car.getRent().getDateReturned());
                statement.executeUpdate();
            } catch(SQLException e){
                e.printStackTrace();
            }

            try(PreparedStatement statement = connection.prepareStatement("insert into Users values (?, ?)" +
                    " on duplicate key update driversLicense = ?")){
                statement.setString(1, car.getRent().getName());
                statement.setString(2, car.getRent().getDriversLicense());
                statement.setString(3, car.getRent().getDriversLicense());
                statement.executeUpdate();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public int getNextId(){
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select id from Cars order by id desc limit 1");
            int id = rs.next() ? rs.getInt("id") : 0;

            rs = statement.executeQuery("select id from Transactions order by id desc limit 1");

            return Math.max(id, rs.next() ? rs.getInt("id") : 0) + 1;
        } catch(SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public void deleteCar(Car car){
        try(PreparedStatement statement = connection.prepareStatement("delete from Cars where id = ?")){
            statement.setInt(1, car.getCarId());
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
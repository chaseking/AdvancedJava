package me.chaseking.advancedjava.finalproject.car;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Chase King
 */
public class RentInfo {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy");

    private String name;
    private String driversLicense = "?";
    private Date dateRented;
    private Date dateReturned;

    public RentInfo(String name, Date dateRented, Date dateReturned){
        this.name = name;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
    }

    public RentInfo(String name){
        this.name = name;

        long current = System.currentTimeMillis();

        this.dateRented = new Date(current);
        this.dateReturned = new Date(current + TimeUnit.DAYS.toMillis(7)); //Default 7 days rent
    }

    public RentInfo(ResultSet rs) throws SQLException {
        this(rs.getString("name"), rs.getDate("dateRented"), rs.getDate("dateReturned"));
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDriversLicense(){
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense){
        this.driversLicense = driversLicense;
    }

    public Date getDateRented(){
        return dateRented;
    }

    public void setDateRented(Date dateRented){
        this.dateRented = dateRented;
    }

    public Date getDateReturned(){
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned){
        this.dateReturned = dateReturned;
    }

    public boolean isActive(){
        return dateReturned == null || dateReturned.getTime() > System.currentTimeMillis();
    }

    public String getDateString(){
        return DATE_FORMAT.format(dateRented.getTime()) + (isActive() ? "-" : " to " + DATE_FORMAT.format(dateReturned));
    }
}
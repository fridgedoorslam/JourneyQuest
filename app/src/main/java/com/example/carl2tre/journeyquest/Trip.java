package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String name;
    private long id;

    public String getName(){
        return name;}
    public long getId() {
        return id;
    }

    public void setName(String n){
        name = n;
    }
    public void setId(long id1){
        if (id1 > 0){
            id = id1;
        }
        else{
            id = 0;
        }
    }

    public String toString(){
        return name;
    }

    static public List<Trip> getAll(DBAdapter db){ //this is a class method
        List<Trip> trips = new ArrayList<Trip>();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                Trip trip = cursorToContact(c, db);
                trips.add(trip);

            } while (c.moveToNext());
        }
        c.close();

        return trips;
    }

    static public Trip cursorToContact( Cursor c, DBAdapter db){
        Trip trip = new Trip();
        trip.setId(c.getInt(c.getColumnIndex(db.KEY_ROWID)));
        trip.setName(c.getString(c.getColumnIndex(db.KEY_NAME)));

        return trip;

    }
}

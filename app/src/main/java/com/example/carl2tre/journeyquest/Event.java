package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private long id;
    private String eventName;
    private int eventTransportationType;
    private Date eventDate;
    private String eventNotes;

    //getters
    public long getId() { return id; }
    public String getEventName(){ return eventName; }
    public int getEventTransportationType() { return eventTransportationType; }
    public Date getEventDate() { return eventDate; }
    public String getEventNotes() { return eventNotes; }

    //setters
    public void setId(long id1){ id = (id1 > 0) ? id1 : 0; }
    public void setName(String n){
        eventName = n;
    }

    public String toString(){
        return eventName;
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

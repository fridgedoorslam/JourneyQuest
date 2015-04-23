package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.database.Cursor;

import java.sql.Time;
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

    static public List<Contact> getAll(DBAdapter db){ //this is a class method
        List<Contact> contacts = new ArrayList<Contact>();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                Contact contact = cursorToContact(c, db);
                contacts.add(contact);

            } while (c.moveToNext());
        }
        c.close();

        return contacts;
    }

    static public Contact cursorToContact( Cursor c, DBAdapter db){
        Contact contact = new Contact();
        contact.setId(c.getInt(c.getColumnIndex(db.KEY_ROWID)));
        contact.setName(c.getString(c.getColumnIndex(db.KEY_NAME)));
        return contact;
    }
}

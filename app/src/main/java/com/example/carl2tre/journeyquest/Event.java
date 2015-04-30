package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event implements Comparable<Event> {
    private long id;
    private String eventName;
    private int eventTransportationType;
    private String eventDate;
    private String eventNotes;

    //getters
    public long getId() { return id; }
    public String getEventName(){ return eventName; }
    public int getEventTransportationType() { return eventTransportationType; }
    public String getEventDate() { return eventDate; }
    public String getEventNotes() { return eventNotes; }

    //setters
    public void setId(long id1){ id = (id1 > 0) ? id1 : 0; }
    public void setName(String n){
        eventName = n;
    }
    public void setDate(String date) {
        eventDate = date;
    }

    public String toString(){
        return eventName;
    }

    //returns list of events
    static public List<Event> getAll(DBAdapter db, long tripID){ //this is a class method
        List<Event> events = new ArrayList<Event>();
        Cursor c = db.getAllEventsSorted(tripID);
        if (c.moveToFirst())
        {
            do {
                Event event = cursorToContact(c, db);
                events.add(event);

            } while (c.moveToNext());
        }
        c.close();

        return events;
    }

    //returns event object
    static public Event cursorToContact( Cursor c, DBAdapter db){
        Event event = new Event();
        event.setId(c.getInt(c.getColumnIndex(db.KEY_EVENT_ID)));
        event.setName(c.getString(c.getColumnIndex(db.KEY_EVENT_NAME)));
        event.setDate(c.getString(c.getColumnIndex(db.KEY_EVENT_DATE)));

        return event;
    }

    //Sort dates
    @Override
    public int compareTo(Event d2) {
        String date1 = this.getEventDate();
        String date2 = d2.getEventDate();
        String[] dateFormat1 = date1.split("\\s+");
        String[] dateFormat2 = date2.split("\\s+");
        String cDay1 = dateFormat1[1];
        String cDay2 = dateFormat2[1];
        cDay1 = cDay1.substring(0, cDay1.length() - 1); // Get rid of comma
        cDay2 = cDay2.substring(0, cDay2.length() - 1); // Get rid of comma
        int day1 = Integer.parseInt(cDay1);
        int day2 = Integer.parseInt(cDay2);
        int year1 = Integer.parseInt(dateFormat1[2]);
        int year2 = Integer.parseInt(dateFormat2[2]);
        int month1 = 0;
        int month2 = 0;
        String sMonth1 = dateFormat1[0];
        String sMonth2 = dateFormat2[0];

        if(sMonth1.equals("Jan")) {month1 = 1; }
        if(sMonth1.equals("Feb")) {month1 = 2; }
        if(sMonth1.equals("Mar")) {month1 = 3; }
        if(sMonth1.equals("Apr")) {month1 = 4; }
        if(sMonth1.equals("May")) {month1 = 5; }
        if(sMonth1.equals("Jun")) {month1 = 6; }
        if(sMonth1.equals("Jul")) {month1 = 7; }
        if(sMonth1.equals("Aug")) {month1 = 8; }
        if(sMonth1.equals("Sep")) {month1 = 9; }
        if(sMonth1.equals("Oct")) {month1 = 10; }
        if(sMonth1.equals("Nov")) {month1 = 11; }
        if(sMonth1.equals("Dec")) {month1 = 12; }

        if(sMonth2.equals("Jan")) {month2 = 1; }
        if(sMonth2.equals("Feb")) {month2 = 2; }
        if(sMonth2.equals("Mar")) {month2 = 3; }
        if(sMonth2.equals("Apr")) {month2 = 4; }
        if(sMonth2.equals("May")) {month2 = 5; }
        if(sMonth2.equals("Jun")) {month2 = 6; }
        if(sMonth2.equals("Jul")) {month2 = 7; }
        if(sMonth2.equals("Aug")) {month2 = 8; }
        if(sMonth2.equals("Sep")) {month2 = 9; }
        if(sMonth2.equals("Oct")) {month2 = 10; }
        if(sMonth2.equals("Nov")) {month2 = 11; }
        if(sMonth2.equals("Dec")) {month2 = 12; }

        return year1 < year2 ? -1 : year1 > year2 ? 1 :
                month1 < month2 ? -1 : month1 > month2 ? 1 :
                        day1 < day2 ? -1 : 1;


    }

}

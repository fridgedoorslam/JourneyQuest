package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class Contact {
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

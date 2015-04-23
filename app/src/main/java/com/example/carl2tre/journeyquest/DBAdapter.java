package com.example.carl2tre.journeyquest;

/**
 * Created by mattdavey on 4/8/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
    static final String DATABASE_TABLE = "trips";
    static final String KEY_ROWID = "_id";  //Give constant names to the rows
    static final String KEY_NAME = "name";
    static final String KEY_EVENT = "event";

    static final String DATABASE_EVENT_TABLE = "events";
    static final String KEY_EVENT_NAME = "eventName";
    static final String KEY_EVENT_TRANSPORTATION_TYPE = "transportationType";
    static final String KEY_EVENT_DATE = "eventDate";
    static final String KEY_EVENT_NOTES = "eventNotes";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";



    static final int DATABASE_VERSION = 6;

    static final String DATABASE_CREATE =  //SQL commands are a pain, so make a string constant to do it
            "create table trips (_id integer primary key autoincrement, "
                    + "name text not null, event text not null);";

    static final String DATABASE_EVENTS_CREATE =
            "create table events (_id integer primary key autoincrement, "
            + "eventName text not null, transportationType text not null, eventDate text not null, eventNotes text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) //SQLiteOpenHelper requires a Context to create it, so we need one, too
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                db.execSQL(DATABASE_EVENTS_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertContact(String name, String event)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EVENT, event);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---insert an event into the database---
    public long insertEvent(String eventName, String eventTransportationType, String eventDate, String eventNotes)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EVENT_NAME, eventName);
        initialValues.put(KEY_EVENT_TRANSPORTATION_TYPE, eventTransportationType);
        initialValues.put(KEY_EVENT_DATE, eventDate);
        initialValues.put(KEY_EVENT_NOTES, eventNotes);
        return db.insert(DATABASE_EVENT_TABLE, null, initialValues);

    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EVENT}, null, null, null, null, null);
        }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EVENT}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String name, String event)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EVENT, event);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }


}
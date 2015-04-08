package com.example.carl2tre.journeyquest;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class TripList extends ListActivity {
    private DBAdapter db;
    List<Contact> contacts;
    public String newTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        db = new DBAdapter(this);
        db.open();

        contacts = Contact.getAll(db);

        db.close();

        //ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts);
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_checked, contacts);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db

                final long dbPosition = contacts.get(position).getId();
                Log.d("ShowThem","dbPosition is " + Long.toString(dbPosition));

                db.open();
                Cursor c = db.getContact(dbPosition);

                if (c != null) {
                    //Toast.makeText(getBaseContext(), "Email address: " + c.getString(c.getColumnIndex(db.KEY_EMAIL)), Toast.LENGTH_LONG).show();
                }
                db.close();

                    }


        });


    }

    @Override
    public void onResume(){
        super.onResume();
        db = new DBAdapter(this);
        db.open();

        contacts = Contact.getAll(db);

        db.close();

        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_checked, contacts);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db

                final long dbPosition = contacts.get(position).getId();
                Log.d("ShowThem", "dbPosition is " + Long.toString(dbPosition));

                db.open();
                Cursor c = db.getContact(dbPosition);

                if (c != null) {
                    Toast.makeText(getBaseContext(), "Email address: " + c.getString(c.getColumnIndex(db.KEY_EMAIL)), Toast.LENGTH_LONG).show();
                }
                db.close();

                    }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTap(View view){
        Toast.makeText(this, "Tapped", Toast.LENGTH_SHORT).show();
        final EditText tripName = new EditText(this);
        AlertDialog.Builder nameBuild = new AlertDialog.Builder(this);
        nameBuild.setTitle("Name your trip");
        nameBuild.setMessage("Please name your trip");
        tripName.setInputType(InputType.TYPE_CLASS_TEXT);
        nameBuild.setView(tripName);
        nameBuild.setPositiveButton("Start Planning Trip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        newTrip = tripName.getText().toString();
                        //Toast.makeText(getApplicationContext(), newTrip, Toast.LENGTH_LONG).show();
                        long id = db.insertContact(newTrip, "test");
                        Toast.makeText(getApplicationContext(), newTrip + "added with id" + id, Toast.LENGTH_LONG).show();
                        db.close();
                        onResume();

                    }
                });

        nameBuild.show();


    }


}

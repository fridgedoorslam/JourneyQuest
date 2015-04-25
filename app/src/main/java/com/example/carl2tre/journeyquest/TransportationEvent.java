package com.example.carl2tre.journeyquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class TransportationEvent extends Activity {
    DBAdapter db;
    public EditText eventName;
    public Spinner eventTransportation;
    public EditText eventDate;
    public EditText eventTime;
    public EditText eventNotes;
    public String event_name;
    public String event_transportation;
    public String event_date;
    public String event_time;
    public String event_notes;
    public long trip_id;
    public String newTrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_event);

        Intent intent = getIntent();
        newTrip = intent.getStringExtra("com.example.carl2tre.journeyquest.newTrip");
        trip_id = intent.getLongExtra("com.example.carl2tre.journeyquest.trip_id", 0);
        db = new DBAdapter(this);
        db.open();
        eventName = (EditText) findViewById(R.id.event_name);
        eventTransportation = (Spinner) findViewById(R.id.event_transportation);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventTime = (EditText) findViewById(R.id.event_time);
        eventNotes = (EditText) findViewById(R.id.event_notes);
        db.close();

    }
    
    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        newTrip = intent.getStringExtra("com.example.carl2tre.journeyquest.newTrip");
        trip_id = intent.getLongExtra("com.example.carl2tre.journeyquest.trip_id", 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transportation_event, menu);
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
    @Override
    public void onBackPressed(){}

    public void onCancel(View view){
        finish();
    }

    public void onSubmit(View view) {

        event_name = eventName.getText().toString();
        event_transportation = eventTransportation.getSelectedItem().toString();
        event_date = eventDate.getText().toString();
        event_time = eventTime.getText().toString();
        event_notes = eventNotes.getText().toString();

        db = new DBAdapter(this);
        db.open();
        long eventId = db.insertEvent(trip_id, event_name, event_transportation, "Date", event_notes);
        db.close();


        Intent intent = new Intent(TransportationEvent.this, EventList.class);
        intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
        intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
        startActivity(intent);






    }
}

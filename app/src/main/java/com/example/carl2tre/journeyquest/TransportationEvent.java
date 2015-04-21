package com.example.carl2tre.journeyquest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TransportationEvent extends Activity {
    public EditText eventName;
    public Spinner eventTransportation;
    public EditText eventDate;
    public EditText eventTime;
    public EditText eventNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_event);
        eventName = (EditText) findViewById(R.id.event_name);
        eventTransportation = (Spinner) findViewById(R.id.event_transportation);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventTime = (EditText) findViewById(R.id.event_time);
        eventNotes = (EditText) findViewById(R.id.event_notes);

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
        String event_name = eventName.getText().toString();
        String event_transporation = eventTransportation.getSelectedItem().toString();
        String event_date = eventDate.getText().toString();
        String event_time = eventTime.getText().toString();
        String event_notes = eventNotes.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("event_name", event_name);
        bundle.putString("event_transportation", event_transporation);
        bundle.putString("event_date", event_date);
        bundle.putString("event_time", event_time);
        bundle.putString("event_notes", event_notes);

        Intent intent = new Intent(TransportationEvent.this, TripOptions.class);
        intent.putExtras(bundle);
        startActivity(intent);



    }
}

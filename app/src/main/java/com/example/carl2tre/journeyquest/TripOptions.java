package com.example.carl2tre.journeyquest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TripOptions extends ListActivity {
    private DBAdapter db;
    public List<Contact> contacts;
    public String event_name;
    List<String> events = new ArrayList<String>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_options);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            event_name = bundle.getString("event_name");
            events.add(event_name);

        }
        populateListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_options, menu);
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


    public void onAddEvent(View view){


        String eventTypes[] ={"Transportation","Reservations","Custom"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TripOptions.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Select Event Type");
        ListView listView = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,eventTypes);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Position is: " + position, Toast.LENGTH_LONG).show();
//                switch (position){
//                    case 0:
//                        Toast.makeText(getApplicationContext(), "Position is: " + position, Toast.LENGTH_LONG).show();
//                        Intent transportationIntent = new Intent(getApplicationContext(), TransportationEvent.class);
//                        startActivity(transportationIntent);
//                    case 1:
//                        Toast.makeText(getApplicationContext(), "Position is: " + position, Toast.LENGTH_LONG).show();
//                        Intent reservationIntent = new Intent(getApplicationContext(), ReservationEvent.class);
//                        startActivity(reservationIntent);
//                    case 2:
//                        Toast.makeText(getApplicationContext(), "Position is: " + position, Toast.LENGTH_LONG).show();
//                        Intent customIntent = new Intent(getApplicationContext(), CustomEvent.class);
//                        startActivity(customIntent);
//                }
                if(position == 0){
                    Intent transportationIntent = new Intent(getApplicationContext(), TransportationEvent.class);
                    startActivity(transportationIntent);
                }
                if(position == 1){
                    Intent reservationIntent = new Intent(getApplicationContext(), ReservationEvent.class);
                    startActivity(reservationIntent);
                }
                if(position == 2){
                    Intent customIntent = new Intent(getApplicationContext(), CustomEvent.class);
                    startActivity(customIntent);
                }
            }
        });
        alertDialog.show();
    }

    public void populateListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, events);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

}

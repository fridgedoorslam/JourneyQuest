package com.example.carl2tre.journeyquest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class EventList extends ListActivity {
    private DBAdapter db = new DBAdapter(this);
    public List<Trip> trips;
    public List<Event> events;
    public String event_name;
    public String newTrip;
    public long trip_id;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Intent intent = getIntent();
        newTrip = intent.getStringExtra("com.example.carl2tre.journeyquest.newTrip");
        trip_id = intent.getLongExtra("com.example.carl2tre.journeyquest.trip_id", 0);
        Log.d("EventList onCreate", "trip_id received: " + trip_id);
    }

    @Override
    public void onPause(){
        super.onPause();
        Intent intent = new Intent();
        intent.putExtra("com.example.carl2tre.newTrip", newTrip);
        intent.putExtra("com.example.carl2tre.trip_id", trip_id);

    }

    @Override
    public void onResume(){
        super.onResume();

        db = new DBAdapter(this);
        db.open();
        //trips = Trip.getAll(db);
        Log.d("EventList", "" + trip_id);
        events = Event.getAll(db, trip_id);
        db.close();

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_checked, events);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db

                final TextView eventTransportationType;


                final long dbPosition = position + 1;
                db.open();
                //Cursor c = db.getEvent(dbPosition);

                Cursor c = db.getEvent(dbPosition);
                String type = c.getString(c.getColumnIndex(db.KEY_EVENT_TRANSPORTATION_TYPE));
                final String event_name = c.getString(c.getColumnIndex(db.KEY_EVENT_NAME));
                final String transportation_type = c.getString(c.getColumnIndex(db.KEY_EVENT_TRANSPORTATION_TYPE));
                final String date = c.getString(c.getColumnIndex(db.KEY_EVENT_DATE));
                final String notes = c.getString(c.getColumnIndex(db.KEY_EVENT_NOTES));

                //Transportation dialog
                AlertDialog.Builder nameBuild = new AlertDialog.Builder(EventList.this);
                if(type.equals("Bus") || type.equals("Plane") || type.equals("Taxi") || type.equals("Car") || type.equals("Train") || type.equals("Walking")) {

                    nameBuild.setTitle("Event Name: " + event_name);
                    nameBuild.setMessage("Transportation Type: " + transportation_type + "\n"
                            + "Date: " + date + "\n"
                            + "Notes: " + notes + "\n");
                    nameBuild.setPositiveButton("Edit Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.open();
                                    Cursor c = db.getEvent(dbPosition);
                                    String type = c.getString(c.getColumnIndex(db.KEY_EVENT_TRANSPORTATION_TYPE));
                                    //Toast if transportation
                                    if (type.equals("Bus") || type.equals("Plane") || type.equals("Taxi") || type.equals("Car") || type.equals("Train") || type.equals("Walking")) {
                                        Intent transportationIntent = new Intent(getApplicationContext(), TransportationEvent.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("event_name", event_name);
                                        bundle.putString("transportation_type", transportation_type);
                                        bundle.putString("date", date);
                                        bundle.putString("notes", notes);
                                        transportationIntent.putExtras(bundle);
                                        startActivity(transportationIntent);
                                    }
                                    db.close();

                                }
                            });
                    nameBuild.setNegativeButton("Delete Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Delete event
                                }

                            });





                    nameBuild.show();
                }

                // Reservation Dialog
                if(type.equals("Hotel") || type.equals("Restaurant") || type.equals("Tour") || type.equals("Other")) {

                    nameBuild.setTitle("Event Name: " + event_name);
                    nameBuild.setMessage("Reservation Type: " + transportation_type + "\n"
                            + "Date: " + date + "\n"
                            + "Notes: " + notes + "\n");
                    nameBuild.setPositiveButton("Edit Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.open();
                                    Cursor c = db.getEvent(dbPosition);
                                    String type = c.getString(c.getColumnIndex(db.KEY_EVENT_TRANSPORTATION_TYPE));
                                    //Toast if transportation
                                    if(type.equals("Hotel") || type.equals("Restaurant") || type.equals("Tour") || type.equals("Other")) {
                                        Intent reservationIntent = new Intent(getApplicationContext(), ReservationEvent.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("event_name", event_name);
                                        bundle.putString("transportation_type", transportation_type);
                                        bundle.putString("date", date);
                                        bundle.putString("notes", notes);
                                        reservationIntent.putExtras(bundle);
                                        startActivity(reservationIntent);
                                    }
                                    db.close();

                                }
                            });
                    nameBuild.setNegativeButton("Delete Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Delete event
                                }

                            });
                    nameBuild.show();
                }

                // Dialog box for custom event
                if(type.equals(" ")) {
                    nameBuild.setTitle("Event Name: " + event_name);
                    nameBuild.setMessage("Date: " + date + "\n" + "Notes: " + notes + "\n");
                    nameBuild.setPositiveButton("Edit Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.open();
                                    Cursor c = db.getEvent(dbPosition);
                                    String type = c.getString(c.getColumnIndex(db.KEY_EVENT_TRANSPORTATION_TYPE));
                                    //Toast if transportation
                                    if (type.equals(" ")) {
                                        Intent customIntent = new Intent(getApplicationContext(), CustomEvent.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("event_name", event_name);
                                        bundle.putString("date", date);
                                        bundle.putString("notes", notes);
                                        customIntent.putExtras(bundle);
                                        startActivity(customIntent);
                                    }
                                    db.close();
                                }
                            });
                    nameBuild.setNegativeButton("Delete Event",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Delete event
                                }

                            });
                    nameBuild.show();
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

        String text = item.getTitle().toString();

        switch (item.getItemId()) {
            case R.id.item0:
                Intent tripIntent = new Intent(this, TripList.class);
                startActivity(tripIntent);
                return true;
            case R.id.item1:
                Intent eventIntent = new Intent(this, EventList.class);
                startActivity(eventIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onAddEvent(View view){


        String eventTypes[] ={"Transportation","Reservations","Custom"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventList.this);
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
                if(position == 0){
                    Intent transportationIntent = new Intent(getApplicationContext(), TransportationEvent.class);
                    transportationIntent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                    transportationIntent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                    startActivity(transportationIntent);
                }
                if(position == 1){
                    Intent reservationIntent = new Intent(getApplicationContext(), ReservationEvent.class);
                    reservationIntent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                    reservationIntent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                    startActivity(reservationIntent);
                }
                if(position == 2){
                    Intent customIntent = new Intent(getApplicationContext(), CustomEvent.class);
                    customIntent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                    customIntent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                    startActivity(customIntent);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, TripList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

package com.example.carl2tre.journeyquest;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class TripList extends ListActivity {
    private DBAdapter db;
    List<Trip> trips;
    public String newTrip;
    public long trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);


    }

    @Override
    public void onResume(){
        super.onResume();
        db = new DBAdapter(this);
        db.open();
        trips = Trip.getAll(db);
        db.close();

        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_checked, trips);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db

                final long trip_id = trips.get(position).getId();
//                trip_id = position;

                db.open();
                Cursor c = db.getTrip(trip_id);

                Intent intent = new Intent(TripList.this, EventList.class);
                intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                startActivity(intent);

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

    public void onTap(View view){
        final EditText tripName = new EditText(this);
        AlertDialog.Builder nameBuild = new AlertDialog.Builder(this);
        nameBuild.setTitle("Name your trip");
        tripName.setInputType(InputType.TYPE_CLASS_TEXT);
        nameBuild.setView(tripName);
        nameBuild.setPositiveButton("Start Planning Trip",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        newTrip = tripName.getText().toString();
                        trip_id = db.insertTrip(newTrip);
                        Toast.makeText(getApplicationContext(), newTrip + "added with id" + trip_id, Toast.LENGTH_LONG).show();
                        db.close();
                        onResume();
                        Intent intent = new Intent(getApplicationContext(), EventList.class);
                        intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                        intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                        startActivity(intent);
                    }
                });
        nameBuild.show();
    }



}

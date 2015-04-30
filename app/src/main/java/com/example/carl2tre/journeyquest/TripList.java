package com.example.carl2tre.journeyquest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TripList extends ListActivity {
    public DateFormat format = DateFormat.getDateInstance();
    public Calendar calendar = Calendar.getInstance();
    public String tripStartDate;
    public String tripEndDate;
    private DBAdapter db;
    List<Trip> trips;
    public String newTrip;
    public long trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        ImageButton addTripButton = (ImageButton) findViewById(R.id.imageButton);
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TripList.this);
                dialog.setTitle("Create New Trip");
                dialog.setContentView(R.layout.trip_dialog);
                dialog.show();

                final EditText tripName = (EditText) dialog.findViewById(R.id.trip_name);
                final Button startDateButton = (Button) dialog.findViewById(R.id.startDateButton);
                final Button endDateButton = (Button) dialog.findViewById(R.id.endDateButton);
                Button startJourneyButton = (Button) dialog.findViewById(R.id.start_journey);
                final Button cancelJourneyButton = (Button) dialog.findViewById(R.id.cancel_trip_creation);

                // Set endDateButton disabled to prevent stupid users
                endDateButton.setEnabled(false);

                startDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        new DatePickerDialog(TripList.this, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                tripStartDate = format.format(calendar.getTime()).toString();
                                endDateButton.setEnabled(true);
                            };
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                endDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        new DatePickerDialog(TripList.this, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                tripEndDate = format.format(calendar.getTime()).toString();

                            };
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });




                startJourneyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.open();
                        newTrip = tripName.getText().toString();
                        trip_id = db.insertTrip(newTrip, tripStartDate, tripEndDate);
                        db.close();
                        // Check if start date is before end date
                        if(tripEndDate != null) {
                            String date1 = tripStartDate;
                            String date2 = tripEndDate;
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

                            if (sMonth1.equals("Jan")) {
                                month1 = 1;
                            }
                            if (sMonth1.equals("Feb")) {
                                month1 = 2;
                            }
                            if (sMonth1.equals("Mar")) {
                                month1 = 3;
                            }
                            if (sMonth1.equals("Apr")) {
                                month1 = 4;
                            }
                            if (sMonth1.equals("May")) {
                                month1 = 5;
                            }
                            if (sMonth1.equals("Jun")) {
                                month1 = 6;
                            }
                            if (sMonth1.equals("Jul")) {
                                month1 = 7;
                            }
                            if (sMonth1.equals("Aug")) {
                                month1 = 8;
                            }
                            if (sMonth1.equals("Sep")) {
                                month1 = 9;
                            }
                            if (sMonth1.equals("Oct")) {
                                month1 = 10;
                            }
                            if (sMonth1.equals("Nov")) {
                                month1 = 11;
                            }
                            if (sMonth1.equals("Dec")) {
                                month1 = 12;
                            }

                            if (sMonth2.equals("Jan")) {
                                month2 = 1;
                            }
                            if (sMonth2.equals("Feb")) {
                                month2 = 2;
                            }
                            if (sMonth2.equals("Mar")) {
                                month2 = 3;
                            }
                            if (sMonth2.equals("Apr")) {
                                month2 = 4;
                            }
                            if (sMonth2.equals("May")) {
                                month2 = 5;
                            }
                            if (sMonth2.equals("Jun")) {
                                month2 = 6;
                            }
                            if (sMonth2.equals("Jul")) {
                                month2 = 7;
                            }
                            if (sMonth2.equals("Aug")) {
                                month2 = 8;
                            }
                            if (sMonth2.equals("Sep")) {
                                month2 = 9;
                            }
                            if (sMonth2.equals("Oct")) {
                                month2 = 10;
                            }
                            if (sMonth2.equals("Nov")) {
                                month2 = 11;
                            }
                            if (sMonth2.equals("Dec")) {
                                month2 = 12;
                            }

                            int var = year1 < year2 ? -1 : year1 > year2 ? 1 :
                                    month1 < month2 ? -1 : month1 > month2 ? 1 :
                                            day1 < day2 ? -1 : 1;

                            if (var == 1) {
                                Toast.makeText(getApplicationContext(), "End date comes before start date. Consider revising.", Toast.LENGTH_LONG).show();
                            }
                        }
                        onResume();
                        Intent intent = new Intent(getApplicationContext(), EventList.class);
                        intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                        intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

                cancelJourneyButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        db = new DBAdapter(this);
        db.open();
        trips = Trip.getAll(db);
        //db.close();

        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_checked, trips);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db
                final String trip_name = trips.get(position).getName();
                final long dbPosition = trips.get(position).getId();
                db.open();
                Cursor c = db.getTrip(dbPosition);
                final String trip_begin_date = c.getString(c.getColumnIndex(db.KEY_TRIP_BEGIN_DATE));
                final String trip_end_date = c.getString(c.getColumnIndex(db.KEY_TRIP_END_DATE));
                db.close();

                AlertDialog.Builder nameBuild = new AlertDialog.Builder(TripList.this);
                nameBuild.setTitle(trip_name);
                nameBuild.setMessage("Begin Date: " + trip_begin_date + "\nEnd Date: " + trip_end_date);
                nameBuild.setPositiveButton("View Events",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.open();
                                Intent intent = new Intent(TripList.this, EventList.class);
                                intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
                                intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
                                startActivity(intent);
                                db.close();

                            }
                        });
                nameBuild.setNegativeButton("Delete Trip",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.open();
                                db.deleteTrip(dbPosition);
                                onResume();
                                db.close();
                            }
                        });
                nameBuild.show();



                    }
            });
       db.close();

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
}




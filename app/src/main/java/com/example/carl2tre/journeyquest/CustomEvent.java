package com.example.carl2tre.journeyquest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

    public class CustomEvent extends Activity implements View.OnClickListener {
        DBAdapter db;
        public EditText eventName;
        public Button eventDate;
        public Button eventTime;
        public EditText eventNotes;
        public String event_date;
        public String event_name;
        public String event_time;
        public String event_notes;
        public long trip_id;
        public String newTrip;
        public Button setDateButton;
        public Button setTimeButton;
        DateFormat format = DateFormat.getDateInstance();
        Calendar calendar = Calendar.getInstance();
        public TimePickerDialog timePicker;
        public int mHour;
        public int mMinute;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_custom_event);

            //Grab objects from xml layout
            setDateButton = (Button) findViewById(R.id.set_date_button);
            setTimeButton = (Button) findViewById(R.id.event_time);
            Intent intent = getIntent();
            newTrip = intent.getStringExtra("com.example.carl2tre.journeyquest.newTrip");
            trip_id = intent.getLongExtra("com.example.carl2tre.journeyquest.trip_id", 0);
            db = new DBAdapter(this);
            db.open();
            eventName = (EditText) findViewById(R.id.event_name);
            eventDate = (Button) findViewById(R.id.set_date_button);
            eventTime = (Button) findViewById(R.id.event_time);
            eventNotes = (EditText) findViewById(R.id.event_notes);

            //Get bundle for editing event
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                eventName.setText(bundle.getString("event_name"));
                eventNotes.setText(bundle.getString("notes"));
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            //grab trip name and id
            Intent intent = getIntent();
            newTrip = intent.getStringExtra("com.example.carl2tre.journeyquest.newTrip");
            trip_id = intent.getLongExtra("com.example.carl2tre.journeyquest.trip_id", 0);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_reservation_event, menu);
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

        //if back button is tapped do nothing
        @Override
        public void onBackPressed() { }

        //finishes activity if cancel is tapped
        public void onCancel(View view) {
            finish();
        }

        //inject event fields into database
        public void onSubmit(View view) {
            event_name = eventName.getText().toString();
            event_notes = eventNotes.getText().toString();
            db = new DBAdapter(this);
            db.open();
            if(event_date == null) {
                Toast.makeText(this, "Date cannot be null. Event not created.", Toast.LENGTH_LONG).show();
            }
            long eventId = db.insertEvent(trip_id, event_name, " ", event_date, event_time, " ", " ", event_notes);
            db.close();
            Intent intent = new Intent(CustomEvent.this, EventList.class);
            intent.putExtra("com.example.carl2tre.journeyquest.newTrip", newTrip);
            intent.putExtra("com.example.carl2tre.journeyquest.trip_id", trip_id);
            startActivity(intent);
        }

        //Sets date
        public void setDate() {
            new DatePickerDialog(CustomEvent.this, d, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }

        //Create date picker
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                event_date = format.format(calendar.getTime()).toString();
            }
        };

        //Override onClick to set date
        @Override
        public void onClick(View arg0) {
            setDate();
        }

        //Creates time picker dialog
        public void onTimeTapped(View view) {
            final Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);

            timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    event_time = mHour + ":" + mMinute;

                }

            }, mHour, mMinute, false);

            timePicker.show();

        }
    }

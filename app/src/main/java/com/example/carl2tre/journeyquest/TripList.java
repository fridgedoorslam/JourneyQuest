package com.example.carl2tre.journeyquest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TripList extends ActionBarActivity {
    public String newTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
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
                    public void onClick(DialogInterface dialog, int id) {
                        newTrip = tripName.getText().toString();
                        Toast.makeText(getApplicationContext(), newTrip, Toast.LENGTH_LONG).show();

                    }
                });

        nameBuild.show();


    }
}

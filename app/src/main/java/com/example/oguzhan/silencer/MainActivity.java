package com.example.oguzhan.silencer;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ListView list;
    ArrayList<LocRecord> locList;
    Context cont;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        final Database db = new Database(getApplicationContext());
        locList = db.getLocations();

        cont = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //SERVICE STARTED HERE
        Intent serviceintent = new Intent(MainActivity.this, LocationService.class);
        startService(serviceintent);
            //GPS TRACKER FOR MainActivity and Database operations
        final GPSTracker mGPS = new GPSTracker(this);



        list = (ListView) findViewById(R.id.listMain);

        MyListAdapter adapter = new MyListAdapter(MainActivity.this, locList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int locId = locList.get(position).getId();
                db.deleteRecord(locId);
                locList = db.getLocations();
                MyListAdapter adapter = new MyListAdapter(MainActivity.this, locList);
                list.setAdapter(adapter);
            }
        });

        btn = (Button) findViewById(R.id.btn1);
        //final AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        // audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Where is this place?? : ");
                final EditText input = new EditText(MainActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();

                        if (name.equals("")) {
                            Toast.makeText(MainActivity.this, "Please enter a name for the location", Toast.LENGTH_SHORT).show();
                            //dialog.cancel();
                        } else {

                            if (mGPS.canGetLocation) {
                                mGPS.getLocation();
                                db.addLocation(name, mGPS.getLatitude(), mGPS.getLongtitude());
                            }
                            locList = db.getLocations();
                            MyListAdapter adapter = new MyListAdapter(MainActivity.this, locList);
                            list.setAdapter(adapter);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }
}

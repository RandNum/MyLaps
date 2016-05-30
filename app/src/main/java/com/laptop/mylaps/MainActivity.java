package com.laptop.mylaps;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;

import com.laptop.models.Kart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener{
    LinkedList<String> lapLinkedList;
    String nextLapEvent;
    String[] mark;
    Date time, markTime = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //rest of init:
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "More information", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void startButton(View view) {
        ((Chronometer) findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime() - (12 * 60 * 60000));
        ((Chronometer) findViewById(R.id.chronometer)).start();

        lapLinkedList = new LinkedList();

        BufferedReader reader;
        try{
            //raw
            final InputStream file = getResources().openRawResource(R.raw.karttimes);

            reader = new BufferedReader(new InputStreamReader(file));
            String label = reader.readLine();
            String line = reader.readLine();
            while(line != null){
                //Log.d("lap: ", line);
                lapLinkedList.add(line);
                line = reader.readLine();
            }

        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        Log.d("linkedList: ", lapLinkedList.toString());


        nextLapEvent = lapLinkedList.pop();
        mark = nextLapEvent.split(",");
        Kart.getKartByNumber(mark[0]).addLapTime(mark[1]);
//      onChronometerTick(((Chronometer) findViewById(R.id.chronometer)));

        while(!lapLinkedList.isEmpty()) {
            nextLapEvent = lapLinkedList.pop();
            mark = nextLapEvent.split(",");
            Kart.getKartByNumber(mark[0]).addLapTime(mark[1]);
        }

        //test totalRaceTime method with static value of "2"
        Kart myKart = Kart.getKartByNumber("2");
        myKart.totalRaceTime();
    }

    public void stopButton(View view) {
        ((Chronometer) findViewById(R.id.chronometer)).stop();
    }

    public void onChronometerTick(Chronometer chronometer) {

//        try {
//
//            time = new SimpleDateFormat("HH:mm:ss").parse(chronometer.getText().toString());
//            markTime = new SimpleDateFormat("HH:mm:ss").parse(mark[1].toString());
//            if(time.after(markTime)){
//                Kart.getKartByNumber(mark[0]).addLapTime(mark[1]);
//                Log.d("Added kart", mark[0].toString());
//                nextLapEvent = lapLinkedList.pop();
//                mark = nextLapEvent.split(",");
//
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

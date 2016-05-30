package com.laptop.models;



import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Kart Object will be dynamically updated and constructed by factory "lazy" initialization
 * in the karts Map<String, kart>
 */
public class Kart {

    private static Map<String, Kart> karts = new HashMap<>();

    private String kartNumber;
    private ArrayList<String> lapTimes = new ArrayList<>();

    public Kart(String number) {
        kartNumber = number;
    }



    public static Kart getKartByNumber(String numb) {
         Kart kart;
        //Possible concurrency issue on the read to karts
        synchronized (karts) {
            if (!karts.containsKey(numb)) {
                // "Lazy" factory initialisation
                kart = new Kart(numb);
                karts.put(numb, kart);
            } else {
                // OK, it's available currently
                kart = karts.get(numb);
            }
        }
        return kart;
    }

    public void addLapTime(String time) {
        lapTimes.add(time);
    }

    public void totalRaceTime(){
        //return sum
        //test time list. after calculate the sumation
        for (String a: lapTimes) {
                Log.d("Time: ", a.toString());
            }
        }

    public void fastestLap(){
        //using time array return min value
        
    }

}

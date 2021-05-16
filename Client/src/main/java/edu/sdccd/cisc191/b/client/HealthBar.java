package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class HealthBar extends Bar {

    //private long time;

    public HealthBar(int maxVal){
        super(maxVal,false,new Color(38,203,36));
    }

    public void regenHealth(int healthIncrease, long regenTime){
        long endSeconds = 0, currSeconds;
        long startTime = System.currentTimeMillis();

        // While there is time remaining and if the current health is
        // not maxVal, increase current health .
        while(endSeconds < regenTime){
            currSeconds = endSeconds;

            //Wait until the next second
            while(endSeconds != currSeconds + 1) {
                endSeconds = (System.currentTimeMillis() - startTime) / 1000;
            }

            if(getCurrentVal() <  getMaxVal())
                increaseCurrent(healthIncrease);
            if(getCurrentVal() > getMaxVal())
                setCurrentVal(getMaxVal());

            endSeconds = (System.currentTimeMillis() - startTime) / 1000;

            //System.out.println("Seconds Passed: " + endSeconds);
        }
    }




    /*public long getTime() {
        return time;
    }*/


    /*public static void main (String[] args) {
        HealthBar hb = new HealthBar(50);
        hb.decreaseCurrent(25);
        System.out.println("Current Health: " + (hb.getCurrentVal()));

        hb.regenHealth(2, 10);
        System.out.println("Time passed: " + (hb.getTime()));
        System.out.println("Current Health: " + (hb.getCurrentVal()));

    }*/
}



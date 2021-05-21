package edu.sdccd.cisc191.b.client;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class HealthBar extends Bar {

    private long time;
    Timer timer;

    public HealthBar(int maxVal){
        super(maxVal,false,new Color(38,203,36));
        timer = new Timer();
    }

    public void regenHealth(int healthIncrease, long regenTime){
        long endSeconds = 0, currSeconds;
        long startTime = System.currentTimeMillis();

        while(regenTime > 0) {
            regenTime--;
            timer.schedule(new Regen(healthIncrease), (int) 5 * 1000);


        }


        // While there is time remaining and if the current health is
        // not maxVal, increase current health .
        //while(endSeconds < regenTime){
            //currSeconds = endSeconds;

            //Wait until the next second
            //while(endSeconds != currSeconds + 1) {
                //endSeconds = (System.currentTimeMillis() - startTime) / 1000;
            //}

            //if(getCurrentVal() <  getMaxVal())
                //increaseCurrent(healthIncrease);
            //if(getCurrentVal() > getMaxVal())
                //setCurrentVal(getMaxVal());

            //endSeconds = (System.currentTimeMillis() - startTime) / 1000;

            //System.out.println("Seconds Passed: " + endSeconds);
        //}
    }

    class Regen extends TimerTask {
        int healthIncrease;

        public Regen(int healthIncrease) {
            this.healthIncrease = healthIncrease;
        }

        public void run() {
            if (getCurrentVal() < getMaxVal())
                increaseCurrent(healthIncrease);
            if (getCurrentVal() > getMaxVal())
                setCurrentVal(getMaxVal());
            System.out.println("Current Health: " + (getCurrentVal()));
            timer.cancel(); //Terminate the timer thread
        }
    }


    public long getTime() {
        return time;
    }


    //public static void main (String[] args) {
//        HealthBar hb = new HealthBar(50);
//        hb.decreaseCurrent(25);
//        System.out.println("Current Health: " + (hb.getCurrentVal()));
//
//        hb.regenHealth(2, 10);
//        //System.out.println("Time passed: " + (hb.getTime()));
//        System.out.println("Current Health: " + (hb.getCurrentVal()));

   // }
}



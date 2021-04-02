package edu.sdccd.cisc191.client;

import java.awt.*;

public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    private HealthBar hpView;

    public EnemyShip(Point position, int type){
        super(0,0,position);
        if (type == 1){

        }
    }

    public int getScoreToDrop() {
        return scoreToDrop;
    }

    public void setScoreToDrop(int scoreToDrop) {
        this.scoreToDrop = scoreToDrop;
    }

    @Override
    public void shoot(){

    }
}

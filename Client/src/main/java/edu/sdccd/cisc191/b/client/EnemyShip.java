package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    private HealthBar hpView;
    int maxHp, currentHp;
    int x,y;
    int moveSpeed;

    public EnemyShip(int x, int y, int type){
        super(0,0);
        this.type = type;
        if (type == 1){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 5;
            scoreToDrop = 1;
        }

        if (type == 2){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 10;
            scoreToDrop = 2;
        }

        if (type == 3){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 15;
            scoreToDrop = 3;
        }
    }

    public int getScoreToDrop() {
        return scoreToDrop;
    }

    public void setScoreToDrop(int scoreToDrop) {
        this.scoreToDrop = scoreToDrop;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    @Override
    public void shoot(){

    }
}

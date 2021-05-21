package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    private HealthBar hpView;
    int maxHp, currentHp;
    int x,y;
    int moveSpeed;
    boolean hit;

    public EnemyShip(int x, int y, int type){
        super(0,0);
        this.x = x;
        this.y = y;
        this.type = type;
        hit = false;
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

    public Rectangle getHitBox() {
        return new Rectangle(getX(),getY(), 15,15);
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Bullet shoot(){
        return new Bullet(getX(),getY());
    }
}

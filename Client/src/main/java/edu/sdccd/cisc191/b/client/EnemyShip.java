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
        if (type == 1){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 2;
            scoreToDrop = 1;
        }

        if (type == 2){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 4;
            scoreToDrop = 2;
        }

        if (type == 3){
            hpView = new HealthBar(maxHp);
            hpView = new HealthBar(currentHp);
            moveSpeed = 6;
            scoreToDrop = 3;
        }
    }

    public Rectangle getHitBox() {
        return new Rectangle(getX(),getY(), 50,50);
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getScoreToDrop() {
        return scoreToDrop;
    }

    public void setScoreToDrop(int scoreToDrop) {
        this.scoreToDrop = scoreToDrop;
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

        return null;
    }
}

package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    int x,y;
    int moveSpeed;
    boolean hit;

    public EnemyShip(int x, int y, int type){
        super(0,0);
        this.x = x;
        this.y = y;
        this.type = type;
        if (type == 1){
            moveSpeed = 2;
            scoreToDrop = 3;
        }

        if (type == 2){
            moveSpeed = 3;
            scoreToDrop = 10;
        }

        if (type == 3){
            moveSpeed = 6;
            scoreToDrop = 25;
        }
        hit = false;
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

    public int getType() {
        return type;
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

}

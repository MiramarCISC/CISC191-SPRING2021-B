package edu.sdccd.cisc191.b.client;


import java.awt.*;
/**
 * the enemy ship class is extended class of ship class and it specify how will the enemy ships will
 * move and how much score will user get.
 */
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
            scoreToDrop = 7;
        }

        if (type == 2){
            moveSpeed = 4;
            scoreToDrop = 15;
        }

        if (type == 3){
            moveSpeed = 6;
            scoreToDrop = 35;
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

package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    boolean hit;
    private int moveSpeed;

    public EnemyShip(int x, int y, int type){
        super(x,y);
        this.type = type;
        if (type == 1){
            setMoveSpeed(2);
            scoreToDrop = 7;
        }

        if (type == 2){
            setMoveSpeed(4);
            scoreToDrop = 15;
        }

        if (type == 3){
            setMoveSpeed(6);
            scoreToDrop = 35;
        }
        hit = false;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getType() {
        return type;
    }

    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), 50,50);
    }

    public boolean isHit() { return hit; }

    public void setHit(boolean hit) { this.hit = hit; }

    public int getScoreToDrop() {
        return scoreToDrop;
    }
}

package edu.sdccd.cisc191.b.client;


import java.awt.*;
/**
 * The bullet class provides logic for the player bullet
 */
public class Bullet {
    private int x;
    private int y;
    private Bullet next;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        next = null;
    }

    public Rectangle getHitBox() {
        return new Rectangle(getX(),getY(), 2,7);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bullet getNext() {
        return next;
    }

    public void setNext(Bullet next) {
        this.next = next;
    }



}

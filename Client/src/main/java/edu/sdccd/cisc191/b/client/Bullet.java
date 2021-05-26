package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class Bullet {
    private int x,y;
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
}

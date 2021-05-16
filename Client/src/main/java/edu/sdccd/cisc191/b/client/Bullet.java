package edu.sdccd.cisc191.b.client;

public class Bullet {
    private int x;
    private int y;
    boolean isVisible;
    int bulletSpeed;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        bulletSpeed = 10;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

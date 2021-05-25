package edu.sdccd.cisc191.b.client;

public abstract class Ship {
    private boolean alive;
    int x, y;

    public Ship(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public abstract Bullet shoot();

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

package edu.sdccd.cisc191.b.client;

/**
 * ship is a super class which is define the basic attributes of player ship and enemy ships
 */
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

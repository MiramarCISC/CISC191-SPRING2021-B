package edu.sdccd.cisc191.b.client;

public class Ship {
    private int moveSpeed;
    private int x,y;

    public Ship(int x, int y){
        this.x = x;
        this.y = y;
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

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) { this.moveSpeed = moveSpeed; }
}

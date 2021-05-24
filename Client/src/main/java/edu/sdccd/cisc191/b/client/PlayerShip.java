package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class PlayerShip extends Ship{
    private int moveSpeed;
    private int lives;
    int x, y;
    boolean moveLeft, moveUp, moveRight, moveDown, bullet;

    public PlayerShip(int x, int y){
        super(x,y);
        lives = 3;
        moveSpeed = 4;
        setAlive(true);
    }

    public Rectangle getHitBox() { return new Rectangle(x, y, 50, 50); }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives(){ lives--; }

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

    @Override
    public Bullet shoot() {
        return new Bullet(getX(),getY());

    }
}

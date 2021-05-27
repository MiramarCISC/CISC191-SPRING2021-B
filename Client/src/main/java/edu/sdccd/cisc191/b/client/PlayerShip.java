package edu.sdccd.cisc191.b.client;

import java.awt.*;
/**
 * player ship class is also extended class of ship class and this class will specify how many life players have
 * and the speed of player ship
 */

public class PlayerShip extends Ship{
    private int moveSpeed;
    private int lives;
    int x, y;
    boolean moveLeft, moveUp, moveRight, moveDown,bullet;
    Rectangle hitBox;

    public PlayerShip(int x, int y){
        super(x,y);
        lives = 3;
        moveSpeed = 4;
        setAlive(true);
        hitBox = new Rectangle(x, y, 50, 50);
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

    public Rectangle getHitBox() {
        hitBox.setLocation(getX(), getY());
        return hitBox;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives(){ lives--; }

}

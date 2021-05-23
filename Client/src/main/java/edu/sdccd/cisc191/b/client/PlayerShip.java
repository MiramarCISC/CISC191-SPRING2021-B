package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class PlayerShip extends Ship{
    private int moveSpeed;
    private int lives;
    int x, y;
    boolean moveLeft, moveUp, moveRight, moveDown;
    Rectangle hitBox;

    public PlayerShip(int x, int y){
        super(x,y);
        lives = 3;
        moveSpeed = 4;
        setAlive(true);
        hitBox = new Rectangle(x, y, 50, 50);
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

    @Override
    public Bullet shoot() {
        return new Bullet(getX(),getY());

    }
}

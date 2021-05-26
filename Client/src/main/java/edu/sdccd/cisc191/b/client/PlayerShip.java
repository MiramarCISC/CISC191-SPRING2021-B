package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class PlayerShip extends Ship{
    private int lives;
    private boolean alive;
    private boolean moveLeft, moveUp, moveRight, moveDown, bullet;

    public PlayerShip(int x, int y){
        super(x,y);
        lives = 3;
        setMoveSpeed(4);
        setAlive(true);
    }

    public Rectangle getHitBox() { return new Rectangle(getX(), getY(), 50, 50); }

    public boolean isAlive() { return alive; }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives(){ lives--; }

    public boolean isMoveLeft() { return moveLeft; }

    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }

    public boolean isMoveUp() { return moveUp;}

    public void setMoveUp(boolean moveUp) { this.moveUp = moveUp; }

    public boolean isMoveRight() { return moveRight; }

    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }

    public boolean isMoveDown() { return moveDown; }

    public void setMoveDown(boolean moveDown) { this.moveDown = moveDown; }

    public boolean isBullet() { return bullet; }

    public void setBullet(boolean bullet) { this.bullet = bullet; }
}

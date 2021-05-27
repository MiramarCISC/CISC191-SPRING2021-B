package edu.sdccd.cisc191.b.client;

import java.awt.*;
/**
 * Player ship class is also extended class of ship class and this class will specify how many life players have and the speed of player ship.
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

    /**
     *  Accesses the player ship's survival stats.
     *
     * @return the survival stats of the player ship
     */
    public Rectangle getHitBox() {
        hitBox.setLocation(getX(), getY());
        return hitBox;
    }

    /**
     * Accesses the player's speed value.
     *
     * @return the speed value
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Accesses the player's current amount of lives.
     *
     * @return  the current total number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Decrease the current amount number of lives.
     */
    public void decrementLives(){ lives--; }

}

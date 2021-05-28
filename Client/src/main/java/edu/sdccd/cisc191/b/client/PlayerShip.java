package edu.sdccd.cisc191.b.client;

import java.awt.*;

/**
 * Player ship class extends the Ship class. It defines features for the player, such as its hit box, current number of lives, and its collision status (also known as its survival status).
 *
 * @author Joaquin Dicang, Sholehani Hafezi, Kim Lim
 */
public class PlayerShip extends Ship{
    private int lives;
    private boolean alive;
    private boolean moveLeft, moveUp, moveRight, moveDown, bullet;

    /**
     * Initializes the player ship's x and y coordinates, default number of lives, its movement speed, and its default survival status as true.
     *
     * @param x the player ship's x coordinate
     * @param y the player ship's y coordinate
     */
    public PlayerShip(int x, int y){
        super(x,y);
        lives = 3;
        setMoveSpeed(4);
        setAlive(true);
    }

    /**
     * Generates player ship's hit box. This handles the player ship's collision events.
     *
     * @return rectangle object that represents the hit box
     * @see Rectangle
     */
    public Rectangle getHitBox() { return new Rectangle(getX(), getY(), 50, 50); }

    /**
     * Accesses the current survival status of the player ship.
     *
     * @return the boolean value representing if the player ship is alive or not
     */
    public boolean isAlive() { return alive; }

    /**
     * Changes the current survival status of the player ship to the passed argument value.
     *
     * @param alive the new survival status of the player ship
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Accesses the player ship's current number of lives.
     *
     * @return the current total number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Decrease the current amount number of lives.
     */
    public void decrementLives(){ lives--; }

    /**
     * Accesses the current status of the player ship's left movement.
     *
     * @return the boolean value determining if the player is moving left
     */
    public boolean isMoveLeft() { return moveLeft; }

    /**
     * Sets the current status of the player ship's left movement with the passed argument value.
     */
    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }

    /**
     * Accesses the current status of the player ship's up movement.
     *
     * @return the boolean value determining if the player is moving up
     */
    public boolean isMoveUp() { return moveUp;}

    /**
     * Sets the current status of the player ship's up movement with the passed argument value.
     */
    public void setMoveUp(boolean moveUp) { this.moveUp = moveUp; }

    /**
     * Accesses the current status of the player ship's right movement.
     *
     * @return the boolean value determining if the player is moving right
     */
    public boolean isMoveRight() { return moveRight; }

    /**
     * Sets the current status of the player ship's right movement with the passed argument value.
     */
    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }

    /**
     * Accesses the current status of the player ship's down movement.
     *
     * @return the boolean value determining if the player is moving down
     */
    public boolean isMoveDown() { return moveDown; }

    /**
     * Sets the current status of the player ship's down movement with the passed argument value.
     */
    public void setMoveDown(boolean moveDown) { this.moveDown = moveDown; }

    /**
     * Accesses the current status of the player ship's bullet.
     *
     * @return the boolean value determining if the player shot a bullet
     */
    public boolean isBullet() { return bullet; }

    /**
     * Sets the current status of the player ship's bullet with the passed argument value.
     */
    public void setBullet(boolean bullet) { this.bullet = bullet; }
}

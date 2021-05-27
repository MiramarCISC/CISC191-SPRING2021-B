package edu.sdccd.cisc191.b.client;


import java.awt.*;
/**
 * The bullet class provides logic for the player bullet.
 */
public class Bullet {
    private int x;
    private int y;
    private Bullet next;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        next = null;
    }
    /**
     * Generates hit box for the bullet. This handles the bullet's collusion events.
     *
     * @return rectangle object that represent the hit box
     */
    public Rectangle getHitBox() {
        return new Rectangle(getX(),getY(), 2,7);
    }
    /**
     * Accesses the bullet's x coordinate.
     *
     * @return the X position
     */
    public int getX() {
        return x;
    }
    /**
     * Accesses the bullet's Y coordinate.
     *
     * @return the Y position
     */
    public int getY() {
        return y;
    }
    /**
     * Sets the bullet's X coordinate.
     *
     * @return the X position
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Sets the bullet's Y coordinate.
     *
     * @return the Y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Use to access the Bullet class' bullet object.
     *
     * @return the bullet object
     */
    public Bullet getNext() {
        return next;
    }

    /**
     * Sets the Bullet class' bullet object to reference a different bullet object.
     *
     * @param next the newly referenced object
     */
    public void setNext(Bullet next) {
        this.next = next;
    }



}

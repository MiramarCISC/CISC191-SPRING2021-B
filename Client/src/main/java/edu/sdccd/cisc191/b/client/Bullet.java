package edu.sdccd.cisc191.b.client;

import java.awt.*;
/**
 * The bullet class provides logic for the player bullet.
 *
 * @author Sholehani Hafezi
 */
public class Bullet {
    private int x,y;
    private Bullet next;

    /**
     * Initializes the x and y positions of the bullet with parameters.
     * Initializes the Bullet instance variable to null, which is used for a linked list.
     *
     * @param x the bullet's x coordinate
     * @param y the bullet's y coordinate
     */
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        next = null;
    }

    /**
     * Generates hit box for the bullet. This handles the bullet's collusion events.
     *
     * @return rectangle object that represent the hit box
     * @see Rectangle
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
}

package edu.sdccd.cisc191.b.client;

import java.awt.*;
/**
 * The enemy ship class extended the Ship class. It defines additional features such as enemy's type, a hit box, the score drop value, and its collision status (also known as its survival status).
 *
 * @author Joaquin Dicang, Sholehani Hafezi, Kim Lim
 */
public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    boolean hit;

    /**
     * Initializes the enemy's ships x and y coordinates and its type according to passed argument values.
     * Type 1 enemy has a movement speed of 2 and drops a score of 7.
     * Type 2 enemy has a movement speed of 4 and drops a score of 15.
     * Type 3 enemy has a movement speed of 6 and drops a score of 35.
     *
     * @param x the enemy ship's x coordinate
     * @param y the enemy ship's y coordinate
     * @param type the enemy ship's type
     */
    public EnemyShip(int x, int y, int type){
        super(x,y);
        this.type = type;
        if (type == 1){
            setMoveSpeed(2);
            scoreToDrop = 7;
        }

        if (type == 2){
            setMoveSpeed(4);
            scoreToDrop = 15;
        }

        if (type == 3){
            setMoveSpeed(6);
            scoreToDrop = 35;
        }
        hit = false;
    }

    /**
     * Specifies the enemy ship's type.
     *
     * @return the type's number value
     */
    public int getType() {
        return type;
    }

    /**
     * Generates enemy ship's hit box. This handles the enemy ship's collision events.
     *
     * @return rectangle object that represents the hit box
     * @see Rectangle
     */
    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), 50,50);
    }

    /**
     * Accesses the enemy ship's survival stats.
     *
     * @return the survival stats of the enemy ship
     */
    public boolean isHit() { return hit; }

    /**
     * Sets the enemy ship's survival stats.
     *
     * @param hit the current survival stats of the enemy ship
     */
    public void setHit(boolean hit) { this.hit = hit; }

    /**
     * Accesses enemy's score points given to the player when the enemy ship's dies.
     *
     * @return the score value
     */
    public int getScoreToDrop() {
        return scoreToDrop;
    }
}

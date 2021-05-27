package edu.sdccd.cisc191.b.client;


import java.awt.*;
/**
 * The enemy ship class is extended class of ship class and it specify how will the enemy ships will move and how much score will user get.
 */
public class EnemyShip extends Ship{
    private int type;
    private int scoreToDrop;
    int x,y;
    int moveSpeed;
    boolean hit;

    public EnemyShip(int x, int y, int type){
        super(0,0);
        this.x = x;
        this.y = y;
        this.type = type;
        if (type == 1){
            moveSpeed = 2;
            scoreToDrop = 7;
        }

        if (type == 2){
            moveSpeed = 4;
            scoreToDrop = 15;
        }

        if (type == 3){
            moveSpeed = 6;
            scoreToDrop = 35;
        }
        hit = false;
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
     * Specifies enemy ship's type.
     *
     * @return the type's number value
     */
    public int getType() {
        return type;
    }

    /**
     * Generates enemy ship's hit box. This handles enemy ship's collusion events.
     *
     * @return rectangle object that represent the hit box
     */
    public Rectangle getHitBox() {
        return new Rectangle(getX(),getY(), 50,50);
    }

    /**
     *  Accesses the enemy ship's survival stats.
     *
     * @return the survival stats of the enemy ship
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Sets the enemy ship's survival stats.
     *
     * @param hit the current survival stats of the enemy ship
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    /**
     *  Accesses enemy's score points given to the player when the enemy ship's dies.
     *
     * @return the score value
     */
    public int getScoreToDrop() {
        return scoreToDrop;
    }

}

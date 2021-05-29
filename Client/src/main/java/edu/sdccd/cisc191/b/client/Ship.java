package edu.sdccd.cisc191.b.client;

/**
 * Ship is a super class that defines the coordinates and speed of the player and enemy ships.
 *
 * @author Joaquin Dicang, Sholehani Hafezi, Kim Lim
 */
public class Ship {
    private int moveSpeed;
    private int x,y;

    /**
     * Initialises the x and y coordinate accordin gto the passed argument value.
     *
     * @param x the x coordinate of the ship
     * @param y the x coordinate of the ship
     */
    public Ship(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Accesses the ship's x coordinate.
     *
     * @return the x position
     */
    public int getX() { return x; }

    /**
     * Sets the ship's x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Accesses the ship's y coordinate.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the ship's y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Accesses the ship's movement speed.
     *
     * @return the movement speed
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Sets the ship's movement speed.
     */
    public void setMoveSpeed(int moveSpeed) { this.moveSpeed = moveSpeed; }
}

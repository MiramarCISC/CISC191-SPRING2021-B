package edu.sdccd.cisc191.b.client;

/**
 * Ship is a super class which is define the basic attributes of player ship and enemy ships.
 */
public abstract class Ship {
    private boolean alive;
    int x, y;

    public Ship(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Accesses ship's survival status.
     *
     * @return boolean
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets ship's survival status.
     *
     * @param alive boolean
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Accesses the ship's x coordinate.
     *
     * @return the X position
     */
    public int getX() {
        return x;
    }
    /**
     * Sets the ship's X coordinate.
     *
     * @return the X position
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Accesses the ship's Y coordinate.
     *
     * @return the Y position
     */
    public int getY() {
        return y;
    }
    /**
     * Sets the ship's Y coordinate.
     *
     * @return the Y position
     */
    public void setY(int y) {
        this.y = y;
    }
}

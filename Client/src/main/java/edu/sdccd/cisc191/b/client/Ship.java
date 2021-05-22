package edu.sdccd.cisc191.b.client;

public abstract class Ship {
    private int maxHP;
    private int currentHP;
    private int atk;
    private boolean alive;
    int x, y;
    boolean moveLeft, moveUp, moveRight, moveDown;
    int moveSpeed;

    public Ship(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Ship(int x, int y, int maxHP, int atk) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.atk = atk;
        this.alive = true;
        this.x = x;
        this.y = y;

    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public abstract Bullet shoot();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

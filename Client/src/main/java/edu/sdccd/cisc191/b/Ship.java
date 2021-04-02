package edu.sdccd.cisc191.b;

import java.awt.*;

public abstract class Ship {
    private int maxHP;
    private int currentHP;
    private int atk;
    private boolean alive;
    private Point position;

    public Ship(int maxHP, int atk, Point position) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.atk = atk;
        this.alive = true;
        this.position = position;
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract void shoot();
}

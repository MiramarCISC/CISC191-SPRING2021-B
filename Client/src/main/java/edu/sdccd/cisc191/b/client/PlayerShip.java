package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class PlayerShip extends Ship{
    private int moveSpeed;
    private int lives;
    private HealthBar hpView;
    private int maxHp, currentHp;
    int x, y;
    boolean moveLeft, moveUp, moveRight, moveDown;

    public PlayerShip(int x, int y, int maxHP, int atk){
        super(x,y, maxHP,atk);
        lives = 3;
        moveSpeed = 10;
        this.maxHp = maxHP;
        currentHp = maxHP;
        hpView = new HealthBar(maxHP);
        hpView = new HealthBar(currentHp);

    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void move(){

    }

    private void implementPowerUp(PowerUp power){

    }

    private void endPowerUp(){

    }

    public void addLives(int add){

    }

    public void detractLives(int detract){

    }

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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    @Override
    public void shoot() {

    }
}

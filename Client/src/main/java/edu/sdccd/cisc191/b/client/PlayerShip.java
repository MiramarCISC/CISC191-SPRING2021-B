package edu.sdccd.cisc191.b.client;

import java.awt.*;

public class PlayerShip extends Ship{
    private int moveSpeed;
    private int lives;
    private HealthBar hpView;

    public PlayerShip(int maxHP, int atk, Point position){
        super(maxHP,atk,position);
        lives = 3;
        moveSpeed = 3;
        hpView = new HealthBar(maxHP);
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

    @Override
    public void shoot() {

    }
}

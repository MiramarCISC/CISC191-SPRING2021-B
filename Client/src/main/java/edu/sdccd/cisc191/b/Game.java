package edu.sdccd.cisc191.b;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Game {
    private int numOfEnemies;
    private int wave;
    private int level;
    private PlayerShip player;
    private ArrayList<EnemyShip> enemies;

    public Game(){

    }

    public void update(){

    }

    public void onClick(ActionEvent event){

    }

    public int getNumOfEnemies() {
        return numOfEnemies;
    }

    public void setNumOfEnemies(int numOfEnemies) {
        this.numOfEnemies = numOfEnemies;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void nextWave(){

    }

    public boolean checkPowerUp(){
        return true;  //fill later
    }

    public PowerUp spawnPowerUp(int type){
        PowerUp power = new PowerUp(type);
        //position, etc
        return power;
    }
}

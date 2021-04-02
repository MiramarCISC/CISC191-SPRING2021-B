package edu.sdccd.cisc191.b;

import java.awt.*;

public class Bar {
    private int maxVal;
    private int currentVal;
    private boolean visible;
    private Color color;

    public Bar(int maxVal, boolean visible, Color color) {
        this.maxVal = maxVal;
        this.currentVal = maxVal;
        this.visible = visible;
        this.color = color;
    }

    public void increaseMax(int max){

    }

    public void decreaseMax(int max){

    }

    public void increaseCurrent(int curr){

    }

    public void decreaseCurrent(int curr){

    }

    public int getMaxVal() {
        return maxVal;
    }

    public int getCurrentVal() {
        return currentVal;
    }

    public boolean isVisible() {
        return visible;
    }

    public Color getColor() {
        return color;
    }
}

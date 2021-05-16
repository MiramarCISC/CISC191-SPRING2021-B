package edu.sdccd.cisc191.b.client;

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
        maxVal += max;
    }

    public void decreaseMax(int max){
        maxVal -= max;
    }

    public void increaseCurrent(int curr){
        currentVal += curr;
    }

    public void decreaseCurrent(int curr){
        currentVal -= curr;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int num) { maxVal = num; }

    public int getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(int num) { currentVal = num; }

    public boolean isVisible() {
        return visible;
    }

    public Color getColor() {
        return color;
    }
}

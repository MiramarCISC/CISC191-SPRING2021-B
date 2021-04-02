package edu.sdccd.cisc191.client;

public class PowerUp {
    private int type;
    private String typeName;
    private int cooldown;
    private CooldownBar cooldownDisplay;

    public PowerUp(int type){
        if (type == 1){
            typeName = "Regeneration";
            cooldown = 10;
            cooldownDisplay = new CooldownBar(cooldown);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}

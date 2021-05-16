package edu.sdccd.cisc191.b.client;

public class PowerUp {
    private int type;
    private String typeName;
    private int cooldown;
    private CooldownBar cooldownDisplay;

    public PowerUp(int type){
        this.type = type;
        switch(type){
            case 1:
                typeName = "Regeneration";
                cooldown = 10;
                break;
            case 2:
                typeName = "Immunity";
                cooldown = 5;
                break;
            default:
                typeName = "Unknown";
                cooldown = 0;
                break;
        }

        cooldownDisplay = new CooldownBar(cooldown);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() { return typeName; }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}

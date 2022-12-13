package sandbox;

public class Entity {

    //vars ---------------------------------------------

    String type, name;
    float power, life;
    boolean isPlayer;

    //constractors -------------------------------------

    Entity(String type,String name, float power,float life, boolean isPlayer) {
        this.type = type;
        this.name = name;
        this.power = power;
        this.life = life;
        this.isPlayer = isPlayer;
    }

    //funcions ------------------------------------------

    //get
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public float getPower() {
        return power;
    }
    public float getLife() {
        return life;
    }
    public boolean getIsPlayer() {
        return isPlayer;
    }

    //set
    public void setPower(Float power){
        this.power = power;
    }
    public void setLife(Float life){
        this.life = life;
    }
}


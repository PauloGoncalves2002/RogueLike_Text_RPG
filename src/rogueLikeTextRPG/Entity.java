package rogueLikeTextRPG;

public class Entity {

    //vars ---------------------------------------------

    String type, name;
    int power, life,x,y;
    boolean isPlayer;

    //constractors -------------------------------------
     /**
     * Construtor de criação de instâncias de Automovel
     * @param type type of entity (string) (player,enemy,boss,)
     * @param name name of entity (string)
     * @param power power of entity (int)
     * @param life life of entity (int)
     * @param x x cordenate (int)
     * @param y y cordenate (int)
     */

    Entity(String type,String name, int power,int life, int x, int y) {
        this.type = type;
        this.name = name;
        this.power = power;
        this.life = life;
        this.x = x;
        this.y = y;
    }

    //funcions ------------------------------------------

    //get
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public int getPower() {
        return power;
    }
    public int getLife() {
        return life;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //set
    public void setName(String name){
        this.name = name;
    }
    public void setPower(int power){
        this.power = power;
    }
    public void setLife(int life){
        this.life = life;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}


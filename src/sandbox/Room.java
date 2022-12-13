package sandbox;

public class Room {
    
    // Variables to store the coordinates of the room
    private int x,y;
    private boolean visited, checked;
    private Entity //enemys, boss, potion, chest, player

    // Constructor to initialize the coordinates of the room
    public Room(int x, y boolean visited, checked) {
        this.x = x;
        this.y = y;
    }

    // Getter methods to return the coordinates of the room
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setter methods to set the coordinates of the room
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

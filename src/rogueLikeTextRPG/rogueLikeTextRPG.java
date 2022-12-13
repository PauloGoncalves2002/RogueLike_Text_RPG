package rogueLikeTextRPG;

import java.util.Scanner;
import java.util.Random;

/**
 * @author NatsuNight
 */
public class rogueLikeTextRPG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        
        Scanner keyboard = new Scanner(System.in);

        //generate seed --------------------------------------------------------------------
        Random random;
        int seed;

        System.out.println("Enter a seed number or 0 for a random seed:");
        seed = keyboard.nextInt();

        if (seed == 0) {
            seed = new Random().nextInt(1024);
            random = new Random(seed);
            System.out.println("Seed set to a random value: " + seed);
        } else {
            random = new Random(seed);
            System.out.println("Seed set to: " + seed);
        }

        //generate rooms ----------------------------------------------------------------------------
        
        // Create a 2D array with 7 rows and 7 columns
        int[][] rooms = new int[7][7];
            
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                // Generate a random number between 0 and 100
                int randomNum = random.nextInt(100);
                
                // If the random number is less than or equal to 25, set the space to 1 (a room)
                if (randomNum <= 25) {
                    rooms[i][j] = 1;
                }
            }
        }

        // Set the value of the middle element (the room) to 1
        rooms[3][3] = 1;


        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                System.out.print(rooms[i][j] + " ");
            }
            System.out.println();
        }
        


    }
}
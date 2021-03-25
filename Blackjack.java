import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean repeat = true;
        Player p = new Player();

        String name;
        while (repeat) {
            System.out.print("Enter your name: ");
            name = in.nextLine();
            /*if (name exists) {
                System.out.println("This name already exists. ");
                System.out.print("Do you want to DELETE the old record or enter a NEW name?(D/N): ");
                if (entered N) {
                    continue;
                } else {  //meaning they entered D
                    Delete the player with the used name
                }
            }*/
            p = new Player(name);
            repeat = false;
        }

        repeat = true;
        while (p.getMoney() > 0 && repeat) {
            //playGame();
        }

        //Add this player's stats to the file.
        //Print out a simplified leaderboard in the console with the player's ranking.

        in.close();
    }
}
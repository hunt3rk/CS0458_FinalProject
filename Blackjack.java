import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Player player = new Player();
        String name;
        boolean repeat = true;

        //Retrieve previous records from leaderboard.dat (assumes the file exists and contains an Alist<Player> already)
        AList<Player> records = new AList<>();
        File leaderboard = new File("leaderboard.dat");

        if (leaderboard.exists()) {
            try {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(leaderboard));
                records = (AList<Player>)fin.readObject();
                fin.close();
            } catch (IOException ex) {
                System.err.println("File read error!");
                System.exit(1);
            } catch (ClassNotFoundException ex) {
                System.err.println("File does not contain valid score information.");
            }
        }

        while (repeat) {
            boolean found = false;
            System.out.print("Enter your name: ");
            name = in.nextLine();

            //check the records to see if a player with the inputted name already exists.
            for (Player p : records) {
                if (name.equals(p.getName())) {
                    found = true;
                    break;
                }
            }

            if (found) {
                System.out.println("This name already exists. ");
                System.out.print("Do you want to DELETE the old record or enter a NEW name?(D/N): "); //no error checking on this currently
                if (in.nextLine().equals("N")) {
                    continue;
                } else {  //meaning they entered D, remove the player with the existing name(Won't be saved unless you finish the game).
                    int i = 0;
                    for (Player p : records) {
                        if (name.equals(p.getName())) {
                            records.remove(i);
                        }
                        i++;
                    }
                }
            }
            player = new Player(name);
            repeat = false;
        }





        
        //GAME MECHANICS




        

        //Update the leaderboard file with the new record.
        records.add(player);
        try {
            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(leaderboard));
                fout.writeObject(records);
                fout.close();
        } catch (IOException ex) {
            System.err.println("File write error!");
                System.exit(0);
        }

        //Print out the leaderboard
        printLeaderboard(leaderboard);

        in.close();
    }

    public static void printLeaderboard(File leaderboard) {
        AList<Player> records = new AList<>();

        //get the records from the leaderboard
        if (leaderboard.exists()) {
            try {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(leaderboard));
                records = (AList<Player>)fin.readObject();
                fin.close();
            } catch (IOException ex) {
                System.err.println("File read error!");
                System.exit(1);
            } catch (ClassNotFoundException ex) {
                System.err.println("File does not contain valid loan information.");
            }
        }
        System.out.println("------------------------------------------------------------------------------------");
        for (Player p : records) {
            System.out.println(p.getName() + ": $" + p.getMoney());
        }
        System.out.println("------------------------------------------------------------------------------------");
    }
}
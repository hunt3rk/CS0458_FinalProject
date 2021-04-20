import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * clears the AList of players in leaderboard.dat, effectively refreshing the leaderboard.
 */

public class InitializeLeaderboard {
    public static void main(String[] args) {
        AList<Player> records = new AList<>();

        File leaderboard = new File("leaderboard.dat");
        try {
            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(leaderboard));
                fout.writeObject(records);
                fout.close();
        } catch (IOException ex) {
            System.err.println("File write error!");
                System.exit(0);
        }
    }
}

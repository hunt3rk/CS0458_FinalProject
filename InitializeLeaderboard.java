import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InitializeLeaderboard {
    public static void main(String[] args) {
        AList<Player> records = new AList<>();

        Player p = new Player();
        Player p2 = new Player("Hunter");
        p2.lose(35);
        
        records.add(p);
        records.add(p2);

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

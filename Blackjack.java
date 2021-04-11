import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Blackjack {

    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
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
                System.err.println("File does not contain valid information.");
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

        playGame(player);

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
                System.exit(2);
            } catch (ClassNotFoundException ex) {
                System.err.println("File does not contain valid information.");
            }
        }
        System.out.println("------------------------------------------------------------------------------------");
        for (Player p : records) {
            System.out.println(p.getName() + ": $" + p.getMoney());
        }
        System.out.println("------------------------------------------------------------------------------------");
    }

    public static void playGame(Player p) {
        boolean playing = true;
        boolean yourTurn = true;
        int bet;

        Deck deck = new Deck(6,true); //creates a deck of 6 decks and shuffles it (standard for casino blackjack)
        int playerTotal;
        int dealerTotal;
        ArrayDeque<Card> playerCards;
        ArrayDeque<Card> dealerCards;

        while (playing) {

            playerTotal = 0;
            dealerTotal = 0;
            playerCards = new ArrayDeque<>();
            dealerCards = new ArrayDeque<>();

            System.out.print("Do you want to PLAY or QUIT and save your score?(P/Q): ");
            if (in.nextLine().equals("Q")) {
                playing = false;
                break;
            }
            do {
                System.out.print("Enter the amount you want to bet: (You currently have $" + p.getMoney() + "): ");
                bet = in.nextInt();  //no input validation, could break here
                if (bet > p.getMoney() || bet < 0)
                    System.out.println("Invalid amount.");
            } while (bet > p.getMoney() || bet < 0);
            
            //draw 2 cards for the dealer and for the player
            System.out.println("The dealer has drawn a " + deck.peek() + " and one other face-down card.");
            dealerCards.add(deck.drawCard());
            dealerCards.add(deck.drawCard());
            System.out.print("You have drawn a " + deck.peek());
            playerCards.add(deck.drawCard());
            System.out.println(" and a " + deck.peek());
            playerCards.add(deck.drawCard());

            //TODO: get player and dealer totals using Card.getRank(), if either are 21 its blackjack, yourTurn = false so game ends immediately.

            //This loop represents your turn to draw cards.
            while (yourTurn) {
                System.out.print("Do you want to HIT, or STAND?(H/S): "); //hit and stand are the 2 primary actions needed, can also add double, split..
                String choice = in.next().toUpperCase();
                if (choice.equals("H")) {
                    //TODO: replicate a hit
                } else if (choice.equals("S")) {
                    //TODO: replicate a stand
                } else {
                    System.out.println("Invalid input.");
                }
                //TODO: update playerTotal after each iteration of this loop.
                //TODO: automatically end yourTurn if total is > 21.
            }

            //TODO: loop that will play as the dealer (keep hitting until total is 17 or over 21)

            if (playerTotal < dealerTotal || playerTotal > 21) {
                p.lose(bet);
            } else {
                p.win(bet);
            }
        }
    }
}
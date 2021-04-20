import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * class with main
 * handles game mechanics and file reading/writing
 * 
 * the file stores an ArrayList of Player objects.  Each player has a name and ending money amount.
 */

public class Blackjack {

    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        Player player = new Player();
        String name;
        boolean repeat = true;

        //Retrieve previous records from leaderboard.dat
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
                System.out.print("Do you want to DELETE the old record or enter a NEW name?(D/N): ");
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

        //execute the main game mechanics
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

    //Prints out the leaderboard
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

    //Executes the main game mechanics
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

            yourTurn = true;
            playerTotal = 0;
            dealerTotal = 0;
            playerCards = new ArrayDeque<>();
            dealerCards = new ArrayDeque<>();

            if (p.getMoney() == 0) {
                playing = false;
                break;
            }

            System.out.print("Do you want to PLAY or QUIT and save your score?(P/Q): ");
            if (in.nextLine().toUpperCase().equals("Q")) {
                playing = false;
                break;
            }
            do {
                System.out.print("Enter the amount you want to bet: (You currently have $" + p.getMoney() + "): ");
                bet = in.nextInt();  //no input validation, could break here
                in.nextLine();
                if (bet > p.getMoney() || bet < 0)
                    System.out.println("Invalid amount.");
            } while (bet > p.getMoney() || bet < 0);
            System.out.println("");
            
            //draw 2 cards for the dealer and for the player
            System.out.println("The dealer has drawn a " + deck.peek() + " and one other face-down card.");
            dealerTotal += deck.peek().getValue();
            dealerCards.add(deck.drawCard());
            dealerTotal += deck.peek().getValue();
            dealerCards.add(deck.drawCard());
            System.out.print("You have drawn a " + deck.peek());
            playerTotal += deck.peek().getValue();
            playerCards.add(deck.drawCard());
            System.out.println(" and a " + deck.peek());
            playerTotal += deck.peek().getValue();
            playerCards.add(deck.drawCard());

            System.out.println("");
			System.out.println("You have a total of " + playerTotal);
            System.out.println("The dealer has a total of " + (dealerTotal - dealerCards.peekLast().getValue()));
			
			if(playerTotal >= 21) {
				yourTurn = false;
			}
			

            //This loop represents your turn to draw cards.
            while (yourTurn) {
                System.out.println("");
                System.out.print("Do you want to HIT, or STAND?(H/S): ");
                String choice = in.nextLine().toUpperCase();
                if (choice.equals("H")) {
                    //hit
					System.out.print("You have drawn a " + deck.peek());
                    playerTotal += deck.peek().getValue();
					System.out.println();
					playerCards.add(deck.drawCard());
					
					System.out.println("You have a total of " + playerTotal);
					if(playerTotal >= 21)
						yourTurn = false;
					
                } else if (choice.equals("S")) {
                    yourTurn = false;
                } else {
                    System.out.println("Invalid input.");
                }

            }

            System.out.println("");
			System.out.println("The dealer's face down card is a " + dealerCards.peekLast());
            //loop that will play as the dealer (keep hitting until total is 17 or over 21)
			
			while(dealerTotal < 17 && playerTotal <= 21) {
				System.out.println("The dealer has drawn a " + deck.peek());
                dealerTotal += deck.peek().getValue();
				dealerCards.add(deck.drawCard());
			}

            System.out.println("");
			System.out.println("Dealer has a total of " + dealerTotal);
			
            System.out.println("");
			if ((playerTotal < dealerTotal && dealerTotal <= 21) || playerTotal > 21) {
                System.out.println("You lost! -$" + bet);
                p.lose(bet);
            } else if (playerTotal == dealerTotal)  {
                System.out.println("Tie! +$0");
            } else {
                System.out.println("You won! +$" + bet);
                p.win(bet);
            }
        }
    }
}
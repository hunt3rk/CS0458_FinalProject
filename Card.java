public class Card {

    /**
     * class that imitates a Card
     * stores the card's rank, suit, and value
     */
    private int rank;
    private int suit;
    private int value;
        //Each rank is 2 lower than expected. ex. "Two" = RANKS[0].
    final public String[] RANKS = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                            "Ten", "Jack", "Queen", "King", "Ace"}; 
    final public String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    
    public Card() {
        this(0,0);
    }

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        decideValue();
    }

    public void setRank(int rank) {
        this.rank = rank;
        decideValue();
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return RANKS[this.rank] + " of " + SUITS[this.suit];
    }

    public boolean sameSuit(Card card) {
        if (this.suit == card.suit)
            return true;
        else
            return false;
    }

    public int compareTo(Card card) {
        if (this.rank > card.rank)
            return 1;
        else if (this.rank < card.rank)
            return -1;
        else
            return 0;
    }

    public boolean equals(Card card) {
        if (this.sameSuit(card) && this.compareTo(card) == 0)
            return true;
        else
            return false;
    }

    private void decideValue() {
        if (rank <= 8) {
            value = rank+2;
        } else if (rank == 9 || rank == 10 || rank == 11) {
            value = 10;
        } else {
            value = 11;
        }
    }
}
public class Card {
    private int rank;
    private int suit;
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
    }

    public void setRank(int rank) {
        this.rank = rank;
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
}
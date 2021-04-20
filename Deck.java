public class Deck {

    /**
     * class that imitates a deck
     * it holds an ArrayDeque of cards and contains methods to modify that ArrayDeque
     */

    private int cardsRemaining;
    private ArrayDeque<Card> cards;
    public final int DEFAULT_SIZE = 52;

    //Creates an unshuffled deck of cards
    public Deck() {
        cardsRemaining = 52;
        createDeck();
    }

    //If boolean shuffled is true, create a shuffled deck, otherwise create a regular deck
    public Deck(boolean shuffled) {
        if (shuffled) {
            cardsRemaining = 52;
            createDeck();
            shuffle(1);
        } else {
            cardsRemaining = 52;
            createDeck();
        }
    }

    //This constructor allows multiple decks to be merged into one Deck object
    //In typical casino blackjack, a 'Deck' consists of 6 actuals decks, or 312 cards.
    public Deck(int n) {
        //if the user enters 0, negative number, or a number over 100, just create a regular unshuffled deck.
        if (n < 1 || n > 100) {
            cardsRemaining = 52;
            createDeck();
        } else {
            cardsRemaining = 52*n;
            createDeck(n);
        }
    }
    
    //This constructor combines the boolean and int arguements
    public Deck(int n, boolean shuffled) {
        if (shuffled) {
            if (n < 1 || n > 100) {
                cardsRemaining = 52;
                createDeck();
                shuffle(1);
            } else {
                cardsRemaining = 52*n;
                createDeck(n);
                shuffle(n);
            }
        } else {
            if (n < 1 || n > 100) {
                cardsRemaining = 52;
                createDeck();
            } else {
                cardsRemaining = 52*n;
                createDeck(n);
            }
        }
    }

    //Returns the number of cards remaining in the deck
    public int getCardsRemaining() {
        return cardsRemaining;
    }

    //Creates the ArrayQueue of 52 Card objects
    public void createDeck() {
        cards = new ArrayDeque<Card>(52);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards.add(new Card(i,j));
            }
        }
    }

    //Creates a deck consisting of n 'real' decks
    public void createDeck(int n) {
        cards = new ArrayDeque<Card>(52*n);
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 13; j++) {
                    cards.add(new Card(i,j));
                }
            }
        }
    }


    //Creates the ArrayDeque of Card based on a Card[]
    public void createDeck(Card[] newCards) {
        this.cards = new ArrayDeque<Card>(newCards.length);
        int count = 0;
        for (int i = 0; i < newCards.length; i++) {
            this.cards.add(newCards[count]);
            count++;
        }
    }

    //Returns a card and removes it from the deck.
    public Card drawCard() {
        Card c = cards.peek();
        cards.remove();
        cardsRemaining--;
        return c;
    }

    public Card peek() {
        return cards.peek();
    }

    //shuffles the deck of cards
    //n will be 1 for a regular 'real' deck, otherwise n is the number of 'real' decks within a Deck
    public void shuffle(int n) {
        Card[] arr = cards.toArray(new Card[cardsRemaining]);
        int r;
        int r2;
        Card temp;
        //This loop switches 2 cards locations within the deck. 100*n switches ensures a truly random feeling shuffle.
        for (int i = 0; i < 100*n; i++) {
            r = (int)(Math.random()*cardsRemaining);
            r2 = (int)(Math.random()*cardsRemaining);
            temp = arr[r];
            arr[r] = arr[r2];
            arr[r2] = temp;
        }
        createDeck(arr);
    }

    //Only used for testing purposes, prints out each card in the deck and removes them.
    public void print() {
        while (cardsRemaining > 0) {
            System.out.println(drawCard());
        }
    }
}
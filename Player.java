import java.io.Serializable;

public class Player implements Serializable {
    private int money;
    private String name;
    public static final int DEFAULT_MONEY = 100;
    public static final String DEFAULT_NAME = "Player";

    public Player() {
        this(DEFAULT_NAME);
    }

    public Player(String name) {
        this.name = name;
        money = DEFAULT_MONEY;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void win(int money) {
        this.money += money;
    }

    public void lose(int money) {
        this.money -= money;
    }
}

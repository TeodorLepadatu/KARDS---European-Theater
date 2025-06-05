package classes;

abstract public class Card {
    protected final String name;
    protected int hp;

    public Card(){
        this.name = "Unknown";
        this.hp = 1;
    }

    public Card(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    abstract public void action(Card target);
}

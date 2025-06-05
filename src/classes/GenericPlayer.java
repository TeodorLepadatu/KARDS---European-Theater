package classes;

import java.util.HashMap;

abstract public sealed class GenericPlayer permits HumanPlayer, AIPlayer {
    public String name;
    protected HashMap<String, Card> hand;
    protected int movesPerTurn;
    protected int difficulty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public GenericPlayer(String name, HashMap<String, Card> hand, int movesPerTurn, int difficulty) {
        this.name = name;
        this.hand = hand;
        this.movesPerTurn = movesPerTurn;
        this.difficulty = difficulty;
    }

    protected void playCard(String cardName, Card target, GenericPlayer opponent) {
        if (!hand.containsKey(cardName)) {
            System.out.println("Invalid card name.");
            return;
        }
        Card card = hand.get(cardName);
        card.action(target);
        if (target.hp <= 0) {
            opponent.loseCard(target, opponent);
        }
    }

    public void loseCard(Card card, GenericPlayer player) {

        player.hand.remove(card.name);
        CardDAOImpl cardDAO = CardDAOImpl.getInstance();
        cardDAO.delete(card.name);
    }

    public boolean hasLost() {
        return hand.isEmpty();
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}

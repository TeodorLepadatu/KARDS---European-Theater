package classes;

import java.util.ArrayList;

public class NationConfig {
    private final int movesPerTurn;
    //need to find something better for heuristic
    public double calculateAveragePower(ArrayList<Card> deck) {
        if (deck == null || deck.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (Card card : deck) {
            total += card.hp;
            if (card instanceof AttackingCard) {
                total += ((AttackingCard) card).getAttackPower();
            } else if (card instanceof HealingCard) {
                total += ((HealingCard) card).getHealPower();
            }
        }
        return (double) total / deck.size();
    }

    public NationConfig(ArrayList<Card> deck) {
        double avg = calculateAveragePower(deck);
        if (avg < 100) {
            movesPerTurn = 2;
        } else {
            movesPerTurn = 1;
        }
    }

    public int getMovesPerTurn() {
        return movesPerTurn;
    }
}

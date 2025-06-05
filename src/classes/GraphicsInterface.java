package classes;

import java.util.ArrayList;
import java.util.Collection;

abstract public class GraphicsInterface {

    public GraphicsInterface() {}

    public static String[] getCardGraphicLines(Card card) {
        String[] lines = new String[5];
        lines[0] = "+---------------------------+";
        lines[1] = String.format("| Name: %-19s|", card.name);
        lines[2] = String.format("| HP: %-21d|", card.hp);

        if (card instanceof AttackingCard attackCard) {
            lines[3] = String.format("| Attack: %-17d|", attackCard.getAttackPower());
        } else if (card instanceof HealingCard healingCard) {
            lines[3] = String.format("| Heal: %-19d|", healingCard.getHealPower());
        } else {
            lines[3] = "| (No action stat)       |";
        }

        lines[4] = "+---------------------------+";
        return lines;
    }

    private static void printHorizontalCards(Collection<Card> cards) {
        ArrayList<String[]> allLines = new ArrayList<>();
        int maxLines = 5;  // Each card has 5 lines

        for (Card card : cards) {
            allLines.add(getCardGraphicLines(card));
        }

        for (int line = 0; line < maxLines; line++) {
            for (String[] cardLines : allLines) {
                System.out.print(cardLines[line] + "  ");
            }
            System.out.println();
        }
    }

    public static void displayHands(GenericPlayer player, GenericPlayer opponent) {
        System.out.println(player.name + "'s Hand:");
        if (player.hand.isEmpty()) {
            System.out.println("  (No cards)");
        } else {
            printHorizontalCards(player.hand.values());
        }

        System.out.println("\n-------------------- VS --------------------\n");

        System.out.println(opponent.name + "'s Hand:");
        if (opponent.hand.isEmpty()) {
            System.out.println("  (No cards)");
        } else {
            printHorizontalCards(opponent.hand.values());
        }
    }
}

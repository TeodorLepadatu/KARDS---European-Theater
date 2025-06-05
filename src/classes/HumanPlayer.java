package classes;

import java.util.HashMap;

public non-sealed class HumanPlayer extends GenericPlayer {
    private static HumanPlayer instance1 = null;
    private static HumanPlayer instance2 = null;

    private HumanPlayer(String name, HashMap<String, Card> hand, int movesPerTurn) {
        super(name, hand, movesPerTurn, 0);
    }

    public static HumanPlayer getInstance(String name, HashMap<String, Card> hand, int movesPerTurn) {
        if (instance1 == null) {
            instance1 = new HumanPlayer(name, hand, movesPerTurn);
            return instance1;
        } else if (instance2 == null) {
            instance2 = new HumanPlayer(name, hand, movesPerTurn);
            return instance2;
        }
        return instance1;
    }
}

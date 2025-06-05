package classes;
import java.util.*;

public non-sealed class AIPlayer extends GenericPlayer {
    private static AIPlayer instance1 = null;
    private static AIPlayer instance2 = null;
    protected int difficulty;

    private AIPlayer(String name, HashMap<String, Card> hand, int movesPerTurn, int difficulty) {
        super(name, hand, movesPerTurn, difficulty);
        this.difficulty = difficulty;
    }

    public static AIPlayer getInstance(String name, HashMap<String, Card> hand, int movesPerTurn, int difficulty) {
        if (instance1 == null) {
            instance1 = new AIPlayer(name, hand, movesPerTurn, difficulty);
            return instance1;
        } else if (instance2 == null) {
            instance2 = new AIPlayer(name, hand, movesPerTurn, difficulty);
            return instance2;
        }
        return instance1;
    }

    public ArrayList<ArrayList<Integer>> chooseWhatToPlay(GenericPlayer opponent, int difficulty) {
        return switch (difficulty) {
            case 1 -> chooseRandomCard(opponent);
            case 2 -> chooseWithAlphaBeta(opponent);
            default -> {
                System.out.println("Invalid difficulty level. Defaulting to easy.");
                yield chooseRandomCard(opponent);
            }
        };
    }

    // EASY: purely random moves
    private ArrayList<ArrayList<Integer>> chooseRandomCard(GenericPlayer opponent) {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        List<String> myKeys = new ArrayList<>(hand.keySet());
        List<String> oppKeys = new ArrayList<>(opponent.hand.keySet());
        Random rnd = new Random();

        for (int m = 0; m < movesPerTurn && !myKeys.isEmpty(); m++) {
            int ci = rnd.nextInt(myKeys.size());
            String cKey = myKeys.get(ci);
            Card card = hand.get(cKey);
            int ti;
            if (card instanceof AttackingCard && !oppKeys.isEmpty()) {
                ti = rnd.nextInt(oppKeys.size());
            } else if (card instanceof HealingCard) {
                ti = rnd.nextInt(myKeys.size());
            } else {
                continue;
            }
            moves.add(new ArrayList<>(Arrays.asList(ci, ti)));
        }
        return moves;
    }

    // MEDIUM: minimax with alpha-beta pruning
    private ArrayList<ArrayList<Integer>> chooseWithAlphaBeta(GenericPlayer opponent) {
        GameState root = new GameState(this, opponent);
        MinimaxResult result = minimax(root, movesPerTurn,
                Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return result.moves;
    }

    private MinimaxResult minimax(GameState state, int depth,
                                  int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || state.isTerminal()) {
            return new MinimaxResult(state.evaluate(), new ArrayList<>());
        }

        MinimaxResult best = maximizingPlayer
                ? new MinimaxResult(Integer.MIN_VALUE, null)
                : new MinimaxResult(Integer.MAX_VALUE, null);

        for (Move move : state.listMoves(maximizingPlayer)) {
            GameState nextState = state.copy();
            nextState.apply(move);
            nextState.switchPlayer();
            MinimaxResult eval = minimax(nextState, depth - 1, alpha, beta, !maximizingPlayer);
            eval.moves.addFirst(new ArrayList<>(move.toList()));

            if (maximizingPlayer) {
                if (eval.score > best.score) best = eval;
                alpha = Math.max(alpha, eval.score);
            } else {
                if (eval.score < best.score) best = eval;
                beta = Math.min(beta, eval.score);
            }
            if (beta <= alpha) break;
        }
        return best;
    }

    // --- Helper inner classes ---
    private static class Move {
        int ci, ti;
        Move(int ci, int ti) { this.ci = ci; this.ti = ti; }
        List<Integer> toList() { return Arrays.asList(ci, ti); }
    }

    private static class MinimaxResult {
        int score;
        ArrayList<ArrayList<Integer>> moves;
        MinimaxResult(int score, ArrayList<ArrayList<Integer>> moves) {
            this.score = score;
            this.moves = moves == null ? new ArrayList<>() : moves;
        }
    }

    private static class GameState {
        List<Card> myCards, oppCards;
        int movesPerTurn;
        boolean playerTurn; // true = AI's turn, false = opponent's simulated

        GameState(GenericPlayer ai, GenericPlayer opp) {
            this.myCards = cloneCards(ai.hand);
            this.oppCards = cloneCards(opp.hand);
            this.movesPerTurn = ai.movesPerTurn;
            this.playerTurn = true;
        }
        private static List<Card> cloneCards(Map<String, Card> hand) {
            List<Card> list = new ArrayList<>();
            for (Card c : hand.values()) {
                if (c instanceof AttackingCard ac)
                    list.add(new AttackingCard(c.name, c.hp, ac.getAttackPower()));
                else if (c instanceof HealingCard hc)
                    list.add(new HealingCard(c.name, c.hp, hc.getHealPower()));
            }
            return list;
        }
        GameState copy() {
            GameState s = new GameState();
            s.myCards = deepCopy(myCards);
            s.oppCards = deepCopy(oppCards);
            s.movesPerTurn = movesPerTurn;
            s.playerTurn = playerTurn;
            return s;
        }
        private GameState() {}

        boolean isTerminal() {
            return myCards.isEmpty() || oppCards.isEmpty();
        }

        int evaluate() {
            // Heuristic: weight attack potential higher than raw HP to discourage pointless healing
            int myAttack = sumAttack(myCards);
            int oppAttack = sumAttack(oppCards);
            int myHP = sumHP(myCards);
            int oppHP = sumHP(oppCards);
            return 2 * (myAttack - oppAttack) + (myHP - oppHP);
        }

        private int sumAttack(List<Card> cards) {
            int sum = 0;
            for (Card c : cards) {
                if (c instanceof AttackingCard ac) sum += ac.getAttackPower();
            }
            return sum;
        }

        private int sumHP(List<Card> cards) {
            int sum = 0;
            for (Card c : cards) sum += c.hp;
            return sum;
        }

        List<Move> listMoves(boolean aiTurn) {
            List<Card> src = aiTurn ? myCards : oppCards;
            List<Card> tgt = aiTurn ? oppCards : myCards;
            List<Move> moves = new ArrayList<>();
            for (int i = 0; i < src.size(); i++) {
                Card c = src.get(i);
                if (c instanceof AttackingCard) {
                    for (int j = 0; j < tgt.size(); j++) moves.add(new Move(i, j));
                } else if (c instanceof HealingCard) {
                    for (int j = 0; j < src.size(); j++) moves.add(new Move(i, j));
                }
            }
            return moves;
        }

        void apply(Move m) {
            List<Card> src = playerTurn ? myCards : oppCards;
            List<Card> tgt = playerTurn ? oppCards : myCards;
            Card c = src.get(m.ci);
            Card t = (c instanceof AttackingCard) ? tgt.get(m.ti) : src.get(m.ti);
            c.action(t);
            if (t.hp <= 0) tgt.remove(t);
        }

        void switchPlayer() {
            playerTurn = !playerTurn;
        }

        private static List<Card> deepCopy(List<Card> orig) {
            List<Card> copy = new ArrayList<>();
            for (Card c : orig) {
                if (c instanceof AttackingCard ac)
                    copy.add(new AttackingCard(c.name, c.hp, ac.getAttackPower()));
                else if (c instanceof HealingCard hc)
                    copy.add(new HealingCard(c.name, c.hp, hc.getHealPower()));
            }
            return copy;
        }
    }
}

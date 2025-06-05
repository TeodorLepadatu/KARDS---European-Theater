package classes;

import java.util.ArrayList;

public class Scenario {
    private String name;
    private ArrayList<Card> germanyDeck;
    private ArrayList<Card> sovietDeck;
    private ArrayList<Card> italyDeck;
    private ArrayList<Card> ukDeck;
    private ArrayList<Card> franceDeck;
    private CardDAOImpl cardDAO = CardDAOImpl.getInstance();

    private void CreateGermany() {
        germanyDeck = new ArrayList<>();
        germanyDeck = cardDAO.readAllByCountryId(1);
    }

    private void CreateUSSR() {
        sovietDeck = new ArrayList<>();
        sovietDeck = cardDAO.readAllByCountryId(2);
    }

    private void CrateFrance() {
        franceDeck = new ArrayList<>();
        franceDeck = cardDAO.readAllByCountryId(3);
    }

    private void CreateEngland() {
        ukDeck = new ArrayList<>();
        ukDeck = cardDAO.readAllByCountryId(4);
    }

    private void CreateItaly() {
        italyDeck = new ArrayList<>();
        italyDeck = cardDAO.readAllByCountryId(5);
    }
    public Scenario() {}
    public Scenario(int index) {
        switch (index) {
            case 1:
                CreateGermany();
                CreateUSSR();
                name = "Germany vs USSR";
                break;
            case 2:
                CreateGermany();
                CrateFrance();
                name = "Germany vs France";
                break;
            case 3:
                CreateItaly();
                CrateFrance();
                name = "Italy vs France";
                break;
            case 4:
                CreateItaly();
                CreateEngland();
                name = "Italy vs England";
                break;
            case 5:
                CreateGermany();
                CreateEngland();
                name = "Germany vs England";
                break;
            default:
                CreateGermany();
                CreateUSSR();
                name = "Germany vs USSR";
                break;
        }
    }

    public ArrayList<Card> getGermanyDeck() { return germanyDeck; }
    public ArrayList<Card> getSovietDeck() { return sovietDeck; }
    public ArrayList<Card> getFranceDeck() { return franceDeck; }
    public ArrayList<Card> getUKDeck() { return ukDeck; }
    public ArrayList<Card> getItalyDeck() { return italyDeck; }
}

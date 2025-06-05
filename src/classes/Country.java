package classes;

import java.util.ArrayList;

public class Country {
    private int id;
    private String name;
    private Integer playerId;
    private ArrayList<Card> deck;
    public Country(int id, String name, Integer playerId) {
        this.id = id;
        this.name = name;
        this.playerId = playerId;
        Scenario scenario = new Scenario();
        if(name == "Germany")
            deck = scenario.getGermanyDeck();
        else if (name == "USSR")
            deck = scenario.getSovietDeck();
        else if(name == "France")
            deck = scenario.getFranceDeck();
        else if(name == "England")
            deck = scenario.getUKDeck();
        else if(name == "Italy")
            deck = scenario.getItalyDeck();
        else
            deck = new ArrayList<>();
    }
    public String getName() { return name; }
    public int getId() { return id; }
    public Integer getPlayerId() { return playerId; }
    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    //public void setPlayerId(Integer playerId) { this.playerId = playerId; }
}

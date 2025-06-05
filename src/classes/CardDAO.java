package classes;

public interface CardDAO { //data access object
    void create(Card card, int playerId);
    Card read(int id);
    void update(Card card, int playerId);
    void delete(String name);
}

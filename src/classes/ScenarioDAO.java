package classes;

public interface ScenarioDAO {
    void create(Scenario scenario);
    Scenario read(int id);
    void update(Scenario scenario);
    void delete(int id);
}

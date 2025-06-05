package classes;

import java.util.List;

public interface GameModeDAO {
    void create(GameMode gameMode);
    GameMode read(int id);
    List<GameMode> readAll();
    void update(GameMode gameMode);
    void delete(int id);
}
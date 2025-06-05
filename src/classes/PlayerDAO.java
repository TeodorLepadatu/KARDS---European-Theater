package classes;

import java.util.List;

public interface PlayerDAO {
    void create(GenericPlayer player);
    GenericPlayer read(int id);
    void update(GenericPlayer player);
    void delete(int id);
}

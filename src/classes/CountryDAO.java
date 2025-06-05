package classes;

import java.util.List;

public interface CountryDAO {
    void create(Country country);
    Country read(int id);
    List<Country> readAll();
    void update(Country country);
    void delete(int id);
}
package classes;

public class GameMode {
    private String name;
    private int id;
    public GameMode(int id, String name)
    {
        this.id = id;
        switch (id){
            case 1 -> this.name = "PvP";
            case 2 -> this.name = "PvAI";
            case 3 -> this.name = "AIvAI";
            default -> this.name = "PvP";
        }
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}

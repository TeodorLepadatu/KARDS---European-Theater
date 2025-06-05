package classes;

public class HealingCard extends Card{
    private final int healPower;

    public HealingCard(String name, int hp, int healPower) {
        super(name, hp);
        this.healPower = healPower;
    }

    public int getHealPower() {
        return healPower;
    }

    @Override
    public void action(Card target) {

        target.hp += healPower;
        CardDAOImpl.getInstance().updateHealth(target.name, target.hp);
    }
}

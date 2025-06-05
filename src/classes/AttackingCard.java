package classes;

public class AttackingCard extends Card{
    private final int attackPower;

    public AttackingCard(String name, int hp, int attackPower) {
        super(name, hp);
        this.attackPower = attackPower;
    }

    @Override
    public void action(Card target) {

        target.hp -= attackPower;
        CardDAOImpl.getInstance().updateHealth(target.name, target.hp);
    }

    public int getAttackPower() {
        return attackPower;
    }
}

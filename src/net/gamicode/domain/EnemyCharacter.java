package net.gamicode.domain;

public class EnemyCharacter extends Character{
    private String skills;
    private boolean defending;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skills) {
        super(name, HP, attack, defense);
        this.skills = skills;
    }

    @Override
    public void takeDamage(int damage) {
        if (defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
            defending = false;
        }
        super.takeDamage(damage);
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }
}

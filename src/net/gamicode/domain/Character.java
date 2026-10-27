package net.gamicode.domain;

public class Character {
    private String name;
    private int HP;
    private int maxHP;
    private int attack;
    private int defense;

    public Character() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }
    //1.判断角色是否死亡
    public boolean isAlive() {
        return HP > 0;
    }
    //2.恢复生命值
    public void heal(int amount) {
        HP += amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }
    //3.受到伤害
    public void takeDamage(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }
    //展示角色信息
    public void displayInfo() {
        System.out.println("名字: " + name);
        System.out.println("血量: " + HP + "/" + maxHP);
        System.out.println("攻击: " + attack);
        System.out.println("防御: " + defense);
    }
}

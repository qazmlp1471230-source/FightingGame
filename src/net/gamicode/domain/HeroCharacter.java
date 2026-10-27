package net.gamicode.domain;

import java.util.ArrayList;

public class HeroCharacter extends Character{
    public ArrayList<String> skills;

    public HeroCharacter() {
        skills = new ArrayList<String>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        skills = new ArrayList<String>();
    }
}

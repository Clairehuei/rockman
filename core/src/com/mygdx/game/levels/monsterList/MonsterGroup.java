package com.mygdx.game.levels.monsterList;

import com.mygdx.game.role.hero.Rhero;
import com.mygdx.game.role.monster.Rmonster;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 6193 on 2015/11/2.
 */
public abstract class MonsterGroup implements MonsterList{
    List<Rmonster> monsters = new ArrayList<Rmonster>();
    Rhero target;
    public List<Rmonster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Rmonster> monsters) {
        this.monsters = monsters;
    }

    public Rhero getTarget() {
        return target;
    }

    public void setTarget(Rhero target) {
        this.target = target;
        setHero();
    }

    public abstract void setMonster();
    public abstract void setHero();
    public abstract boolean victoryCondition();
    public abstract void cleanAllMonster();

}

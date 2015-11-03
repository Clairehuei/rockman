package com.mygdx.game.levels.monsterList;

import com.mygdx.game.role.monster.Rboss01;
import com.mygdx.game.role.monster.Rmonster;

/**怪物清單
 * Created by 6193 on 2015/11/2.
 */
public class Mplay03 extends MonsterGroup {

    Rmonster boss;//本關卡的BOSS
    Rmonster m1;//本關卡的怪物1號

    @Override
    public void init(){
        boss = new Rboss01();
        boss.position.x = 800;
        boss.position.y = 382;
        boss.velocity.x = 300;//英雄X軸初始速度


        m1 = new Rboss01();
        m1.position.x = 100;
        m1.position.y = 382;

        setMonster();
    }


    @Override
    public void setMonster() {
        monsters.add(boss);
        monsters.add(m1);
    }

    @Override
    public void setHero() {
        boss.setTarget(target);
    }

    @Override
    public boolean victoryCondition() {
        if(boss.HP<=0 && boss.beKilled){
            return true;
        }

        return false;
    }

    @Override
    public void cleanAllMonster() {
        boss.getMonster1Atlas().dispose();
        m1.getMonster1Atlas().dispose();
    }


}

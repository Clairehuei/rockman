package com.mygdx.game.role.monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.role.BaseRole;

/**
 * Created by User on 2015/11/9.
 */
public abstract class RlittleMonster extends Rmonster implements BaseRole {
    public String roleType = "littleMonster";
    public Animation animationLoseRight;//死亡(右)動畫
    public Animation animationLoseLeft;//死亡(左)動畫
    public Animation animationLoseKeepRight;//死亡持續姿勢(右)動畫
    public Animation animationLoseKeepLeft;//死亡持續姿勢(左)動畫


    //************************************************setter/getter**************************************************

    public Animation getAnimationLoseRight() {
        return animationLoseRight;
    }
    public void setAnimationLoseRight(Animation animationLoseRight) {
        this.animationLoseRight = animationLoseRight;
    }
    public Animation getAnimationLoseLeft() {
        return animationLoseLeft;
    }
    public void setAnimationLoseLeft(Animation animationLoseLeft) {
        this.animationLoseLeft = animationLoseLeft;
    }
    public Animation getAnimationLoseKeepRight() {
        return animationLoseKeepRight;
    }
    public void setAnimationLoseKeepRight(Animation animationLoseKeepRight) {
        this.animationLoseKeepRight = animationLoseKeepRight;
    }
    public Animation getAnimationLoseKeepLeft() {
        return animationLoseKeepLeft;
    }
    public void setAnimationLoseKeepLeft(Animation animationLoseKeepLeft) {
        this.animationLoseKeepLeft = animationLoseKeepLeft;
    }
}

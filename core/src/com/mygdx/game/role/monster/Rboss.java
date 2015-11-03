package com.mygdx.game.role.monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.role.BaseRole;

/**魔王腳色父類別
 * Created by 6193 on 2015/10/28.
 */
public abstract class Rboss extends Rmonster implements BaseRole {
    public String roleType = "boss";
    public Animation animationWin;//勝利動畫
    public Animation animationWinKeep;//勝利持續姿勢動畫
    public Animation animationLoseRight;//死亡(右)動畫
    public Animation animationLoseLeft;//死亡(左)動畫
    public Animation animationLoseKeepRight;//死亡持續姿勢(右)動畫
    public Animation animationLoseKeepLeft;//死亡持續姿勢(左)動畫


    //************************************************setter/getter**************************************************
    public Animation getAnimationWin() {
        return animationWin;
    }
    public void setAnimationWin(Animation animationWin) {
        this.animationWin = animationWin;
    }
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
    public Animation getAnimationWinKeep() {
        return animationWinKeep;
    }
    public void setAnimationWinKeep(Animation animationWinKeep) {
        this.animationWinKeep = animationWinKeep;
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

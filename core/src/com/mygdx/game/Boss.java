package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by User on 2015/10/21.
 */
public abstract class Boss extends Actor {

    public Vector2 bossPosition = new Vector2();//魔王當前位置
    public TextureRegion hero1Frame;//當前人物的畫面
    public String currentAction = "Standing";//人物當前動作(預設為站立)
    public boolean isFacingRight;//是否面向右邊
    public int HP = 1000;





    //************************************************setter/getter**************************************************

    public Vector2 getBossPosition() {
        return bossPosition;
    }

    public void setBossPosition(Vector2 bossPosition) {
        this.bossPosition = bossPosition;
    }

    public TextureRegion getHero1Frame() {
        return hero1Frame;
    }

    public void setHero1Frame(TextureRegion hero1Frame) {
        this.hero1Frame = hero1Frame;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setIsFacingRight(boolean isFacingRight) {
        this.isFacingRight = isFacingRight;
    }
}

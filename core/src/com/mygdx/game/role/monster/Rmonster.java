package com.mygdx.game.role.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.role.BaseRole;
import com.mygdx.game.role.hero.Rhero;

/**遊戲中所有的怪(BOSS 與 小怪)的父類別
 * Created by 6193 on 2015/10/29.
 */
public abstract class Rmonster extends Actor implements BaseRole {
    public String roleType = "monster";
    public Vector2 position = new Vector2();//怪物當前位置
    public TextureRegion monsterFrame;//當前怪物的畫面
    public String currentAction = "Standing";//怪物當前動作(預設為站立)
    public String beforeAction = "Standing";//怪物上一個動作
    public boolean isFacingRight;//是否面向右邊
    public int HP = 300;
    public TextureAtlas monster1Atlas;//怪物系列圖檔資源
    public Animation currentAnimation;//怪物當前執行的動畫
    public Animation animationHurtLeft;//受傷(左)動畫
    public Animation animationHurtRight;//受傷(右)動畫
    public Vector2 velocity = new Vector2();//英雄的方向速度
    public float resultRunTime = 0.0f;
    public float animationTime = 0.0f;
    public boolean beKilled = false;


    public Rhero target;
    public int serchRange = 150;

    //怪物的人工智慧
    public abstract void callAI();

    //更新怪物行為
    public abstract void updateMonsterAction(float deltaTime, boolean isLeftTouchDown, boolean isRightTouchDown, boolean isLeftSprintJump, boolean isRightSprintJump);

    //刷新動畫時間
    public void updateAnimationTime(){
        if(!currentAction.equals(beforeAction)){
            animationTime = 0.0f;
        }
        animationTime+= Gdx.graphics.getDeltaTime();
    }

    //************************************************setter/getter**************************************************
    public String getRoleType() {
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public TextureRegion getMonsterFrame() {
        return monsterFrame;
    }
    public void setMonsterFrame(TextureRegion monsterFrame) {
        this.monsterFrame = monsterFrame;
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
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
    public TextureAtlas getMonster1Atlas() {
        return monster1Atlas;
    }
    public void setMonster1Atlas(TextureAtlas monster1Atlas) {
        this.monster1Atlas = monster1Atlas;
    }
    public Animation getCurrentAnimation() {
        return currentAnimation;
    }
    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }
    public Animation getAnimationHurtLeft() {
        return animationHurtLeft;
    }
    public void setAnimationHurtLeft(Animation animationHurtLeft) {
        this.animationHurtLeft = animationHurtLeft;
    }
    public Animation getAnimationHurtRight() {
        return animationHurtRight;
    }
    public void setAnimationHurtRight(Animation animationHurtRight) {
        this.animationHurtRight = animationHurtRight;
    }
    public Rhero getTarget() {
        return target;
    }
    public void setTarget(Rhero target) {
        this.target = target;
    }
    public int getSerchRange() {
        return serchRange;
    }
    public void setSerchRange(int serchRange) {
        this.serchRange = serchRange;
    }
    public Vector2 getVelocity() {
        return velocity;
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public boolean isBeKilled() {
        return beKilled;
    }
    public void setBeKilled(boolean beKilled) {
        this.beKilled = beKilled;
    }
    public String getBeforeAction() {
        return beforeAction;
    }
    public void setBeforeAction(String beforeAction) {
        this.beforeAction = beforeAction;
    }
}

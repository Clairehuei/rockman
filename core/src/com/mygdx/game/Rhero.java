package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by 6193 on 2015/10/27.
 */
public class Rhero extends Actor implements BaseRole {

    public String roleType = "Hero";

    Vector2 position = new Vector2();//英雄當前位置
    Vector2 beforePosition = new Vector2();//英雄前一個位置
    Vector2 velocity = new Vector2();//英雄的方向速度

    public TextureAtlas hero1Atlas;//人物系列圖檔資源
    public TextureRegion hero1Frame;//當前人物的畫面
    public String currentAction = "Standing";//人物當前動作(預設為站立)
    public boolean isFacingRight;//是否面向右邊
    public boolean isJumpAndWalk = false;//是否在空中且跑步狀態
    public Animation currentAnimation;//英雄當前執行的動畫
    public Animation animationStandingLeft;//站立(左)動畫
    public Animation animationStandingRight;//站立(右)動畫
    public Animation animationWalkingLeft;//跑步(左)動畫
    public Animation animationWalkingRight;//跑步(右)動畫

    public Animation animationHurtLeft;//受傷(左)動畫
    public Animation animationHurtRight;//受傷(右)動畫
    public Animation animationWin;//勝利動畫
    public Animation animationWinKeep;//勝利持續姿勢動畫
    public Animation animationLoseRight;//死亡(右)動畫
    public Animation animationLoseLeft;//死亡(左)動畫
    public Animation animationLoseKeepRight;//死亡持續姿勢(右)動畫
    public Animation animationLoseKeepLeft;//死亡持續姿勢(左)動畫

    public TextureRegion jumpingLeftUp;//左前跳躍(上升中)
    public TextureRegion jumpingLeftDown;//左前跳躍(下降中)
    public TextureRegion jumpingRightUp;//右前跳躍(上升中)
    public TextureRegion jumpingRightDown;//右前跳躍(下降中)


    //************************************************setter/getter**************************************************

    public TextureAtlas getHero1Atlas() {
        return hero1Atlas;
    }

    public void setHero1Atlas(TextureAtlas hero1Atlas) {
        this.hero1Atlas = hero1Atlas;
    }

    public TextureRegion getHero1Frame() {
        return hero1Frame;
    }

    public void setHero1Frame(TextureRegion hero1Frame) {
//        System.out.println("hero1Frame.hashCode() = "+hero1Frame.hashCode());
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

    public boolean isJumpAndWalk() {
        return isJumpAndWalk;
    }

    public void setIsJumpAndWalk(boolean isJumpAndWalk) {
        this.isJumpAndWalk = isJumpAndWalk;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation getAnimationStandingLeft() {
        return animationStandingLeft;
    }

    public void setAnimationStandingLeft(Animation animationStandingLeft) {
        this.animationStandingLeft = animationStandingLeft;
    }

    public Animation getAnimationStandingRight() {
        return animationStandingRight;
    }

    public void setAnimationStandingRight(Animation animationStandingRight) {
        this.animationStandingRight = animationStandingRight;
    }

    public Animation getAnimationWalkingLeft() {
        return animationWalkingLeft;
    }

    public void setAnimationWalkingLeft(Animation animationWalkingLeft) {
        this.animationWalkingLeft = animationWalkingLeft;
    }

    public Animation getAnimationWalkingRight() {
        return animationWalkingRight;
    }

    public void setAnimationWalkingRight(Animation animationWalkingRight) {
        this.animationWalkingRight = animationWalkingRight;
    }

    public TextureRegion getJumpingLeftUp() {
        return jumpingLeftUp;
    }

    public void setJumpingLeftUp(TextureRegion jumpingLeftUp) {
        this.jumpingLeftUp = jumpingLeftUp;
    }

    public TextureRegion getJumpingLeftDown() {
        return jumpingLeftDown;
    }

    public void setJumpingLeftDown(TextureRegion jumpingLeftDown) {
        this.jumpingLeftDown = jumpingLeftDown;
    }

    public TextureRegion getJumpingRightUp() {
        return jumpingRightUp;
    }

    public void setJumpingRightUp(TextureRegion jumpingRightUp) {
        this.jumpingRightUp = jumpingRightUp;
    }

    public TextureRegion getJumpingRightDown() {
        return jumpingRightDown;
    }

    public void setJumpingRightDown(TextureRegion jumpingRightDown) {
        this.jumpingRightDown = jumpingRightDown;
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

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getBeforePosition() {
        return beforePosition;
    }

    public void setBeforePosition(Vector2 beforePosition) {
        this.beforePosition = beforePosition;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}

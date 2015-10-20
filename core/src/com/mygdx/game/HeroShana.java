package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**英雄-灼眼夏娜
 * Created by 6193 on 2015/10/20.
 */
public class HeroShana extends Hero {

    private static final float FRAME_DURATION = 1.0f / 15.0f;
    private static final float FRAME_DURATION_STAND = 1.0f / 8.0f;
    private static final float FRAME_DURATION_RUN = 1.0f / 15.0f;
    private static final float FRAME_DURATION_JUMP = 1.0f / 15.0f;
    private TextureAtlas hero1Atlas;
    private TextureRegion hero1Frame;

    private String currentAction = "Standing";//人物當前動作(預設為站立)

    private boolean isFacingRight;
    private boolean isJumpAndWalk = false;
    private Animation animationStandingLeft;
    private Animation animationStandingRight;
    private Animation animationWalkingLeft;
    private Animation animationWalkingRight;
    private TextureRegion jumpingLeftUp;
    private TextureRegion jumpingLeftDown;
    private TextureRegion jumpingRightUp;
    private TextureRegion jumpingRightDown;


    public HeroShana(){
        init();
    }

    public void init(){
        isFacingRight = true;
        this.setCurrentAction("Jumping");

        //Load atlases and textures
        hero1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/heroShana.pack"));

        //Initialize hero1 Standing Left & Right, Walking Left & Right
        //*****************************人物右邊設定*****************************
        //右邊跳躍
        jumpingRightUp = hero1Atlas.findRegion("JumpingRight1");
        jumpingRightDown = hero1Atlas.findRegion("JumpingRight2");

        //右邊站立
        TextureRegion[] frameStandingRight = new TextureRegion[6];
        frameStandingRight[0] = hero1Atlas.findRegion("StandingRight1");
        frameStandingRight[1] = hero1Atlas.findRegion("StandingRight2");
        frameStandingRight[2] = hero1Atlas.findRegion("StandingRight3");
        frameStandingRight[3] = hero1Atlas.findRegion("StandingRight4");
        frameStandingRight[4] = hero1Atlas.findRegion("StandingRight5");
        frameStandingRight[5] = hero1Atlas.findRegion("StandingRight6");
        animationStandingRight = new Animation(FRAME_DURATION_STAND, frameStandingRight);

        //右邊跑步
        TextureRegion[] frameWalkingRight = new TextureRegion[8];
        frameWalkingRight[0] = hero1Atlas.findRegion("RunningRight1");
        frameWalkingRight[1] = hero1Atlas.findRegion("RunningRight2");
        frameWalkingRight[2] = hero1Atlas.findRegion("RunningRight3");
        frameWalkingRight[3] = hero1Atlas.findRegion("RunningRight4");
        frameWalkingRight[4] = hero1Atlas.findRegion("RunningRight5");
        frameWalkingRight[5] = hero1Atlas.findRegion("RunningRight6");
        frameWalkingRight[6] = hero1Atlas.findRegion("RunningRight7");
        frameWalkingRight[7] = hero1Atlas.findRegion("RunningRight8");
        animationWalkingRight = new Animation(FRAME_DURATION_RUN, frameWalkingRight);


        //*****************************人物左邊設定*****************************
        //左邊跳躍
        jumpingLeftUp = hero1Atlas.findRegion("JumpingLeft1");
        jumpingLeftDown = hero1Atlas.findRegion("JumpingLeft2");

        //左邊站立
        TextureRegion[] frameStandingLeft = new TextureRegion[6];
        frameStandingLeft[0] = hero1Atlas.findRegion("StandingLeft1");
        frameStandingLeft[1] = hero1Atlas.findRegion("StandingLeft2");
        frameStandingLeft[2] = hero1Atlas.findRegion("StandingLeft3");
        frameStandingLeft[3] = hero1Atlas.findRegion("StandingLeft4");
        frameStandingLeft[4] = hero1Atlas.findRegion("StandingLeft5");
        frameStandingLeft[5] = hero1Atlas.findRegion("StandingLeft6");
        animationStandingLeft = new Animation(FRAME_DURATION_STAND, frameStandingLeft);

        //左邊跑步
        TextureRegion[] frameWalkingLeft = new TextureRegion[8];
        frameWalkingLeft[0] = hero1Atlas.findRegion("RunningLeft1");
        frameWalkingLeft[1] = hero1Atlas.findRegion("RunningLeft2");
        frameWalkingLeft[2] = hero1Atlas.findRegion("RunningLeft3");
        frameWalkingLeft[3] = hero1Atlas.findRegion("RunningLeft4");
        frameWalkingLeft[4] = hero1Atlas.findRegion("RunningLeft5");
        frameWalkingLeft[5] = hero1Atlas.findRegion("RunningLeft6");
        frameWalkingLeft[6] = hero1Atlas.findRegion("RunningLeft7");
        frameWalkingLeft[7] = hero1Atlas.findRegion("RunningLeft8");
        animationWalkingLeft = new Animation(FRAME_DURATION_RUN, frameWalkingLeft);
    }


    //*********************************setter/getter***************************************

    public Animation getAnimationWalkingRight() {
        return animationWalkingRight;
    }

    public void setAnimationWalkingRight(Animation animationWalkingRight) {
        this.animationWalkingRight = animationWalkingRight;
    }

    public static float getFrameDuration() {
        return FRAME_DURATION;
    }

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
        this.hero1Frame = hero1Frame;
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

    public Animation getAnimationWalkingLeft() {
        return animationWalkingLeft;
    }

    public void setAnimationWalkingLeft(Animation animationWalkingLeft) {
        this.animationWalkingLeft = animationWalkingLeft;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
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

}

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
    private TextureRegion hero1StandingLeft;
    private TextureRegion hero1StandingRight;
    private TextureRegion hero1Left;
    private TextureRegion hero1Right;
    private TextureRegion hero1Frame;
    private Sprite spriteHero1, spriteHero2, spriteHero3;


    private String currentAction = "Standing";

    private boolean isFacingRight;
    private boolean isJumpAndWalk = false;
    private Animation animationStandingLeft;
    private Animation animationStandingRight;
    private Animation animationWalkingLeft;
    private Animation animationWalkingRight;
    private Animation animationJumpingLeft;
    private Animation animationJumpingRight;


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
//        hero1Right = hero1Atlas.findRegion("jumpright");
        hero1StandingRight = hero1Atlas.findRegion("RunningRight5");

        //右邊跳躍
        TextureRegion[] frameJumpingRight = new TextureRegion[12];
        frameJumpingRight[0] = hero1Atlas.findRegion("JumpingRight1");
        frameJumpingRight[1] = hero1Atlas.findRegion("JumpingRight2");
        frameJumpingRight[2] = hero1Atlas.findRegion("JumpingRight3");
        frameJumpingRight[3] = hero1Atlas.findRegion("JumpingRight4");
        frameJumpingRight[4] = hero1Atlas.findRegion("JumpingRight5");
        frameJumpingRight[5] = hero1Atlas.findRegion("JumpingRight6");
        frameJumpingRight[6] = hero1Atlas.findRegion("JumpingRight7");
        frameJumpingRight[7] = hero1Atlas.findRegion("JumpingRight8");
        frameJumpingRight[8] = hero1Atlas.findRegion("JumpingRight9");
        frameJumpingRight[9] = hero1Atlas.findRegion("JumpingRight10");
        frameJumpingRight[10] = hero1Atlas.findRegion("JumpingRight11");
        frameJumpingRight[11] = hero1Atlas.findRegion("JumpingRight12");
        animationJumpingRight = new Animation(FRAME_DURATION_JUMP, frameJumpingRight);

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
//        hero1Left = hero1Atlas.findRegion("jumpleft");
        hero1StandingLeft = hero1Atlas.findRegion("RunningLeft5");

        //左邊跳躍
        TextureRegion[] frameJumpingLeft = new TextureRegion[12];
        frameJumpingLeft[0] = hero1Atlas.findRegion("JumpingLeft1");
        frameJumpingLeft[1] = hero1Atlas.findRegion("JumpingLeft2");
        frameJumpingLeft[2] = hero1Atlas.findRegion("JumpingLeft3");
        frameJumpingLeft[3] = hero1Atlas.findRegion("JumpingLeft4");
        frameJumpingLeft[4] = hero1Atlas.findRegion("JumpingLeft5");
        frameJumpingLeft[5] = hero1Atlas.findRegion("JumpingLeft6");
        frameJumpingLeft[6] = hero1Atlas.findRegion("JumpingLeft7");
        frameJumpingLeft[7] = hero1Atlas.findRegion("JumpingLeft8");
        frameJumpingLeft[8] = hero1Atlas.findRegion("JumpingLeft9");
        frameJumpingLeft[9] = hero1Atlas.findRegion("JumpingLeft10");
        frameJumpingLeft[10] = hero1Atlas.findRegion("JumpingLeft11");
        frameJumpingLeft[11] = hero1Atlas.findRegion("JumpingLeft12");
        animationJumpingLeft = new Animation(FRAME_DURATION_JUMP, frameJumpingLeft);

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


        spriteHero1 = new Sprite(hero1StandingRight);
        spriteHero1.setPosition(880, 690);
        spriteHero1.setScale(0.5f);

        spriteHero2 = new Sprite(hero1StandingRight);
        spriteHero2.setPosition(880 + 40, 690);
        spriteHero2.setScale(0.5f);

        spriteHero3 = new Sprite(hero1StandingRight);
        spriteHero3.setPosition(880 + 80, 690);
        spriteHero3.setScale(0.5f);
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

    public TextureRegion getHero1StandingLeft() {
        return hero1StandingLeft;
    }

    public void setHero1StandingLeft(TextureRegion hero1StandingLeft) {
        this.hero1StandingLeft = hero1StandingLeft;
    }

    public TextureRegion getHero1StandingRight() {
        return hero1StandingRight;
    }

    public void setHero1StandingRight(TextureRegion hero1StandingRight) {
        this.hero1StandingRight = hero1StandingRight;
    }

    public TextureRegion getHero1Left() {
        return hero1Left;
    }

    public void setHero1Left(TextureRegion hero1Left) {
        this.hero1Left = hero1Left;
    }

    public TextureRegion getHero1Right() {
        return hero1Right;
    }

    public void setHero1Right(TextureRegion hero1Right) {
        this.hero1Right = hero1Right;
    }

    public TextureRegion getHero1Frame() {
        return hero1Frame;
    }

    public void setHero1Frame(TextureRegion hero1Frame) {
        this.hero1Frame = hero1Frame;
    }

    public Sprite getSpriteHero1() {
        return spriteHero1;
    }

    public void setSpriteHero1(Sprite spriteHero1) {
        this.spriteHero1 = spriteHero1;
    }

    public Sprite getSpriteHero2() {
        return spriteHero2;
    }

    public void setSpriteHero2(Sprite spriteHero2) {
        this.spriteHero2 = spriteHero2;
    }

    public Sprite getSpriteHero3() {
        return spriteHero3;
    }

    public void setSpriteHero3(Sprite spriteHero3) {
        this.spriteHero3 = spriteHero3;
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

    public Animation getAnimationJumpingLeft() {
        return animationJumpingLeft;
    }

    public void setAnimationJumpingLeft(Animation animationJumpingLeft) {
        this.animationJumpingLeft = animationJumpingLeft;
    }

    public Animation getAnimationJumpingRight() {
        return animationJumpingRight;
    }

    public void setAnimationJumpingRight(Animation animationJumpingRight) {
        this.animationJumpingRight = animationJumpingRight;
    }
}

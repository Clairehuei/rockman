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
        hero1Atlas = new TextureAtlas(Gdx.files.internal("data/hero1atlas.pack"));

        //Initialize hero1 Standing Left & Right, Walking Left & Right
        hero1Right = hero1Atlas.findRegion("jumpright");
        hero1StandingRight = hero1Atlas.findRegion("walk002");
        TextureRegion[] frameWalkingRight = new TextureRegion[3];
        frameWalkingRight[0] = hero1Atlas.findRegion("walk000");
        frameWalkingRight[1] = hero1Atlas.findRegion("walk001");
        frameWalkingRight[2] = hero1Atlas.findRegion("walk002");
        animationWalkingRight = new Animation(FRAME_DURATION, frameWalkingRight);

        hero1Left = hero1Atlas.findRegion("jumpleft");
        hero1StandingLeft = hero1Atlas.findRegion("walk005");
        TextureRegion[] frameWalkingLeft = new TextureRegion[3];
        frameWalkingLeft[0] = hero1Atlas.findRegion("walk003");
        frameWalkingLeft[1] = hero1Atlas.findRegion("walk004");
        frameWalkingLeft[2] = hero1Atlas.findRegion("walk005");
        animationWalkingLeft = new Animation(FRAME_DURATION, frameWalkingLeft);


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
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**英雄-灼眼夏娜
 * Created by 6193 on 2015/10/20.
 */
public class HeroShana extends Hero {

    private static final float FRAME_DURATION = 1.0f / 15.0f;
    private static final float FRAME_DURATION_STAND = 1.0f / 8.0f;//站立動畫的播放速度
    private static final float FRAME_DURATION_RUN = 1.0f / 15.0f;//跑步動畫的播放速度
    private static final float FRAME_DURATION_ATK1 = 1.0f / 13.0f;//普通攻擊(A模式)的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_JUMP = 1.0f / 15.0f;//跳躍動畫的播放速度(暫時忽略)

    private TextureAtlas hero1Atlas;//人物系列圖檔資源
    private TextureRegion hero1Frame;//當前人物的畫面

    private String currentAction = "Standing";//人物當前動作(預設為站立)

    private boolean isFacingRight;//是否面向右邊
    private boolean isJumpAndWalk = false;//是否在空中且跑步狀態

    private Animation animationStandingLeft;//站立(左)動畫
    private Animation animationStandingRight;//站立(右)動畫
    private Animation animationWalkingLeft;//跑步(左)動畫
    private Animation animationWalkingRight;//跑步(右)動畫
    private Animation animationAttaRight;//普通攻擊(A模式)(右)動畫
    private Animation animationAttaLeft;//普通攻擊(A模式)(左)動畫

    private Animation currentAnimation;//英雄當前執行的動畫

    private TextureRegion jumpingLeftUp;//左前跳躍(上升中)
    private TextureRegion jumpingLeftDown;//左前跳躍(下降中)
    private TextureRegion jumpingRightUp;//右前跳躍(上升中)
    private TextureRegion jumpingRightDown;//右前跳躍(下降中)


    public HeroShana(){
        init();
    }

    public void init(){
        isFacingRight = true;
        this.setCurrentAction("Jumping");

        //讀取人物圖檔資源
        hero1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/heroShana.pack"));

        //開始初始化人物所有動作
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


        //右邊普通攻擊(A模式)
        TextureRegion[] frameAtkaRight = new TextureRegion[8];
        frameAtkaRight[0] = hero1Atlas.findRegion("RightATTA1");
        frameAtkaRight[1] = hero1Atlas.findRegion("RightATTA2");
        frameAtkaRight[2] = hero1Atlas.findRegion("RightATTA3");
        frameAtkaRight[3] = hero1Atlas.findRegion("RightATTA4");
        frameAtkaRight[4] = hero1Atlas.findRegion("RightATTA5");
        frameAtkaRight[5] = hero1Atlas.findRegion("RightATTA6");
        frameAtkaRight[6] = hero1Atlas.findRegion("RightATTA7");
        frameAtkaRight[7] = hero1Atlas.findRegion("RightATTA8");
        animationAttaRight = new Animation(FRAME_DURATION_ATK1, frameAtkaRight);


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

        //左邊普通攻擊(A模式)
        TextureRegion[] frameAtkaLeft = new TextureRegion[8];
        frameAtkaLeft[0] = hero1Atlas.findRegion("LeftATTA1");
        frameAtkaLeft[1] = hero1Atlas.findRegion("LeftATTA2");
        frameAtkaLeft[2] = hero1Atlas.findRegion("LeftATTA3");
        frameAtkaLeft[3] = hero1Atlas.findRegion("LeftATTA4");
        frameAtkaLeft[4] = hero1Atlas.findRegion("LeftATTA5");
        frameAtkaLeft[5] = hero1Atlas.findRegion("LeftATTA6");
        frameAtkaLeft[6] = hero1Atlas.findRegion("LeftATTA7");
        frameAtkaLeft[7] = hero1Atlas.findRegion("LeftATTA8");
        animationAttaLeft = new Animation(FRAME_DURATION_ATK1, frameAtkaLeft);
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
//        System.out.println("hero1Frame.hashCode() = "+hero1Frame.hashCode());
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

    public Animation getAnimationAttaRight() {
        return animationAttaRight;
    }

    public void setAnimationAttaRight(Animation animationAttaRight) {
        this.animationAttaRight = animationAttaRight;
    }

    public Animation getAnimationAttaLeft() {
        return animationAttaLeft;
    }

    public void setAnimationAttaLeft(Animation animationAttaLeft) {
        this.animationAttaLeft = animationAttaLeft;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }
}

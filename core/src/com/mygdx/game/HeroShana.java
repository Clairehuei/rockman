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
    private static final float FRAME_DURATION_WIN = 1.0f / 8.0f;//勝利動畫的播放速度
    private static final float FRAME_DURATION_WINKEEP = 1.0f / 8.0f;//勝利持續姿勢動畫的播放速度
    private static final float FRAME_DURATION_LOSE = 1.0f / 4.5f;//死亡動畫的播放速度
    private static final float FRAME_DURATION_LOSEKEEP = 1.0f / 2.5f;//死亡持續姿勢動畫的播放速度
    private static final float FRAME_DURATION_ATK1 = 1.0f / 14.0f;//普通攻擊(A模式)的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_ATK2 = 1.0f / 14.0f;//普通攻擊(B模式)的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_ATK3 = 1.0f / 14.0f;//普通攻擊(C模式)的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_SATK1 = 1.0f / 11.0f;//特殊技能1的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_HURT = 1.0f / 15.0f;//受傷的播放速度 = 每一格動作的播放間隔時間
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
    private Animation animationAttbRight;//普通攻擊(B模式)(右)動畫
    private Animation animationAttbLeft;//普通攻擊(B模式)(左)動畫
    private Animation animationAttcRight;//普通攻擊(C模式)(右)動畫
    private Animation animationAttcLeft;//普通攻擊(C模式)(左)動畫
    private Animation animationSatkaRight;//特殊技能1(右)動畫
    private Animation animationSatkaLeft;//特殊技能1(左)動畫

    private Animation animationHurtLeft;//受傷(左)動畫
    private Animation animationHurtRight;//受傷(右)動畫
    private Animation animationWin;//勝利動畫
    private Animation animationWinKeep;//勝利持續姿勢動畫
    private Animation animationLoseRight;//死亡(右)動畫
    private Animation animationLoseLeft;//死亡(左)動畫
    private Animation animationLoseKeepRight;//死亡持續姿勢(右)動畫
    private Animation animationLoseKeepLeft;//死亡持續姿勢(左)動畫

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

        //右邊普通攻擊(B模式)
        TextureRegion[] frameAtkbRight = new TextureRegion[8];
        frameAtkbRight[0] = hero1Atlas.findRegion("RightATTB1");
        frameAtkbRight[1] = hero1Atlas.findRegion("RightATTB2");
        frameAtkbRight[2] = hero1Atlas.findRegion("RightATTB3");
        frameAtkbRight[3] = hero1Atlas.findRegion("RightATTB4");
        frameAtkbRight[4] = hero1Atlas.findRegion("RightATTB5");
        frameAtkbRight[5] = hero1Atlas.findRegion("RightATTB6");
        frameAtkbRight[6] = hero1Atlas.findRegion("RightATTB7");
        frameAtkbRight[7] = hero1Atlas.findRegion("RightATTB8");
        animationAttbRight = new Animation(FRAME_DURATION_ATK2, frameAtkbRight);

        //右邊普通攻擊(C模式)
        TextureRegion[] frameAtkcRight = new TextureRegion[7];
        frameAtkcRight[0] = hero1Atlas.findRegion("RightATTC1");
        frameAtkcRight[1] = hero1Atlas.findRegion("RightATTC2");
        frameAtkcRight[2] = hero1Atlas.findRegion("RightATTC3");
        frameAtkcRight[3] = hero1Atlas.findRegion("RightATTC4");
        frameAtkcRight[4] = hero1Atlas.findRegion("RightATTC5");
        frameAtkcRight[5] = hero1Atlas.findRegion("RightATTC6");
        frameAtkcRight[6] = hero1Atlas.findRegion("RightATTC7");
        animationAttcRight = new Animation(FRAME_DURATION_ATK3, frameAtkcRight);

        //右邊特殊技能1
        TextureRegion[] frameSatkaRight = new TextureRegion[11];
        frameSatkaRight[0] = hero1Atlas.findRegion("SatkRight01");
        frameSatkaRight[1] = hero1Atlas.findRegion("SatkRight02");
        frameSatkaRight[2] = hero1Atlas.findRegion("SatkRight03");
        frameSatkaRight[3] = hero1Atlas.findRegion("SatkRight04");
        frameSatkaRight[4] = hero1Atlas.findRegion("SatkRight05");
        frameSatkaRight[5] = hero1Atlas.findRegion("SatkRight06");
        frameSatkaRight[6] = hero1Atlas.findRegion("SatkRight07");
        frameSatkaRight[7] = hero1Atlas.findRegion("SatkRight08");
        frameSatkaRight[8] = hero1Atlas.findRegion("SatkRight09");
        frameSatkaRight[9] = hero1Atlas.findRegion("SatkRight10");
        frameSatkaRight[10] = hero1Atlas.findRegion("SatkRight11");
        animationSatkaRight = new Animation(FRAME_DURATION_SATK1, frameSatkaRight);

        //右邊受傷
        TextureRegion[] frameHurtRight = new TextureRegion[3];
        frameHurtRight[0] = hero1Atlas.findRegion("HurtRight");
        frameHurtRight[1] = hero1Atlas.findRegion("HurtRight");
        frameHurtRight[2] = hero1Atlas.findRegion("HurtRight");
        animationHurtRight = new Animation(FRAME_DURATION_HURT, frameHurtRight);

        //右邊死亡
        TextureRegion[] frameLoseRight = new TextureRegion[3];
        frameLoseRight[0] = hero1Atlas.findRegion("LoseRight1");
        frameLoseRight[1] = hero1Atlas.findRegion("LoseRight2");
        frameLoseRight[2] = hero1Atlas.findRegion("LoseRight3");
        animationLoseRight = new Animation(FRAME_DURATION_LOSE, frameLoseRight);

        //右邊死亡持續姿勢
        TextureRegion[] frameLoseKeepRight = new TextureRegion[3];
        frameLoseKeepRight[0] = hero1Atlas.findRegion("LoseRight3");
        frameLoseKeepRight[1] = hero1Atlas.findRegion("LoseRight3");
        frameLoseKeepRight[2] = hero1Atlas.findRegion("LoseRight3");
        animationLoseKeepRight = new Animation(FRAME_DURATION_LOSEKEEP, frameLoseKeepRight);


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

        //左邊普通攻擊(B模式)
        TextureRegion[] frameAtkbLeft = new TextureRegion[8];
        frameAtkbLeft[0] = hero1Atlas.findRegion("LeftATTB1");
        frameAtkbLeft[1] = hero1Atlas.findRegion("LeftATTB2");
        frameAtkbLeft[2] = hero1Atlas.findRegion("LeftATTB3");
        frameAtkbLeft[3] = hero1Atlas.findRegion("LeftATTB4");
        frameAtkbLeft[4] = hero1Atlas.findRegion("LeftATTB5");
        frameAtkbLeft[5] = hero1Atlas.findRegion("LeftATTB6");
        frameAtkbLeft[6] = hero1Atlas.findRegion("LeftATTB7");
        frameAtkbLeft[7] = hero1Atlas.findRegion("LeftATTB8");
        animationAttbLeft = new Animation(FRAME_DURATION_ATK2, frameAtkbLeft);

        //左邊普通攻擊(C模式)
        TextureRegion[] frameAtkcLeft = new TextureRegion[7];
        frameAtkcLeft[0] = hero1Atlas.findRegion("LeftATTC1");
        frameAtkcLeft[1] = hero1Atlas.findRegion("LeftATTC2");
        frameAtkcLeft[2] = hero1Atlas.findRegion("LeftATTC3");
        frameAtkcLeft[3] = hero1Atlas.findRegion("LeftATTC4");
        frameAtkcLeft[4] = hero1Atlas.findRegion("LeftATTC5");
        frameAtkcLeft[5] = hero1Atlas.findRegion("LeftATTC6");
        frameAtkcLeft[6] = hero1Atlas.findRegion("LeftATTC7");
        animationAttcLeft = new Animation(FRAME_DURATION_ATK3, frameAtkcLeft);

        //左邊特殊技能1
        TextureRegion[] frameSatkaLeft = new TextureRegion[11];
        frameSatkaLeft[0] = hero1Atlas.findRegion("SatkLeft01");
        frameSatkaLeft[1] = hero1Atlas.findRegion("SatkLeft02");
        frameSatkaLeft[2] = hero1Atlas.findRegion("SatkLeft03");
        frameSatkaLeft[3] = hero1Atlas.findRegion("SatkLeft04");
        frameSatkaLeft[4] = hero1Atlas.findRegion("SatkLeft05");
        frameSatkaLeft[5] = hero1Atlas.findRegion("SatkLeft06");
        frameSatkaLeft[6] = hero1Atlas.findRegion("SatkLeft07");
        frameSatkaLeft[7] = hero1Atlas.findRegion("SatkLeft08");
        frameSatkaLeft[8] = hero1Atlas.findRegion("SatkLeft09");
        frameSatkaLeft[9] = hero1Atlas.findRegion("SatkLeft10");
        frameSatkaLeft[10] = hero1Atlas.findRegion("SatkLeft11");
        animationSatkaLeft = new Animation(FRAME_DURATION_SATK1, frameSatkaLeft);

        //左邊受傷
        TextureRegion[] frameHurtLeft = new TextureRegion[3];
        frameHurtLeft[0] = hero1Atlas.findRegion("HurtLeft");
        frameHurtLeft[1] = hero1Atlas.findRegion("HurtLeft");
        frameHurtLeft[2] = hero1Atlas.findRegion("HurtLeft");
        animationHurtLeft = new Animation(FRAME_DURATION_HURT, frameHurtLeft);

        //左邊死亡
        TextureRegion[] frameLoseLeft = new TextureRegion[3];
        frameLoseLeft[0] = hero1Atlas.findRegion("LoseLeft1");
        frameLoseLeft[1] = hero1Atlas.findRegion("LoseLeft2");
        frameLoseLeft[2] = hero1Atlas.findRegion("LoseLeft3");
        animationLoseLeft = new Animation(FRAME_DURATION_LOSE, frameLoseLeft);

        //左邊死亡持續姿勢
        TextureRegion[] frameLoseKeepLeft = new TextureRegion[3];
        frameLoseKeepLeft[0] = hero1Atlas.findRegion("LoseLeft3");
        frameLoseKeepLeft[1] = hero1Atlas.findRegion("LoseLeft3");
        frameLoseKeepLeft[2] = hero1Atlas.findRegion("LoseLeft3");
        animationLoseKeepLeft = new Animation(FRAME_DURATION_LOSEKEEP, frameLoseKeepLeft);




        //勝利
        TextureRegion[] frameWin = new TextureRegion[8];
        frameWin[0] = hero1Atlas.findRegion("Win1");
        frameWin[1] = hero1Atlas.findRegion("Win2");
        frameWin[2] = hero1Atlas.findRegion("Win3");
        frameWin[3] = hero1Atlas.findRegion("Win4");
        frameWin[4] = hero1Atlas.findRegion("Win5");
        frameWin[5] = hero1Atlas.findRegion("Win6");
        frameWin[6] = hero1Atlas.findRegion("Win7");
        frameWin[7] = hero1Atlas.findRegion("Win8");
        animationWin = new Animation(FRAME_DURATION_WIN, frameWin);

        //勝利持續姿勢
        TextureRegion[] frameWinKeep = new TextureRegion[3];
        frameWinKeep[0] = hero1Atlas.findRegion("WinKeep1");
        frameWinKeep[1] = hero1Atlas.findRegion("WinKeep2");
        frameWinKeep[2] = hero1Atlas.findRegion("WinKeep3");
        animationWinKeep = new Animation(FRAME_DURATION_WINKEEP, frameWinKeep);
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

    public Animation getAnimationAttbRight() {
        return animationAttbRight;
    }

    public void setAnimationAttbRight(Animation animationAttbRight) {
        this.animationAttbRight = animationAttbRight;
    }

    public Animation getAnimationAttbLeft() {
        return animationAttbLeft;
    }

    public void setAnimationAttbLeft(Animation animationAttbLeft) {
        this.animationAttbLeft = animationAttbLeft;
    }

    public Animation getAnimationAttcRight() {
        return animationAttcRight;
    }

    public void setAnimationAttcRight(Animation animationAttcRight) {
        this.animationAttcRight = animationAttcRight;
    }

    public Animation getAnimationAttcLeft() {
        return animationAttcLeft;
    }

    public void setAnimationAttcLeft(Animation animationAttcLeft) {
        this.animationAttcLeft = animationAttcLeft;
    }

    public Animation getAnimationSatkaRight() {
        return animationSatkaRight;
    }

    public void setAnimationSatkaRight(Animation animationSatkaRight) {
        this.animationSatkaRight = animationSatkaRight;
    }

    public Animation getAnimationSatkaLeft() {
        return animationSatkaLeft;
    }

    public void setAnimationSatkaLeft(Animation animationSatkaLeft) {
        this.animationSatkaLeft = animationSatkaLeft;
    }
}

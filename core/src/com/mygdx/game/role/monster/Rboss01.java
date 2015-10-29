package com.mygdx.game.role.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.dao.CollisionDao;

/**第1關BOSS
 * Created by 6193 on 2015/10/28.
 */
public class Rboss01 extends Rboss {

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

    private TextureRegion jumpingLeftUp;//左前跳躍(上升中)
    private TextureRegion jumpingLeftDown;//左前跳躍(下降中)
    private TextureRegion jumpingRightUp;//右前跳躍(上升中)
    private TextureRegion jumpingRightDown;//右前跳躍(下降中)

    private float satkaRunTime = 0.0f;//英雄特殊技能1動畫累積時間

    private float bossHurtRunTime = 0.0f;
    CollisionDao collisionDao;
    int serchResult = 0;

    public Rboss01(){
        init();
    }

    public void init(){
        isFacingRight = true;


        //讀取人物圖檔資源
        monster1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/heroShana.pack"));

        //開始初始化人物所有動作
        //*****************************人物右邊設定*****************************
        //右邊跳躍
        jumpingRightUp = monster1Atlas.findRegion("JumpingRight1");
        jumpingRightDown = monster1Atlas.findRegion("JumpingRight2");

        //右邊站立
        TextureRegion[] frameStandingRight = new TextureRegion[6];
        frameStandingRight[0] = monster1Atlas.findRegion("StandingRight1");
        frameStandingRight[1] = monster1Atlas.findRegion("StandingRight2");
        frameStandingRight[2] = monster1Atlas.findRegion("StandingRight3");
        frameStandingRight[3] = monster1Atlas.findRegion("StandingRight4");
        frameStandingRight[4] = monster1Atlas.findRegion("StandingRight5");
        frameStandingRight[5] = monster1Atlas.findRegion("StandingRight6");
        animationStandingRight = new Animation(FRAME_DURATION_STAND, frameStandingRight);

        //右邊跑步
        TextureRegion[] frameWalkingRight = new TextureRegion[8];
        frameWalkingRight[0] = monster1Atlas.findRegion("RunningRight1");
        frameWalkingRight[1] = monster1Atlas.findRegion("RunningRight2");
        frameWalkingRight[2] = monster1Atlas.findRegion("RunningRight3");
        frameWalkingRight[3] = monster1Atlas.findRegion("RunningRight4");
        frameWalkingRight[4] = monster1Atlas.findRegion("RunningRight5");
        frameWalkingRight[5] = monster1Atlas.findRegion("RunningRight6");
        frameWalkingRight[6] = monster1Atlas.findRegion("RunningRight7");
        frameWalkingRight[7] = monster1Atlas.findRegion("RunningRight8");
        animationWalkingRight = new Animation(FRAME_DURATION_RUN, frameWalkingRight);

        //右邊普通攻擊(A模式)
        TextureRegion[] frameAtkaRight = new TextureRegion[8];
        frameAtkaRight[0] = monster1Atlas.findRegion("RightATTA1");
        frameAtkaRight[1] = monster1Atlas.findRegion("RightATTA2");
        frameAtkaRight[2] = monster1Atlas.findRegion("RightATTA3");
        frameAtkaRight[3] = monster1Atlas.findRegion("RightATTA4");
        frameAtkaRight[4] = monster1Atlas.findRegion("RightATTA5");
        frameAtkaRight[5] = monster1Atlas.findRegion("RightATTA6");
        frameAtkaRight[6] = monster1Atlas.findRegion("RightATTA7");
        frameAtkaRight[7] = monster1Atlas.findRegion("RightATTA8");
        animationAttaRight = new Animation(FRAME_DURATION_ATK1, frameAtkaRight);

        //右邊普通攻擊(B模式)
        TextureRegion[] frameAtkbRight = new TextureRegion[8];
        frameAtkbRight[0] = monster1Atlas.findRegion("RightATTB1");
        frameAtkbRight[1] = monster1Atlas.findRegion("RightATTB2");
        frameAtkbRight[2] = monster1Atlas.findRegion("RightATTB3");
        frameAtkbRight[3] = monster1Atlas.findRegion("RightATTB4");
        frameAtkbRight[4] = monster1Atlas.findRegion("RightATTB5");
        frameAtkbRight[5] = monster1Atlas.findRegion("RightATTB6");
        frameAtkbRight[6] = monster1Atlas.findRegion("RightATTB7");
        frameAtkbRight[7] = monster1Atlas.findRegion("RightATTB8");
        animationAttbRight = new Animation(FRAME_DURATION_ATK2, frameAtkbRight);

        //右邊普通攻擊(C模式)
        TextureRegion[] frameAtkcRight = new TextureRegion[7];
        frameAtkcRight[0] = monster1Atlas.findRegion("RightATTC1");
        frameAtkcRight[1] = monster1Atlas.findRegion("RightATTC2");
        frameAtkcRight[2] = monster1Atlas.findRegion("RightATTC3");
        frameAtkcRight[3] = monster1Atlas.findRegion("RightATTC4");
        frameAtkcRight[4] = monster1Atlas.findRegion("RightATTC5");
        frameAtkcRight[5] = monster1Atlas.findRegion("RightATTC6");
        frameAtkcRight[6] = monster1Atlas.findRegion("RightATTC7");
        animationAttcRight = new Animation(FRAME_DURATION_ATK3, frameAtkcRight);

        //右邊特殊技能1
        TextureRegion[] frameSatkaRight = new TextureRegion[11];
        frameSatkaRight[0] = monster1Atlas.findRegion("SatkRight01");
        frameSatkaRight[1] = monster1Atlas.findRegion("SatkRight02");
        frameSatkaRight[2] = monster1Atlas.findRegion("SatkRight03");
        frameSatkaRight[3] = monster1Atlas.findRegion("SatkRight04");
        frameSatkaRight[4] = monster1Atlas.findRegion("SatkRight05");
        frameSatkaRight[5] = monster1Atlas.findRegion("SatkRight06");
        frameSatkaRight[6] = monster1Atlas.findRegion("SatkRight07");
        frameSatkaRight[7] = monster1Atlas.findRegion("SatkRight08");
        frameSatkaRight[8] = monster1Atlas.findRegion("SatkRight09");
        frameSatkaRight[9] = monster1Atlas.findRegion("SatkRight10");
        frameSatkaRight[10] = monster1Atlas.findRegion("SatkRight11");
        animationSatkaRight = new Animation(FRAME_DURATION_SATK1, frameSatkaRight);

        //右邊受傷
        TextureRegion[] frameHurtRight = new TextureRegion[3];
        frameHurtRight[0] = monster1Atlas.findRegion("HurtRight");
        frameHurtRight[1] = monster1Atlas.findRegion("HurtRight");
        frameHurtRight[2] = monster1Atlas.findRegion("HurtRight");
        animationHurtRight = new Animation(FRAME_DURATION_HURT, frameHurtRight);

        //右邊死亡
        TextureRegion[] frameLoseRight = new TextureRegion[3];
        frameLoseRight[0] = monster1Atlas.findRegion("LoseRight1");
        frameLoseRight[1] = monster1Atlas.findRegion("LoseRight2");
        frameLoseRight[2] = monster1Atlas.findRegion("LoseRight3");
        animationLoseRight = new Animation(FRAME_DURATION_LOSE, frameLoseRight);

        //右邊死亡持續姿勢
        TextureRegion[] frameLoseKeepRight = new TextureRegion[3];
        frameLoseKeepRight[0] = monster1Atlas.findRegion("LoseRight3");
        frameLoseKeepRight[1] = monster1Atlas.findRegion("LoseRight3");
        frameLoseKeepRight[2] = monster1Atlas.findRegion("LoseRight3");
        animationLoseKeepRight = new Animation(FRAME_DURATION_LOSEKEEP, frameLoseKeepRight);


        //*****************************人物左邊設定*****************************
        //左邊跳躍
        jumpingLeftUp = monster1Atlas.findRegion("JumpingLeft1");
        jumpingLeftDown = monster1Atlas.findRegion("JumpingLeft2");

        //左邊站立
        TextureRegion[] frameStandingLeft = new TextureRegion[6];
        frameStandingLeft[0] = monster1Atlas.findRegion("StandingLeft1");
        frameStandingLeft[1] = monster1Atlas.findRegion("StandingLeft2");
        frameStandingLeft[2] = monster1Atlas.findRegion("StandingLeft3");
        frameStandingLeft[3] = monster1Atlas.findRegion("StandingLeft4");
        frameStandingLeft[4] = monster1Atlas.findRegion("StandingLeft5");
        frameStandingLeft[5] = monster1Atlas.findRegion("StandingLeft6");
        animationStandingLeft = new Animation(FRAME_DURATION_STAND, frameStandingLeft);

        //左邊跑步
        TextureRegion[] frameWalkingLeft = new TextureRegion[8];
        frameWalkingLeft[0] = monster1Atlas.findRegion("RunningLeft1");
        frameWalkingLeft[1] = monster1Atlas.findRegion("RunningLeft2");
        frameWalkingLeft[2] = monster1Atlas.findRegion("RunningLeft3");
        frameWalkingLeft[3] = monster1Atlas.findRegion("RunningLeft4");
        frameWalkingLeft[4] = monster1Atlas.findRegion("RunningLeft5");
        frameWalkingLeft[5] = monster1Atlas.findRegion("RunningLeft6");
        frameWalkingLeft[6] = monster1Atlas.findRegion("RunningLeft7");
        frameWalkingLeft[7] = monster1Atlas.findRegion("RunningLeft8");
        animationWalkingLeft = new Animation(FRAME_DURATION_RUN, frameWalkingLeft);

        //左邊普通攻擊(A模式)
        TextureRegion[] frameAtkaLeft = new TextureRegion[8];
        frameAtkaLeft[0] = monster1Atlas.findRegion("LeftATTA1");
        frameAtkaLeft[1] = monster1Atlas.findRegion("LeftATTA2");
        frameAtkaLeft[2] = monster1Atlas.findRegion("LeftATTA3");
        frameAtkaLeft[3] = monster1Atlas.findRegion("LeftATTA4");
        frameAtkaLeft[4] = monster1Atlas.findRegion("LeftATTA5");
        frameAtkaLeft[5] = monster1Atlas.findRegion("LeftATTA6");
        frameAtkaLeft[6] = monster1Atlas.findRegion("LeftATTA7");
        frameAtkaLeft[7] = monster1Atlas.findRegion("LeftATTA8");
        animationAttaLeft = new Animation(FRAME_DURATION_ATK1, frameAtkaLeft);

        //左邊普通攻擊(B模式)
        TextureRegion[] frameAtkbLeft = new TextureRegion[8];
        frameAtkbLeft[0] = monster1Atlas.findRegion("LeftATTB1");
        frameAtkbLeft[1] = monster1Atlas.findRegion("LeftATTB2");
        frameAtkbLeft[2] = monster1Atlas.findRegion("LeftATTB3");
        frameAtkbLeft[3] = monster1Atlas.findRegion("LeftATTB4");
        frameAtkbLeft[4] = monster1Atlas.findRegion("LeftATTB5");
        frameAtkbLeft[5] = monster1Atlas.findRegion("LeftATTB6");
        frameAtkbLeft[6] = monster1Atlas.findRegion("LeftATTB7");
        frameAtkbLeft[7] = monster1Atlas.findRegion("LeftATTB8");
        animationAttbLeft = new Animation(FRAME_DURATION_ATK2, frameAtkbLeft);

        //左邊普通攻擊(C模式)
        TextureRegion[] frameAtkcLeft = new TextureRegion[7];
        frameAtkcLeft[0] = monster1Atlas.findRegion("LeftATTC1");
        frameAtkcLeft[1] = monster1Atlas.findRegion("LeftATTC2");
        frameAtkcLeft[2] = monster1Atlas.findRegion("LeftATTC3");
        frameAtkcLeft[3] = monster1Atlas.findRegion("LeftATTC4");
        frameAtkcLeft[4] = monster1Atlas.findRegion("LeftATTC5");
        frameAtkcLeft[5] = monster1Atlas.findRegion("LeftATTC6");
        frameAtkcLeft[6] = monster1Atlas.findRegion("LeftATTC7");
        animationAttcLeft = new Animation(FRAME_DURATION_ATK3, frameAtkcLeft);

        //左邊特殊技能1
        TextureRegion[] frameSatkaLeft = new TextureRegion[11];
        frameSatkaLeft[0] = monster1Atlas.findRegion("SatkLeft01");
        frameSatkaLeft[1] = monster1Atlas.findRegion("SatkLeft02");
        frameSatkaLeft[2] = monster1Atlas.findRegion("SatkLeft03");
        frameSatkaLeft[3] = monster1Atlas.findRegion("SatkLeft04");
        frameSatkaLeft[4] = monster1Atlas.findRegion("SatkLeft05");
        frameSatkaLeft[5] = monster1Atlas.findRegion("SatkLeft06");
        frameSatkaLeft[6] = monster1Atlas.findRegion("SatkLeft07");
        frameSatkaLeft[7] = monster1Atlas.findRegion("SatkLeft08");
        frameSatkaLeft[8] = monster1Atlas.findRegion("SatkLeft09");
        frameSatkaLeft[9] = monster1Atlas.findRegion("SatkLeft10");
        frameSatkaLeft[10] = monster1Atlas.findRegion("SatkLeft11");
        animationSatkaLeft = new Animation(FRAME_DURATION_SATK1, frameSatkaLeft);

        //左邊受傷
        TextureRegion[] frameHurtLeft = new TextureRegion[3];
        frameHurtLeft[0] = monster1Atlas.findRegion("HurtLeft");
        frameHurtLeft[1] = monster1Atlas.findRegion("HurtLeft");
        frameHurtLeft[2] = monster1Atlas.findRegion("HurtLeft");
        animationHurtLeft = new Animation(FRAME_DURATION_HURT, frameHurtLeft);

        //左邊死亡
        TextureRegion[] frameLoseLeft = new TextureRegion[3];
        frameLoseLeft[0] = monster1Atlas.findRegion("LoseLeft1");
        frameLoseLeft[1] = monster1Atlas.findRegion("LoseLeft2");
        frameLoseLeft[2] = monster1Atlas.findRegion("LoseLeft3");
        animationLoseLeft = new Animation(FRAME_DURATION_LOSE, frameLoseLeft);

        //左邊死亡持續姿勢
        TextureRegion[] frameLoseKeepLeft = new TextureRegion[3];
        frameLoseKeepLeft[0] = monster1Atlas.findRegion("LoseLeft3");
        frameLoseKeepLeft[1] = monster1Atlas.findRegion("LoseLeft3");
        frameLoseKeepLeft[2] = monster1Atlas.findRegion("LoseLeft3");
        animationLoseKeepLeft = new Animation(FRAME_DURATION_LOSEKEEP, frameLoseKeepLeft);




        //勝利
        TextureRegion[] frameWin = new TextureRegion[8];
        frameWin[0] = monster1Atlas.findRegion("Win1");
        frameWin[1] = monster1Atlas.findRegion("Win2");
        frameWin[2] = monster1Atlas.findRegion("Win3");
        frameWin[3] = monster1Atlas.findRegion("Win4");
        frameWin[4] = monster1Atlas.findRegion("Win5");
        frameWin[5] = monster1Atlas.findRegion("Win6");
        frameWin[6] = monster1Atlas.findRegion("Win7");
        frameWin[7] = monster1Atlas.findRegion("Win8");
        animationWin = new Animation(FRAME_DURATION_WIN, frameWin);

        //勝利持續姿勢
        TextureRegion[] frameWinKeep = new TextureRegion[3];
        frameWinKeep[0] = monster1Atlas.findRegion("WinKeep1");
        frameWinKeep[1] = monster1Atlas.findRegion("WinKeep2");
        frameWinKeep[2] = monster1Atlas.findRegion("WinKeep3");
        animationWinKeep = new Animation(FRAME_DURATION_WINKEEP, frameWinKeep);

        collisionDao = new CollisionDao();

        setCurrentAction("Standing");
        setMonsterFrame(isFacingRight ? monster1Atlas.findRegion("StandingRight1") : monster1Atlas.findRegion("StandingLeft1"));
    }


    @Override
    public void callAI() {
        if(target!=null){
            serchResult = collisionDao.serchTarget(position.x, position.y, getMonsterFrame().getRegionWidth(), getMonsterFrame().getRegionHeight(),
                    target.position.x, target.position.y, serchRange);
            if(serchResult==1){
                setIsFacingRight(false);
                setCurrentAction("Atk");
            }else if(serchResult==2){
                setIsFacingRight(true);
                setCurrentAction("Atk");
            }else{
                setCurrentAction("Standing");
            }
        }else{
            setCurrentAction("Standing");
        }
    }

    @Override
    public void updateMonsterAction(float deltaTime, float animationTime, boolean isLeftTouchDown, boolean isRightTouchDown, boolean isLeftSprintJump, boolean isRightSprintJump) {

        callAI();

        if(getCurrentAction().equals("Hurt")){
            bossHurtRunTime+=Gdx.graphics.getDeltaTime();
            setMonsterFrame(isFacingRight ? getAnimationHurtRight().getKeyFrame(animationTime, true) : getAnimationHurtLeft().getKeyFrame(animationTime, true));
            setCurrentAnimation(isFacingRight ? getAnimationHurtRight() : getAnimationHurtLeft());

            if(isFacingRight){
                position.x = position.x-1;
            }else{
                position.x = position.x+1;
            }

            if(getCurrentAnimation().isAnimationFinished(bossHurtRunTime)){
                setCurrentAction("Standing");
                setCurrentAnimation(isFacingRight ? getAnimationStandingRight() : getAnimationStandingLeft());
                bossHurtRunTime = 0.0f;
            }
        } else if(getCurrentAction().equals("Atk")){
            serchResult = collisionDao.serchTarget(position.x, position.y, getMonsterFrame().getRegionWidth(), getMonsterFrame().getRegionHeight(),
                    target.position.x, target.position.y, 5);
            if(serchResult==1 || serchResult==2){
                satkaRunTime+=Gdx.graphics.getDeltaTime();
                setMonsterFrame(isFacingRight ? getAnimationSatkaRight().getKeyFrame(satkaRunTime, true) : getAnimationSatkaLeft().getKeyFrame(satkaRunTime, true));
                setCurrentAnimation(isFacingRight ? getAnimationSatkaRight() : getAnimationSatkaLeft());

                if(getCurrentAnimation().isAnimationFinished(satkaRunTime)) {//當前特殊技能1攻擊動畫結束
                    satkaRunTime = 0.0f;
                }
            }else{
                setMonsterFrame(isFacingRight ? getAnimationWalkingRight().getKeyFrame(animationTime, true) : getAnimationWalkingLeft().getKeyFrame(animationTime, true));
                setCurrentAnimation(isFacingRight ? getAnimationWalkingRight() : getAnimationWalkingLeft());

                if(isFacingRight){
                    velocity.x = 300;
                }else{
                    velocity.x = -300;
                }

                position.x = position.x + (velocity.x * deltaTime);
            }
        } else {
            setCurrentAction("Standing");
            setMonsterFrame(isFacingRight ? getAnimationStandingRight().getKeyFrame(animationTime, true) : getAnimationStandingLeft().getKeyFrame(animationTime, true));
        }
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

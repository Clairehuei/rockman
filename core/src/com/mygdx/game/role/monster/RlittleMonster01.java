package com.mygdx.game.role.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.dao.CollisionDao;

/**
 * Created by User on 2015/11/9.
 */
public class RlittleMonster01 extends RlittleMonster {

    private static final float FRAME_DURATION_STAND = 1.0f / 8.0f;//站立動畫的播放速度
    private static final float FRAME_DURATION_RUN = 1.0f / 15.0f;//跑步動畫的播放速度
    private static final float FRAME_DURATION_LOSE = 1.0f / 4.5f;//死亡動畫的播放速度
    private static final float FRAME_DURATION_LOSEKEEP = 1.0f / 2.5f;//死亡持續姿勢動畫的播放速度
    private static final float FRAME_DURATION_ATK = 1.0f / 14.0f;//攻擊的播放速度 = 每一格動作的播放間隔時間
    private static final float FRAME_DURATION_HURT = 1.0f / 15.0f;//受傷的播放速度 = 每一格動作的播放間隔時間

    private Animation animationStandingLeft;//站立(左)動畫
    private Animation animationStandingRight;//站立(右)動畫
    private Animation animationWalkingLeft;//跑步(左)動畫
    private Animation animationWalkingRight;//跑步(右)動畫
    private Animation animationAtkRight;//攻擊(右)動畫
    private Animation animationAtkLeft;//攻擊(左)動畫

    CollisionDao collisionDao;
    int serchResult = 0;
    private float satkaRunTime = 0.0f;//小怪攻擊動畫累積時間

    public RlittleMonster01(){
        init();
    }

    public void init(){
        isFacingRight = true;

        //讀取小怪圖檔資源
        monster1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/heroShana.pack"));

        //開始初始化小怪所有動作
        //*****************************小怪右邊設定*****************************
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
        animationAtkRight = new Animation(FRAME_DURATION_ATK, frameAtkaRight);

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


        //*****************************小怪左邊設定*****************************
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
        animationAtkLeft = new Animation(FRAME_DURATION_ATK, frameAtkaLeft);

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


        collisionDao = new CollisionDao();

        setCurrentAction("Standing");
        setMonsterFrame(isFacingRight ? monster1Atlas.findRegion("StandingRight1") : monster1Atlas.findRegion("StandingLeft1"));
    }

    @Override
    public void callAI() {

    }

    @Override
    public void updateMonsterAction(float deltaTime, boolean isLeftTouchDown, boolean isRightTouchDown, boolean isLeftSprintJump, boolean isRightSprintJump) {
        if(getCurrentAction().equals("Hurt")){
            updateAnimationTime();
            setMonsterFrame(isFacingRight ? getAnimationHurtRight().getKeyFrame(animationTime, true) : getAnimationHurtLeft().getKeyFrame(animationTime, true));
            setCurrentAnimation(isFacingRight ? getAnimationHurtRight() : getAnimationHurtLeft());

            if(isFacingRight){
                position.x = position.x-1;
            }else{
                position.x = position.x+1;
            }

            if(getCurrentAnimation().isAnimationFinished(animationTime)){
                setCurrentAction("Standing");
                setCurrentAnimation(isFacingRight ? getAnimationStandingRight() : getAnimationStandingLeft());
                animationTime = 0.0f;
            }
            beforeAction = "Hurt";
        } else if(getCurrentAction().equals("Atk")){
            updateAnimationTime();
            serchResult = collisionDao.serchTarget(position.x, position.y, getMonsterFrame().getRegionWidth(), getMonsterFrame().getRegionHeight(),
                    target.position.x, target.position.y, 5);
            if(serchResult==1 || serchResult==2){
                satkaRunTime+=Gdx.graphics.getDeltaTime();
                setMonsterFrame(isFacingRight ? getAnimationAtkRight().getKeyFrame(satkaRunTime, true) : getAnimationAtkLeft().getKeyFrame(satkaRunTime, true));
                setCurrentAnimation(isFacingRight ? getAnimationAtkRight() : getAnimationAtkLeft());

                if(getCurrentAnimation().isAnimationFinished(satkaRunTime)) {//當前攻擊動畫結束
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
            beforeAction = "Atk";
        } else if(getCurrentAction().equals("Lose")){
            updateAnimationTime();
            resultRunTime+= Gdx.graphics.getDeltaTime();
            setCurrentAction("Lose");
            setMonsterFrame(isFacingRight ? getAnimationLoseRight().getKeyFrame(resultRunTime, true) : getAnimationLoseLeft().getKeyFrame(resultRunTime, true));
            setCurrentAnimation(isFacingRight ? getAnimationLoseRight() : getAnimationLoseLeft());

            if(isFacingRight){
                position.x = position.x-2;
            }else{
                position.x = position.x+2;
            }

            if(getCurrentAnimation().isAnimationFinished(resultRunTime)){
                resultRunTime = 0.0f;
                setCurrentAction("LoseKeep");
            }
        } else if(getCurrentAction().equals("LoseKeep")){
            updateAnimationTime();
            resultRunTime+= Gdx.graphics.getDeltaTime();
            setCurrentAction("LoseKeep");
            setMonsterFrame(isFacingRight ? getAnimationLoseKeepRight().getKeyFrame(resultRunTime, true) : getAnimationLoseKeepLeft().getKeyFrame(resultRunTime, true));
            setCurrentAnimation(isFacingRight ? getAnimationLoseKeepRight() : getAnimationLoseKeepLeft());

            if(getCurrentAnimation().isAnimationFinished(resultRunTime)){
                resultRunTime = 0.0f;
                setCurrentAction("cleanMe");
                beKilled = true;
            }
        } else if(getCurrentAction().equals("Standing")) {
            updateAnimationTime();
            setMonsterFrame(isFacingRight ? getAnimationStandingRight().getKeyFrame(animationTime, true) : getAnimationStandingLeft().getKeyFrame(animationTime, true));
            beforeAction = "Standing";
        } else {
            Gdx.app.log("otherAction", "exception");
        }
    }



    //*********************************setter/getter***************************************

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
    public Animation getAnimationAtkRight() {
        return animationAtkRight;
    }
    public void setAnimationAtkRight(Animation animationAtkRight) {
        this.animationAtkRight = animationAtkRight;
    }
    public Animation getAnimationAtkLeft() {
        return animationAtkLeft;
    }
    public void setAnimationAtkLeft(Animation animationAtkLeft) {
        this.animationAtkLeft = animationAtkLeft;
    }
    public CollisionDao getCollisionDao() {
        return collisionDao;
    }
    public void setCollisionDao(CollisionDao collisionDao) {
        this.collisionDao = collisionDao;
    }
}

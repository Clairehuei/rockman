package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**遊戲腳色-夏娜
 * Created by 6193 on 2015/10/27.
 */
public class Rshana extends Rhero {

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

    //各別英雄專屬動畫
    private Animation animationAttaRight;//普通攻擊(A模式)(右)動畫
    private Animation animationAttaLeft;//普通攻擊(A模式)(左)動畫
    private Animation animationAttbRight;//普通攻擊(B模式)(右)動畫
    private Animation animationAttbLeft;//普通攻擊(B模式)(左)動畫
    private Animation animationAttcRight;//普通攻擊(C模式)(右)動畫
    private Animation animationAttcLeft;//普通攻擊(C模式)(左)動畫
    private Animation animationSatkaRight;//特殊技能1(右)動畫
    private Animation animationSatkaLeft;//特殊技能1(左)動畫


//    float animationTime = 0.0f;
    //所有動畫展示時間
    private float atkaRunTime = 0.0f;//英雄普通攻擊A模式動畫累積時間
    private float atkbRunTime = 0.0f;//英雄普通攻擊B模式動畫累積時間
    private float atkcRunTime = 0.0f;//英雄普通攻擊C模式動畫累積時間
    private float satkaRunTime = 0.0f;//英雄特殊技能1動畫累積時間


    int clickNumber = 0;//普通攻擊按鈕點擊次數

    //普通攻擊段數
    boolean canAtkLv1 = false;//普通攻擊第1階段
    boolean canAtkLv2 = false;//普通攻擊第2階段
    boolean canAtkLv3 = false;//普通攻擊第3階段

    Skin btnSkin;
    Button  btn_atk1;
    Button  btn_satk1;
    float v0 = 300.0f;

    public Rshana(){
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


        btnSkin = new Skin(Gdx.files.internal("data/btn.json"),new TextureAtlas("data/btn.pack"));
    }


    @Override
    public void setSpecialBtn(){
        super.setSpecialBtn();
        setBtnAtk1();
        setBtnSatk1();
    }


    private void setBtnAtk1(){
        btn_atk1 = new Button(btnSkin, "firebutton");
        btn_atk1.setPosition(Gdx.graphics.getWidth() - 138, 10);
        btn_atk1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                atk1();
                clickNumber = getTapCount();
                if (clickNumber == 1) {
                    canAtkLv1 = true;
                } else if (clickNumber == 2) {
                    canAtkLv2 = true;
                } else if (clickNumber == 3) {
                    canAtkLv3 = true;
                } else {
                    if (!canAtkLv3 && !canAtkLv2 && !canAtkLv1) {
                        canAtkLv1 = true;
                    } else if (canAtkLv1) {
                        canAtkLv2 = true;
                    } else if (canAtkLv2) {
                        canAtkLv3 = true;
                    }
                }
            }
        });
    }


    public void atk1(){
        setCurrentAction("Atking1");
    }


    public void setBtnSatk1(){
        btn_satk1 = new Button(btnSkin, "firebutton");
        btn_satk1.setPosition(Gdx.graphics.getWidth() - 138, 300);
        btn_satk1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                satk1();
            }
        });
    }


    public void satk1(){
        setCurrentAction("Satking1");
    }


    /**
     * 計算跳躍位置
     * @return
     */
    public float calJumpY(float deltaTime){
        float tempY;
        float g = -10;
        if(v0==0.0f){
            g = -15000;
        }
        //新版
        velocity.y = v0 + ((g)*deltaTime);
        tempY = position.y + (velocity.y * deltaTime);

        if(tempY > beforePosition.y) {//跳躍上升中
            if(tempY>=jumpY+110){//到達頂點
                v0 = 0.0f;
//                jumpY = 0.0f;
            }else{//未到達頂點
                v0 = 300.0f;
            }
        }else{//跳躍下降中

        }

        return tempY;
    }



    @Override
    public void updateHeroAction(float deltaTime, float animationTime, boolean isLeftTouchDown, boolean isRightTouchDown, boolean isLeftSprintJump, boolean isRightSprintJump){

        super.updateHeroAction(deltaTime, animationTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

        if(getCurrentAction().equals("Walking")){//跑步
            atkaRunTime = 0.0f;
            atkbRunTime = 0.0f;
            atkcRunTime = 0.0f;
            setHero1Frame(isFacingRight() ? getAnimationWalkingRight().getKeyFrame(animationTime, true) : getAnimationWalkingLeft().getKeyFrame(animationTime, true));

            collisionDao.collisionLeft = false;
            collisionDao.collisionRight = false;

            if (isFacingRight()){
                collisionDao.collisionRight(position.x, position.y);//偵測是否碰撞障礙物
                if (collisionDao.collisionRight){
                    velocity.x = 0;
                } else{
                    velocity.x = 300;
                }
            }
            if (!isFacingRight()){
                collisionDao.collisionLeft(position.x, position.y);
                if (collisionDao.collisionLeft){
                    velocity.x = 0;
                } else {
                    velocity.x = -300;
                }
            }
            position.x = position.x + (velocity.x * deltaTime);

            collisionDao.collisionBottom=false;
            v0 = collisionDao.collisionBottom(position.x, position.y, isFacingRight, v0);

            if (!collisionDao.collisionBottom){
                setCurrentAction("Jumping");
                velocity.y=-300;
            }
        }else if(getCurrentAction().equals("Jumping")){//跳躍
            atkaRunTime = 0.0f;
            atkbRunTime = 0.0f;
            atkcRunTime = 0.0f;
            if (isJumpAndWalk()){
                if(!isRightSprintJump && !isLeftSprintJump){//原地跳後 才給予方向動力
                    if(isRightTouchDown){//當在空中按住方向鍵時,給予x軸方向動力
                        velocity.x = 550;
                    }else if(isLeftTouchDown){
                        velocity.x = -550;
                    }
                }

                position.x = position.x + (velocity.x * deltaTime*0.3f);
            }else{
                velocity.x = 0;
            }

            collisionDao.collisionTop=false;
            collisionDao.collisionTop(position.x, position.y);

            if (collisionDao.collisionTop){
                velocity.y = -100;
                setCurrentAction("Jumping");
            }

            position.y = calJumpY(deltaTime);

            if(isFacingRight()){//向右跳躍
                if(position.y > beforePosition.y){//跳躍上升中
                    setHero1Frame(getJumpingRightUp());
                }else{//跳躍下降中
                    setHero1Frame(getJumpingRightDown());
                }
            }else{//向左跳躍
                if(position.y > beforePosition.y){//跳躍上升中
                    setHero1Frame(getJumpingLeftUp());
                }else{//跳躍下降中
                    setHero1Frame(getJumpingLeftDown());
                }
            }

            collisionDao.collisionBottom=false;
            v0 = collisionDao.collisionBottom(position.x, position.y, isFacingRight, v0);
            if (collisionDao.collisionBottom){//已著地
                velocity.y = 0;
                setCurrentAction("Standing");
                if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                    setCurrentAction("Walking");
                }
            }else{//尚未著地
                setCurrentAction("Jumping");
                if(isLeftTouchDown || isRightTouchDown){//當在空中按住方向鍵時,isJumpAndWalk設為true
                    setIsJumpAndWalk(true);
                }else{
                    setIsJumpAndWalk(false);
                }
            }
        }else if (getCurrentAction().equals("Atking1")){

            if(!canAtkLv3 && !canAtkLv2 && !canAtkLv1){
                if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                    setCurrentAction("Walking");
                    setCurrentAnimation(isFacingRight() ? getAnimationWalkingRight() : getAnimationWalkingLeft());
                }else{
                    setCurrentAction("Standing");
                    setCurrentAnimation(isFacingRight() ? getAnimationStandingRight() : getAnimationStandingLeft());
                }
            }

            if(canAtkLv3){
                if(!canAtkLv2){
                    atkcRunTime+=Gdx.graphics.getDeltaTime();
                    setHero1Frame(isFacingRight() ? getAnimationAttcRight().getKeyFrame(atkcRunTime, true) : getAnimationAttcLeft().getKeyFrame(atkcRunTime, true));
                    setCurrentAnimation(isFacingRight() ? getAnimationAttcRight() : getAnimationAttcLeft());

                    if(isFacingRight()){
                        position.x = position.x+1;
                    }else{
                        position.x = position.x-1;
                    }

                    if(getCurrentAnimation().isAnimationFinished(atkcRunTime)) {//當前第三階段攻擊動畫結束
                        canAtkLv3 = false;
                        atkcRunTime = 0.0f;

                        if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                            setCurrentAction("Walking");
                            setCurrentAnimation(isFacingRight() ? getAnimationWalkingRight() : getAnimationWalkingLeft());
                        }else{
                            setCurrentAction("Standing");
                            setCurrentAnimation(isFacingRight() ? getAnimationStandingRight() : getAnimationStandingLeft());
                        }

                    }
                }
            }

            if(canAtkLv2){
                if(!canAtkLv1){
                    atkbRunTime+=Gdx.graphics.getDeltaTime();
                    setHero1Frame(isFacingRight() ? getAnimationAttaRight().getKeyFrame(atkbRunTime, true) : getAnimationAttaLeft().getKeyFrame(atkbRunTime, true));
                    setCurrentAnimation(isFacingRight() ? getAnimationAttaRight() : getAnimationAttaLeft());
                    if(isFacingRight()){
                        position.x = position.x+1;
                    }else{
                        position.x = position.x-1;
                    }
                    if(getCurrentAnimation().isAnimationFinished(atkbRunTime)){//當前第二階段攻擊動畫結束
                        canAtkLv2 = false;
                        atkbRunTime = 0.0f;
                        if(!canAtkLv3){//沒有第三階段攻擊
                            //停止攻擊
                            if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                                setCurrentAction("Walking");
                                setCurrentAnimation(isFacingRight() ? getAnimationWalkingRight() : getAnimationWalkingLeft());
                            }else{
                                setCurrentAction("Standing");
                                setCurrentAnimation(isFacingRight() ? getAnimationStandingRight() : getAnimationStandingLeft());
                            }
                        }
                    }
                }
            }

            if(canAtkLv1){
                atkaRunTime+=Gdx.graphics.getDeltaTime();
                setHero1Frame(isFacingRight() ? getAnimationAttbRight().getKeyFrame(atkaRunTime, true) : getAnimationAttbLeft().getKeyFrame(atkaRunTime, true));
                setCurrentAnimation(isFacingRight() ? getAnimationAttbRight() : getAnimationAttbLeft());

                if(getCurrentAnimation().isAnimationFinished(atkaRunTime)){//當前第一階段攻擊動畫結束
                    canAtkLv1 = false;
                    atkaRunTime = 0.0f;
                    if(!canAtkLv2){//沒有第二階段攻擊
                        //停止攻擊
                        if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                            setCurrentAction("Walking");
                            setCurrentAnimation(isFacingRight() ? getAnimationWalkingRight() : getAnimationWalkingLeft());
                        }else{
                            setCurrentAction("Standing");
                            setCurrentAnimation(isFacingRight() ? getAnimationStandingRight() : getAnimationStandingLeft());
                        }
                    }
                }
            }

            collisionDao.collisionBottom=false;
            v0 = collisionDao.collisionBottom(position.x, position.y, isFacingRight, v0);
            if (!collisionDao.collisionBottom){
                setCurrentAction("Jumping");
                velocity.y=-300;
            }
        } else if (getCurrentAction().equals("Satking1")){

            satkaRunTime+=Gdx.graphics.getDeltaTime();
            setHero1Frame(isFacingRight() ? getAnimationSatkaRight().getKeyFrame(satkaRunTime, true) : getAnimationSatkaLeft().getKeyFrame(satkaRunTime, true));
            setCurrentAnimation(isFacingRight() ? getAnimationSatkaRight() : getAnimationSatkaLeft());

            if(getCurrentAnimation().isAnimationFinished(satkaRunTime)) {//當前特殊技能1攻擊動畫結束
                satkaRunTime = 0.0f;

                if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                    setCurrentAction("Walking");
                    setCurrentAnimation(isFacingRight() ? getAnimationWalkingRight() : getAnimationWalkingLeft());
                }else{
                    setCurrentAction("Standing");
                    setCurrentAnimation(isFacingRight() ? getAnimationStandingRight() : getAnimationStandingLeft());
                }

            }

            collisionDao.collisionBottom=false;
            v0 = collisionDao.collisionBottom(position.x, position.y, isFacingRight, v0);
            if (!collisionDao.collisionBottom){
                setCurrentAction("Jumping");
                velocity.y=-300;
            }
        } else{//其他列為站立
            setCurrentAction("Standing");
            setHero1Frame(isFacingRight() ? getAnimationStandingRight().getKeyFrame(animationTime, true) : getAnimationStandingLeft().getKeyFrame(animationTime, true));
        }

    }




    //*********************************setter/getter***************************************
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

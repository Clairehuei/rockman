package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**遊戲第3關
 * Created by 6193 on 2015/10/19.
 */
public class PlayScreen3 extends PlayBase {

    private Vector2 bossPosition = new Vector2();//魔王當前位置
    private float MaxVelocity = 800f;//人物空中下降終端速度

    //所有動畫展示時間
    private float atkaRunTime = 0.0f;//英雄普通攻擊A模式動畫累積時間
    private float atkbRunTime = 0.0f;//英雄普通攻擊B模式動畫累積時間
    private float atkcRunTime = 0.0f;//英雄普通攻擊C模式動畫累積時間
    private float satkaRunTime = 0.0f;//英雄特殊技能1動畫累積時間
    private float bossHurtRunTime = 0.0f;//魔王受傷動畫累積時間
    private float heroResultRunTime = 0.0f;//英雄戰鬥結果動畫累積時間
    private float bossResultRunTime = 0.0f;//魔王戰鬥結果動畫累積時間
    private float animationTime = 0.0f;

    //背景地圖
    private int[] background = new int[] {0};
    private int[] foreground = new int[] {1};

    //與背景地圖障礙物碰撞參數
    private float x1, y1, i1, j1;
    private int tempcount, tempcount1;


    //攝影機變數
    private OrthographicCamera camera;
    private Viewport viewport;


    private Sprite sprite;//英雄
    private Sprite spriteBoss;//BOSS
    private SpriteBatch batch;//繪製主要腳色
    private SpriteBatch controlBatch;//繪製玩家操控按鈕
    private SpriteBatch HUDBatch;//繪製玩家生命值相關資訊



    private float deltay = 0.0f;


    //子彈變數
    Texture bulletRight;
    TextureRegion bulletLeft;
    Bullet bullet, currentBullet;
    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();
    public int bulletVelocityX = 12;

    private BitmapFont font1;//英雄血量文字
    private BitmapFont font2;//BOSS血量文字

    private  float jumpY = 0.0f;
    private  float v0 = 300.0f;


    private boolean heroResultKeep = false;//是否開始撥放英雄戰鬥結果(持續)動畫
    private boolean bossResultKeep = false;//是否開始撥放BOSS戰鬥結果(持續)動畫


    BackgroundSound bgSound1;//背景音樂
    Sound gunSound;//子彈音效

    //碰撞判斷
    private boolean collisionLeft, collisionRight, collisionBottom, collisionTop;

    private TiledMapTileLayer.Cell cell;


    private Skin btnSkin;
    private Stage stage;

    //玩家介面操控按鈕 [方向鍵] + [技能鍵]
    private Button btn_right;
    private Button  btn_left;
    private Button  btn_jump;
    private Button  btn_home;
    private Button  btn_fire;
    private Button  btn_atk1;
    private Button  btn_satk1;

    private boolean isRightTouchDown = false;//判斷是否持續按住 [向右方向鍵]
    private boolean isLeftTouchDown = false;//判斷是否持續按住 [向左方向鍵]

    private boolean isLeftSprintJump = false;//判斷是否為衝刺跳躍[左大跳]
    private boolean isRightSprintJump = false;//判斷是否衝刺跳躍[右大跳]

    BossKing1 boss;//魔王
    private int HERO_SCORE = 100;
    private int BOSS_SCORE = 1000;

    //判斷是否碰撞到BOSS(無論BOSS面向哪邊).......P1 BOSS P2.......
    boolean touchBossP1 = false;
    boolean touchBossP2 = false;
    boolean hitOnBoss = false;//是否擊中BOSS

    String gameStatus = "Running";//Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗

    int clickNumber = 0;//普通攻擊按鈕點擊次數

    //普通攻擊段數
    boolean canAtkLv1 = false;//普通攻擊第1階段
    boolean canAtkLv2 = false;//普通攻擊第2階段
    boolean canAtkLv3 = false;//普通攻擊第3階段

    public PlayScreen3(Game game){
        this.game=game;
        init();
    }


    @Override
    public void init () {
//        startGame = true;
        super.init();
        Gdx.app.log("==PlayScreen3.init()===", "start init()");



        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();
        controlBatch = new SpriteBatch();

        hero = new Rshana();
        boss = new BossKing1();

        hero.position.x = screenWidth/2;//英雄初始位置X
        hero.position.y = 250;//英雄初始位置Y
        hero.velocity.x = 300;//英雄X軸初始速度
        hero.velocity.y = -150;//英雄Y軸初始速度

        bossPosition.x = 800;
        bossPosition.y = 86;


        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);//設置鏡頭大小
        camera.setToOrtho(false, screenWidth, screenHeight);//y軸向上
        camera.update();

//        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMap = new TmxMapLoader().load("map/newmap2.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("foreground");

        stageWidth = foregroundLayer.getWidth()*foregroundLayer.getTileWidth();
        System.out.println("場景寬度:"+stageWidth);

        bulletRight = new Texture("data/bullet.png");
        bulletLeft = new TextureRegion(bulletRight);
        bulletLeft.flip(true, false);


        //設定背景音樂
        bgSound1 = new BackgroundSound();

        //設定子彈音效
        gunSound = Gdx.audio.newSound(Gdx.files.internal("sound/gun1.ogg"));

        font1 = new BitmapFont();
        font1.setColor(Color.YELLOW);

        font2 = new BitmapFont();
        font2.setColor(Color.RED);

        stage = new Stage();

        btnSkin = new Skin(Gdx.files.internal("data/btn.json"),new TextureAtlas("data/btn.pack"));

        //設定功能按鈕
        setBtnDirection();
        setBtnFire();
        setBtnJump();
        setBtnHome();
        setBtnAtk1();
        setBtnSatk1();

        //場景加入演員
        stage.addActor(btn_right);
        stage.addActor(btn_left);
        stage.addActor(btn_fire);
        stage.addActor(btn_jump);
        stage.addActor(btn_home);
        stage.addActor(btn_atk1);
        stage.addActor(btn_satk1);

        Gdx.input.setInputProcessor(stage);//將場景加入輸入(觸控)偵測
    }


    //***************************************************************************************************************

    /**
     * 方向鍵設定
     */
    private void setBtnDirection(){
        btn_right = new Button(btnSkin, "rightbutton");
        btn_right.setPosition(Gdx.graphics.getWidth()/8+50, Gdx.graphics.getHeight() / 7);
        btn_right.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                isRightTouchDown = true;
                if (hero.getCurrentAction().equals("Standing")){
                    hero.setCurrentAction("Walking");
                } else if (hero.getCurrentAction().equals("Jumping")){
                    hero.setIsJumpAndWalk(true);
                }
                hero.setIsFacingRight(true);
                if (hero.velocity.x < 0){
                    hero.velocity.x = -hero.velocity.x;
                }

                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                isRightTouchDown = false;
                if (hero.getCurrentAction().equals("Walking")){
                    hero.setCurrentAction("Standing");
                }
            }
        });

        btn_left = new Button(btnSkin, "leftbutton");
        btn_left.setPosition(10, Gdx.graphics.getHeight() / 7);
        btn_left.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isLeftTouchDown = true;
                if (hero.getCurrentAction().equals("Standing")) {
                    hero.setCurrentAction("Walking");
                } else if (hero.getCurrentAction().equals("Jumping")) {
                    hero.setIsJumpAndWalk(true);
                }

                hero.setIsFacingRight(false);
                if (hero.velocity.x > 0) {
                    hero.velocity.x = -hero.velocity.x;
                }

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isLeftTouchDown = false;
                if (hero.getCurrentAction().equals("Walking")) {
                    hero.setCurrentAction("Standing");
                }
            }
        });
    }


    /**
     * 跳躍鍵設定
     */
    private void setBtnJump(){
        btn_jump = new Button(btnSkin, "jumpbutton");
        btn_jump.setPosition(Gdx.graphics.getWidth()*3/4 , Gdx.graphics.getHeight()/7 );
        btn_jump.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jump();
            }
        });
    }


    /**
     * 技能鍵設定
     */
    private void setBtnFire(){
        btn_fire = new Button(btnSkin, "firebutton");
        btn_fire.setPosition(Gdx.graphics.getWidth()-138, Gdx.graphics.getHeight() / 5);
        btn_fire.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFire();
            }
        });
    }


    private void setBtnAtk1(){
        btn_atk1 = new Button(btnSkin, "firebutton");
        btn_atk1.setPosition(Gdx.graphics.getWidth()-138, 10);
        btn_atk1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                atk1();
                clickNumber = getTapCount();
                if(clickNumber==1){
                    canAtkLv1 = true;
                }else if(clickNumber==2){
                    canAtkLv2 = true;
                }else if(clickNumber==3){
                    canAtkLv3 = true;
                }
                else{
                    if(!canAtkLv3 && !canAtkLv2 && !canAtkLv1){
                        canAtkLv1 = true;
                    }else if(canAtkLv1){
                        canAtkLv2 = true;
                    }else if(canAtkLv2){
                        canAtkLv3 = true;
                    }
                }
            }
        });
    }


    private void setBtnSatk1(){
        btn_satk1 = new Button(btnSkin, "firebutton");
        btn_satk1.setPosition(Gdx.graphics.getWidth()-138, 300);
        btn_satk1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                satk1();
            }
        });
    }


    /**
     * 返回城鎮鍵設定
     */
    private void setBtnHome(){
        btn_home = new Button(btnSkin, "jumpbutton");
        btn_home.setPosition(0 , 610 );
        btn_home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backHome();
            }
        });
    }
    //***************************************************************************************************************



    //********************************************************************************
    /**
     * 判斷是否為障礙物
     * @param x
     * @param y
     * @return
     */
    private	boolean isCellBlocked( float x, float y ){
        cell = foregroundLayer.getCell( (int)x, (int)y);
        if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")){
            return  true;
        }
        return false;
    }


    /**
     * 判斷英雄本體是否接觸魔王
     * @param x
     * @param y
     * @return
     */
    private	boolean isTouchBoss(float x, float y){
        if(x+hero.getHero1Frame().getRegionWidth()>=bossPosition.x-5 && x+hero.getHero1Frame().getRegionWidth()<=bossPosition.x){
            touchBossP1 = true;
            touchBossP2 = false;
            return true;
        }else if(x-5<=bossPosition.x+boss.getHero1Frame().getRegionWidth() && x+hero.getHero1Frame().getRegionWidth()>=bossPosition.x+boss.getHero1Frame().getRegionWidth()){
            touchBossP1 = false;
            touchBossP2 = true;
            return true;
        }else{
            touchBossP1 = false;
            touchBossP2 = false;
        }

        return false;
    }


    /**
     * 判斷是否攻擊到魔王
     * @param x
     * @param y
     * @return
     */
    private	boolean isHitBoss(float x, float y, float w, float h){
        if(x+w>=bossPosition.x-5 && x+w<=bossPosition.x){
            if( (y+h > bossPosition.y+boss.getHero1Frame().getRegionHeight() && y< bossPosition.y+boss.getHero1Frame().getRegionHeight())
                    || (y+h<=bossPosition.y+boss.getHero1Frame().getRegionHeight() && y>=bossPosition.y)
                    || (y+h>bossPosition.y && y<bossPosition.y)){
                hitOnBoss = true;
                return true;
            }
        }else if(x-5<=bossPosition.x+boss.getHero1Frame().getRegionWidth() && x+w>=bossPosition.x+boss.getHero1Frame().getRegionWidth()){
            if( (y+h > bossPosition.y+boss.getHero1Frame().getRegionHeight() && y< bossPosition.y+boss.getHero1Frame().getRegionHeight())
                    || (y+h<=bossPosition.y+boss.getHero1Frame().getRegionHeight() && y>=bossPosition.y)
                    || (y+h>bossPosition.y && y<bossPosition.y)){
                hitOnBoss = true;
                return true;
            }
        }else{
            hitOnBoss = false;
        }

        return false;
    }


    /**
     * 判斷普通攻擊是否攻擊到魔王
     * @param x
     * @param y
     * @return
     */
    private	boolean isHitBoss2(float x, float y, float w, float h){
        if(x+w>=bossPosition.x-10 && x<=bossPosition.x){
            hitOnBoss = true;
            return true;
        }else if(x-10<=bossPosition.x+boss.getHero1Frame().getRegionWidth() && x>bossPosition.x){
            hitOnBoss = true;
            return true;
        }else{
            hitOnBoss = false;
        }

        return false;
    }


    //********************************************************************************
//  Left Collision Detection
    public	boolean collisionLeft() {
        tempcount=0;
        for (int i = 32 ; i<=64; i +=32){
            i1 = new Float(i);
            x1 = (hero.position.x-5)/32;
            y1 = ((hero.position.y+i1)/32);
            if (isCellBlocked(x1, y1)){
                tempcount = tempcount+1;
            }
        }
        if (tempcount > 0)
            collisionLeft = true;
        return false;
    }
    //********************************************************************************
// 	Right Collision Detection
    public	boolean collisionRight() {
        tempcount=0;
        for (int i = 32 ; i<=64; i +=32){
            i1 = new Float(i);
            x1 = (hero.position.x+64)/32;
            y1 = ((hero.position.y+i1)/32);
            if (isCellBlocked(x1, y1)){
                tempcount = tempcount+1;
            }
        }
        if (tempcount > 0)
            collisionRight = true;
        return false;
    }
    //********************************************************************************
// 	Bottom Collision Detection
    public	boolean collisionBottom() {
        tempcount1=0;
        collisionBottom = false;

        if (!hero.isFacingRight()){
            for (int j = 32 ; j<=64; j +=32){
                j1 = new Float(j);
                x1 = (hero.position.x+j1)/32;
                y1 = (hero.position.y/32);
                if (isCellBlocked(x1, y1)){
                    tempcount1 = tempcount1+1;
                }
                if (tempcount1 > 0){
                    collisionBottom = true;
                    v0 = 300.0f;
                }

            }
        }
        else {
            for (int j = 0 ; j<=32; j +=32){
                j1 = new Float(j);
                x1 = (hero.position.x+j1)/32;
                y1 = (hero.position.y/32);
                if (isCellBlocked(x1, y1)){
                    tempcount1 = tempcount1+1;
                }
                if (tempcount1 > 0){
                    collisionBottom = true;
                    v0 = 300.0f;
                }

            }
        }
        return false;
    }
    //********************************************************************************
//  Top Collision Detection
    public	boolean collisionTop() {
        tempcount1=0;
        collisionTop = false;
        x1 = (hero.position.x+32)/32;
        y1 = (hero.position.y+64)/32;
        if (isCellBlocked(x1, y1)){
            tempcount1 = tempcount1+1;
        }
        if (tempcount1 > 0)
            collisionTop = true;
        return false;
    }
    //********************************************************************************


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        super.render(delta);

        animationTime += Gdx.graphics.getDeltaTime();

//		Set camera to batch and update camera
        batch.setProjectionMatrix(camera.combined);
        camera.update();


        //Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗
        if(gameStatus.equals("Running")){
//		Set hero1's Frame & X, Y Positions
            hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationStandingRight().getKeyFrame(animationTime, true):hero.getAnimationStandingLeft().getKeyFrame(animationTime, true));

            boss.setHero1Frame(boss.isFacingRight()?hero.getAnimationStandingRight().getKeyFrame(animationTime, true):boss.getAnimationStandingLeft().getKeyFrame(animationTime, true));

            if (hero.getCurrentAction().equals("Walking")){
                atkaRunTime = 0.0f;
                atkbRunTime = 0.0f;
                atkcRunTime = 0.0f;
                hero.setHero1Frame(hero.isFacingRight() ? hero.getAnimationWalkingRight().getKeyFrame(animationTime, true) : hero.getAnimationWalkingLeft().getKeyFrame(animationTime, true));

                collisionLeft = false;
                collisionRight = false;
                if (hero.isFacingRight()){
                    collisionRight();//偵測是否碰撞障礙物
                    isTouchBoss(hero.position.x, hero.position.y);
                    if (collisionRight){
                        hero.velocity.x = 0;
                    } else if(touchBossP1){
                        hero.velocity.x = 0;
                    } else if(touchBossP2){
                        hero.velocity.x = 300;
                    } else{
                        hero.velocity.x = 300;
                    }
                }
                if (!hero.isFacingRight()){
                    collisionLeft();
                    isTouchBoss(hero.position.x, hero.position.y);
                    if (collisionLeft){
                        hero.velocity.x = 0;
                    } else if(touchBossP1){
                        hero.velocity.x = -300;
                    } else if(touchBossP2){
                        hero.velocity.x = 0;
                    } else {
                        hero.velocity.x = -300;
                    }
                }
                hero.position.x = hero.position.x + (hero.velocity.x * deltaTime);

                collisionBottom=false;
                collisionBottom();
                if (!collisionBottom){
                    hero.setCurrentAction("Jumping");
                    hero.velocity.y=-300;
                }
//********************************************************************************
            }else if (hero.getCurrentAction().equals("Jumping")){
                atkaRunTime = 0.0f;
                atkbRunTime = 0.0f;
                atkcRunTime = 0.0f;
                if (hero.isJumpAndWalk()){
                    if(!isRightSprintJump && !isLeftSprintJump){//原地跳後 才給予方向動力
                        if(isRightTouchDown){//當在空中按住方向鍵時,給予x軸方向動力
                            hero.velocity.x = 550;
                        }else if(isLeftTouchDown){
                            hero.velocity.x = -550;
                        }
                    }

                    hero.position.x = hero.position.x + (hero.velocity.x * deltaTime*0.3f);
                }else{
                    hero.velocity.x = 0;
                }

                collisionTop=false;
                collisionTop();
                if (collisionTop){
                    hero.velocity.y = -100;
                    hero.setCurrentAction("Jumping");
                }

//            velocity.y -= MaxVelocity * deltaTime*1.45f;
////	   Clamp velocity (Terminal Velocity)終端速度
//            if (velocity.y < -MaxVelocity){
//                velocity.y = -MaxVelocity;
//            }
//            position.y = position.y + (velocity.y * deltaTime);

                hero.position.y = calJumpY();

                if(hero.isFacingRight()){//向右跳躍
                    if(hero.position.y > hero.beforePosition.y){//跳躍上升中
                        hero.setHero1Frame(hero.getJumpingRightUp());
                    }else{//跳躍下降中
                        hero.setHero1Frame(hero.getJumpingRightDown());
                    }
                }else{//向左跳躍
                    if(hero.position.y > hero.beforePosition.y){//跳躍上升中
                        hero.setHero1Frame(hero.getJumpingLeftUp());
                    }else{//跳躍下降中
                        hero.setHero1Frame(hero.getJumpingLeftDown());
                    }
                }

                collisionBottom=false;
                collisionBottom();
                if (collisionBottom){//已著地
                    hero.velocity.y = 0;
                    hero.setCurrentAction("Standing");
                    if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                        hero.setCurrentAction("Walking");
                    }
                }else{//尚未著地
                    hero.setCurrentAction("Jumping");
                    if(isLeftTouchDown || isRightTouchDown){//當在空中按住方向鍵時,isJumpAndWalk設為true
                        hero.setIsJumpAndWalk(true);
                    }else{
                        hero.setIsJumpAndWalk(false);
                    }
                }
            }else if (hero.getCurrentAction().equals("Atking1")){

                if(!canAtkLv3 && !canAtkLv2 && !canAtkLv1){
                    if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                        hero.setCurrentAction("Walking");
                        hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationWalkingRight() : hero.getAnimationWalkingLeft());
                    }else{
                        hero.setCurrentAction("Standing");
                        hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationStandingRight() : hero.getAnimationStandingLeft());
                    }
                }

                if(canAtkLv3){
                    if(!canAtkLv2){
                        atkcRunTime+=Gdx.graphics.getDeltaTime();
                        hero.setHero1Frame(hero.isFacingRight() ? ((Rshana) hero).getAnimationAttcRight().getKeyFrame(atkcRunTime, true) : ((Rshana) hero).getAnimationAttcLeft().getKeyFrame(atkcRunTime, true));
                        hero.setCurrentAnimation(hero.isFacingRight() ? ((Rshana)hero).getAnimationAttcRight() : ((Rshana)hero).getAnimationAttcLeft());

                        if(hero.isFacingRight()){
                            hero.position.x = hero.position.x+1;
                        }else{
                            hero.position.x = hero.position.x-1;
                        }

                        if(hero.getCurrentAnimation().isAnimationFinished(atkcRunTime)) {//當前第三階段攻擊動畫結束
                            canAtkLv3 = false;
                            atkcRunTime = 0.0f;

                            if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                                hero.setCurrentAction("Walking");
                                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationWalkingRight() : hero.getAnimationWalkingLeft());
                            }else{
                                hero.setCurrentAction("Standing");
                                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationStandingRight() : hero.getAnimationStandingLeft());
                            }

                        }
                    }
                }

                if(canAtkLv2){
                    if(!canAtkLv1){
                        atkbRunTime+=Gdx.graphics.getDeltaTime();
                        hero.setHero1Frame(hero.isFacingRight() ? ((Rshana)hero).getAnimationAttaRight().getKeyFrame(atkbRunTime, true) : ((Rshana)hero).getAnimationAttaLeft().getKeyFrame(atkbRunTime, true));
                        hero.setCurrentAnimation(hero.isFacingRight() ? ((Rshana)hero).getAnimationAttaRight() : ((Rshana)hero).getAnimationAttaLeft());
                        if(hero.isFacingRight()){
                            hero.position.x = hero.position.x+1;
                        }else{
                            hero.position.x = hero.position.x-1;
                        }
                        if(hero.getCurrentAnimation().isAnimationFinished(atkbRunTime)){//當前第二階段攻擊動畫結束
                            canAtkLv2 = false;
                            atkbRunTime = 0.0f;
                            if(!canAtkLv3){//沒有第三階段攻擊
                                //停止攻擊
                                if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                                    hero.setCurrentAction("Walking");
                                    hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationWalkingRight() : hero.getAnimationWalkingLeft());
                                }else{
                                    hero.setCurrentAction("Standing");
                                    hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationStandingRight() : hero.getAnimationStandingLeft());
                                }
                            }
                        }
                    }
                }

                if(canAtkLv1){
                    atkaRunTime+=Gdx.graphics.getDeltaTime();
                    hero.setHero1Frame(hero.isFacingRight() ? ((Rshana)hero).getAnimationAttbRight().getKeyFrame(atkaRunTime, true) : ((Rshana)hero).getAnimationAttbLeft().getKeyFrame(atkaRunTime, true));
                    hero.setCurrentAnimation(hero.isFacingRight() ? ((Rshana)hero).getAnimationAttbRight() : ((Rshana)hero).getAnimationAttbLeft());

                    if(hero.getCurrentAnimation().isAnimationFinished(atkaRunTime)){//當前第一階段攻擊動畫結束
                        canAtkLv1 = false;
                        atkaRunTime = 0.0f;
                        if(!canAtkLv2){//沒有第二階段攻擊
                            //停止攻擊
                            if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                                hero.setCurrentAction("Walking");
                                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationWalkingRight() : hero.getAnimationWalkingLeft());
                            }else{
                                hero.setCurrentAction("Standing");
                                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationStandingRight() : hero.getAnimationStandingLeft());
                            }
                        }
                    }
                }

                collisionBottom=false;
                collisionBottom();
                if (!collisionBottom){
                    hero.setCurrentAction("Jumping");
                    hero.velocity.y=-300;
                }

                //判斷是否擊中目標
                isHitBoss2(hero.position.x, hero.position.y, hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
                if (hitOnBoss){//擊中
                    boss.setCurrentAction("Hurt");
                    BOSS_SCORE = BOSS_SCORE-1;
                }

//********************************************************************************
            }else if (hero.getCurrentAction().equals("Satking1")){

                satkaRunTime+=Gdx.graphics.getDeltaTime();
                hero.setHero1Frame(hero.isFacingRight() ? ((Rshana)hero).getAnimationSatkaRight().getKeyFrame(satkaRunTime, true) : ((Rshana)hero).getAnimationSatkaLeft().getKeyFrame(satkaRunTime, true));
                hero.setCurrentAnimation(hero.isFacingRight() ? ((Rshana)hero).getAnimationSatkaRight() : ((Rshana)hero).getAnimationSatkaLeft());

                if(hero.getCurrentAnimation().isAnimationFinished(satkaRunTime)) {//當前特殊技能1攻擊動畫結束
                    satkaRunTime = 0.0f;

                    if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                        hero.setCurrentAction("Walking");
                        hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationWalkingRight() : hero.getAnimationWalkingLeft());
                    }else{
                        hero.setCurrentAction("Standing");
                        hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationStandingRight() : hero.getAnimationStandingLeft());
                    }

                }

                collisionBottom=false;
                collisionBottom();
                if (!collisionBottom){
                    hero.setCurrentAction("Jumping");
                    hero.velocity.y=-300;
                }

                //判斷是否擊中目標
                isHitBoss2(hero.position.x, hero.position.y, hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
                if (hitOnBoss){//擊中
                    boss.setCurrentAction("Hurt");
                    BOSS_SCORE = BOSS_SCORE-1;
                }

//********************************************************************************
            }

//********************************************************************************


//	    Store Spritesheet to sprite
            if(sprite!=null){
                sprite.setRegion(hero.getHero1Frame());
                sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            }else{
                sprite = new Sprite(hero.getHero1Frame());
            }




            //將魔王的方向面對英雄
            if(hero.position.x<=bossPosition.x){
                boss.setIsFacingRight(false);
            }else{
                boss.setIsFacingRight(true);
            }



            if(boss.getCurrentAction().equals("Standing")){
                boss.setHero1Frame(boss.isFacingRight()?hero.getAnimationStandingRight().getKeyFrame(animationTime, true):boss.getAnimationStandingLeft().getKeyFrame(animationTime, true));
            } else if(boss.getCurrentAction().equals("Hurt")){
                bossHurtRunTime+=Gdx.graphics.getDeltaTime();
                boss.setHero1Frame(boss.isFacingRight() ? boss.getAnimationHurtRight().getKeyFrame(animationTime, true) : boss.getAnimationHurtLeft().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.isFacingRight() ? boss.getAnimationHurtRight() : boss.getAnimationHurtLeft());

                if(boss.isFacingRight()){
                    bossPosition.x = bossPosition.x-1;
                }else{
                    bossPosition.x = bossPosition.x+1;
                }


                if(boss.getCurrentAnimation().isAnimationFinished(bossHurtRunTime)){

                    boss.setCurrentAction("Standing");
                    boss.setCurrentAnimation(boss.isFacingRight() ? boss.getAnimationStandingRight() : boss.getAnimationStandingLeft());

                    bossHurtRunTime = 0.0f;
                }
            }
//
//            //step1. 亂數選擇魔王的行為
//
//            //step2. 根據魔王的行為進行設定相關動作
//            if(boss.getCurrentAction().equals("Standing")){//發呆
//
//            }else if(boss.getCurrentAction().equals("Running")){//追蹤玩家
//
//            }else if(boss.getCurrentAction().equals("Jump")){//跳躍
//
//            }





            if(spriteBoss!=null){
                spriteBoss.setRegion(boss.getHero1Frame());
                spriteBoss.setSize(boss.getHero1Frame().getRegionWidth(), boss.getHero1Frame().getRegionHeight());
                spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);
            }else{
                spriteBoss = new Sprite(boss.getHero1Frame());
            }


            //人物著地後繪圖位置修正量
            if(collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(bossPosition.x, bossPosition.y);//設定魔王位置
            spriteBoss.setScale(1.8f);//設定魔王大小

            tiledMapRenderer.setView(camera);

            //調整鏡頭位置
            if(hero.position.x>=screenWidth/2 && hero.position.x<=(stageWidth-(screenWidth/2))){//中間區域
                //跟隨英雄
                camera.position.x = hero.position.x;
            }else if(hero.position.x<screenWidth/2){//最左邊
                camera.position.x = screenWidth/2;
            }else if(hero.position.x>(stageWidth-(screenWidth/2))){//最右邊
                camera.position.x = stageWidth-(screenWidth/2);
            }

            tiledMapRenderer.render(background);
            tiledMapRenderer.render(foreground);

//	    Display on Screen
            batch.begin();
            spriteBoss.draw(batch);
            sprite.draw(batch);


            //********************************************************************************
            int bulletCounter = 0;
            while (bulletCounter < bulletManager.size())
            {
                currentBullet = bulletManager.get(bulletCounter);
                currentBullet.update();

                //判斷是否擊中目標
                isHitBoss(currentBullet.TempbulletPosition.x, currentBullet.TempbulletPosition.y, bulletRight.getWidth(), bulletRight.getHeight());
                if(hitOnBoss){//擊中
                    BOSS_SCORE = BOSS_SCORE-1;
                    bulletManager.remove(bulletCounter);
                    if(bulletManager.size() > 0){
                        bulletCounter--;
                    }
                }

                if(currentBullet.TempbulletPosition.x> -100 &&
                        currentBullet.TempbulletPosition.x < (screenWidth*2 + 100)){

                    if (hero.isFacingRight()){
                        batch.draw(bulletRight, currentBullet.TempbulletPosition.x+32,
                                currentBullet.TempbulletPosition.y+16);//deltay
                    }
                    else
                        batch.draw(bulletLeft, currentBullet.TempbulletPosition.x,
                                currentBullet.TempbulletPosition.y+16);//deltay
                }
                else{
                    bulletManager.remove(bulletCounter);
                    if(bulletManager.size() > 0){
                        bulletCounter--;
                    }

                }
                bulletCounter++;
            }


//********************************************************************************
            batch.end();

            //設定英雄前一個位置
            hero.beforePosition.x = hero.position.x;
            hero.beforePosition.y = hero.position.y;

            if(BOSS_SCORE<=0){
                gameStatus = "Win";
            }else if(HERO_SCORE<=0){
                gameStatus = "Lose";
            }

        }else if(gameStatus.equals("Sotp")){

        }else if(gameStatus.equals("Win")){

            if(!heroResultKeep){//第一次顯示英雄戰鬥結果
                heroResultRunTime+=Gdx.graphics.getDeltaTime();
                hero.setCurrentAction("Win");
                hero.setHero1Frame(hero.getAnimationWin().getKeyFrame(animationTime, true));
                hero.setCurrentAnimation(hero.getAnimationWin());
                if(hero.getCurrentAnimation().isAnimationFinished(heroResultRunTime)){
                    heroResultRunTime = 0.0f;
                    heroResultKeep = true;
                }
            }else{//持續顯示英雄戰鬥結果
                hero.setCurrentAction("WinKeep");
                hero.setHero1Frame(hero.getAnimationWinKeep().getKeyFrame(animationTime, true));
                hero.setCurrentAnimation(hero.getAnimationWinKeep());
            }

            sprite.setRegion(hero.getHero1Frame());
            sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

            //-------------------------------------------------------------------------------------------------------


            if(!bossResultKeep){//第一次顯示魔王戰鬥結果
                bossResultRunTime+=Gdx.graphics.getDeltaTime();
                boss.setCurrentAction("Lose");
                boss.setHero1Frame(boss.isFacingRight() ? boss.getAnimationLoseRight().getKeyFrame(animationTime, true) : boss.getAnimationLoseLeft().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.isFacingRight() ? boss.getAnimationLoseRight() : boss.getAnimationLoseLeft());

                if(boss.isFacingRight()){
                    bossPosition.x = bossPosition.x-2;
                }else{
                    bossPosition.x = bossPosition.x+2;
                }

                if(boss.getCurrentAnimation().isAnimationFinished(bossResultRunTime)){
                    bossResultRunTime = 0.0f;
                    bossResultKeep = true;
                }
            }else{//持續顯示魔王戰鬥結果
                boss.setCurrentAction("LoseKeep");
                boss.setHero1Frame(boss.isFacingRight() ? boss.getAnimationLoseKeepRight().getKeyFrame(animationTime, true) : boss.getAnimationLoseKeepLeft().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.isFacingRight()?boss.getAnimationLoseKeepRight():boss.getAnimationLoseKeepLeft());
            }



            spriteBoss.setRegion(boss.getHero1Frame());
            spriteBoss.setSize(boss.getHero1Frame().getRegionWidth(), boss.getHero1Frame().getRegionHeight());
            spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);

            //人物著地後繪圖位置修正量
            if(collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(bossPosition.x, bossPosition.y);//設定魔王位置
            spriteBoss.setScale(1.8f);//設定魔王大小

            tiledMapRenderer.setView(camera);

            //調整鏡頭位置
            if(hero.position.x>=screenWidth/2 && hero.position.x<=(stageWidth-(screenWidth/2))){//中間區域
                //跟隨英雄
                camera.position.x = hero.position.x;
            }else if(hero.position.x<screenWidth/2){//最左邊
                camera.position.x = screenWidth/2;
            }else if(hero.position.x>(stageWidth-(screenWidth/2))){//最右邊
                camera.position.x = stageWidth-(screenWidth/2);
            }

            tiledMapRenderer.render(background);
            tiledMapRenderer.render(foreground);
//	    Display on Screen
            batch.begin();
            spriteBoss.draw(batch);
            sprite.draw(batch);
            batch.end();

        }else if(gameStatus.equals("Lose")){

            if(!heroResultKeep){//第一次顯示英雄戰鬥結果
                heroResultRunTime+=Gdx.graphics.getDeltaTime();
                hero.setCurrentAction("Lose");
                hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationLoseRight().getKeyFrame(animationTime, true):hero.getAnimationLoseLeft().getKeyFrame(animationTime, true));
                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationLoseRight() : hero.getAnimationLoseLeft());
                if(hero.isFacingRight()){
                    hero.position.x = hero.position.x-2;
                }else{
                    hero.position.x = hero.position.x+2;
                }
                if(hero.getCurrentAnimation().isAnimationFinished(heroResultRunTime)){
                    heroResultRunTime = 0.0f;
                    heroResultKeep = true;
                }
            }else{//持續顯示英雄戰鬥結果
                hero.setCurrentAction("LoseKeep");
                hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationLoseKeepRight().getKeyFrame(animationTime, true):hero.getAnimationLoseKeepLeft().getKeyFrame(animationTime, true));
                hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationLoseKeepRight() : hero.getAnimationLoseKeepLeft());
            }

            sprite.setRegion(hero.getHero1Frame());
            sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

            //--------------------------------------------------------------------------------------------------------------------

            if(!bossResultKeep){//第一次顯示魔王戰鬥結果
                bossResultRunTime+=Gdx.graphics.getDeltaTime();
                boss.setCurrentAction("Win");
                boss.setHero1Frame(boss.getAnimationWin().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.getAnimationWin());
                if(boss.getCurrentAnimation().isAnimationFinished(bossResultRunTime)){
                    bossResultRunTime = 0.0f;
                    bossResultKeep = true;
                }
            }else{//持續顯示魔王戰鬥結果
                boss.setCurrentAction("WinKeep");
                boss.setHero1Frame(boss.getAnimationWinKeep().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.getAnimationWinKeep());
            }

            spriteBoss.setRegion(boss.getHero1Frame());
            spriteBoss.setSize(boss.getHero1Frame().getRegionWidth(), boss.getHero1Frame().getRegionHeight());
            spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);

            //人物著地後繪圖位置修正量
            if(collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(bossPosition.x, bossPosition.y);//設定魔王位置
            spriteBoss.setScale(1.8f);//設定魔王大小

            tiledMapRenderer.setView(camera);

            //調整鏡頭位置
            if(hero.position.x>=screenWidth/2 && hero.position.x<=(stageWidth-(screenWidth/2))){//中間區域
                //跟隨英雄
                camera.position.x = hero.position.x;
            }else if(hero.position.x<screenWidth/2){//最左邊
                camera.position.x = screenWidth/2;
            }else if(hero.position.x>(stageWidth-(screenWidth/2))){//最右邊
                camera.position.x = stageWidth-(screenWidth/2);
            }

            tiledMapRenderer.render(background);
            tiledMapRenderer.render(foreground);

//	    Display on Screen
            batch.begin();
            spriteBoss.draw(batch);
            sprite.draw(batch);
            batch.end();
        }

//	    顯示分數or生命值
        HUDBatch.begin();
        font1.draw(HUDBatch, "HERO:" + HERO_SCORE, 550, 700);
        font2.draw(HUDBatch, "BOSS:" + BOSS_SCORE, 750, 700);
        HUDBatch.end();

        //顯示控制按鈕
        controlBatch.begin();
        stage.act();
        stage.draw();
        controlBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        hero.getHero1Atlas().dispose();
        boss.getHero1Atlas().dispose();
        bulletRight.dispose();
        HUDBatch.dispose();
        controlBatch.dispose();
        font1.dispose();
        bgSound1.music.dispose();

        if(gunSound!=null){
            gunSound.dispose();
        }
    }


    public void openFire(){
        gunSound.play(0.5f);
        if(hero.isFacingRight()){
            bullet = new Bullet(hero.position, bulletVelocityX);
        }
        else{
            bullet = new Bullet(hero.position, -bulletVelocityX);
        }

        bulletManager.add(bullet);
    }

    public void jump(){
        hero.setCurrentAction("Jumping");

        if(isRightTouchDown){//當在跑步中按住方向鍵時,額外給予x軸與y軸方向動力
            hero.setIsJumpAndWalk(true);
            isRightSprintJump = true;
            hero.velocity.x = 850;
            hero.velocity.y = 350;
        }else if(isLeftTouchDown){
            hero.setIsJumpAndWalk(true);
            isLeftSprintJump = true;
            hero.velocity.x = -850;
            hero.velocity.y = 350;
        }else{//原地跳
            hero.setIsJumpAndWalk(false);
            isRightSprintJump = false;
            isLeftSprintJump = false;
            hero.velocity.y = 300;
        }

        jumpY = hero.position.y;
    }


    public void atk1(){
        hero.setCurrentAction("Atking1");
    }

    public void satk1(){
        hero.setCurrentAction("Satking1");
    }


    public void backHome () {
        homeScreen=new HomeScreen(game);
        this.game.setScreen(homeScreen);
        dispose();
    }


    /**
     * 計算跳躍位置
     * @return
     */
    public float calJumpY(){
        float tempY;

        float g = -10;

        if(v0==0.0f){
            g = -15000;
        }

        //新版
        hero.velocity.y = v0 + ((g)*deltaTime);

        tempY = hero.position.y + (hero.velocity.y * deltaTime);

        if(tempY > hero.beforePosition.y) {//跳躍上升中
            if(tempY>=jumpY+110){//到達頂點
                v0 = 0.0f;
                jumpY = 0.0f;
            }else{//未到達頂點
                v0 = 300.0f;
            }
        }else{//跳躍下降中

        }

        return tempY;
    }

}

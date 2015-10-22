package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
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

/**遊戲第一關
 * Created by 6193 on 2015/10/19.
 */
public class PlayScreenTest implements Screen{

    private Vector2 position = new Vector2();//英雄當前位置
    private Vector2 bossPosition = new Vector2();//魔王當前位置
    private Vector2 beforePosition = new Vector2();//英雄前一個位置
    private Vector2 velocity = new Vector2();//給予英雄的方向速度
    private float MaxVelocity = 800f;//終端速度
    private float deltaTime = 0.0f;
    private float runTime = 0.0f;
    private float animationTime = 0.0f;
    private  float screenWidth;
    private  float screenHeight;
    private int[] background = new int[] {0};
    private int[] foreground = new int[] {1};
    private int[] upperlayer = new int[] {2};
    private float x1, y1, i1, j1;
    private int tempcount, tempcount1;
    public int bulletVelocityX = 12;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Sprite sprite;
    private Sprite spriteBoss;
    private TiledMapTileLayer.Cell cell;
    Texture bulletRight;
    TextureRegion bulletLeft;
    Bullet bullet, currentBullet;
    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();
    private float deltay = 0.0f;

    private SpriteBatch controlBatch;
    private SpriteBatch HUDBatch;
    private BitmapFont font1;
    private BitmapFont font2;
    private  float jumpY = 0.0f;
    private  float v0 = 300.0f;


    BackgroundSound bgSound1;//背景音樂
    Sound gunSound;//子彈音效

    //碰撞判斷
    private boolean collisionLeft, collisionRight, collisionBottom, collisionTop;

    //地圖資源
    TiledMapTileLayer foregroundLayer;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    private Skin btnSkin;
    private Stage stage;

    //玩家介面操控按鈕 [方向鍵] + [技能鍵]
    private Button btn_fire;
    private Button  btn_jump;
    private Button  btn_right;
    private Button  btn_left;
    private Button  btn_home;
    private Button  btn_atk1;

    private boolean isRightTouchDown = false;//判斷是否持續按住 [向右方向鍵]
    private boolean isLeftTouchDown = false;//判斷是否持續按住 [向左方向鍵]

    private boolean isLeftSprintJump = false;//判斷是否為衝刺跳躍[左大跳]
    private boolean isRightSprintJump = false;//判斷是否衝刺跳躍[右大跳]

    Game game;
    HomeScreen homeScreen;//返回城鎮(關卡選擇)
    HeroShana hero;//英雄人物
    BossKing1 boss;//魔王
    private int HERO_SCORE = 100;
    private int BOSS_SCORE = 100;

    private float currentHeroWidth = 0.0f;
    private float currentHeroHeight = 0.0f;
//    boolean startGame = false;

    private float stageWidth = 0.0f;

    boolean touchBossP1 = false;
    boolean touchBossP2 = false;
    boolean hitOnBoss = false;
    String gameStatus = "Running";//Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗

    boolean isSearchHero = false;

    public PlayScreenTest(Game game){
        this.game=game;
        init();
    }


    public void init () {
//        startGame = true;
        Gdx.app.log("==init()===", "start init()");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();
        controlBatch = new SpriteBatch();

        hero = new HeroShana();
        boss = new BossKing1();

        position.x = screenWidth/2;//英雄初始位置X
        position.y = 250;//英雄初始位置Y
        velocity.x = 300;//英雄X軸初始速度
        velocity.y = -600;//英雄Y軸初始速度

        bossPosition.x = 800;
        bossPosition.y = 86;


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);//設置鏡頭大小
        camera.setToOrtho(false, w, h);//y軸向上
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

        //場景加入演員
        stage.addActor(btn_right);
        stage.addActor(btn_left);
        stage.addActor(btn_fire);
        stage.addActor(btn_jump);
        stage.addActor(btn_home);
        stage.addActor(btn_atk1);

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
                if (velocity.x < 0){
                    velocity.x = -velocity.x;
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
                if (velocity.x > 0) {
                    velocity.x = -velocity.x;
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
        if (cell != null &&
                cell.getTile() != null &&
                cell.getTile().getProperties().containsKey("blocked")){
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


    //********************************************************************************
//  Left Collision Detection
    public	boolean collisionLeft() {
        tempcount=0;
        for (int i = 32 ; i<=64; i +=32){
            i1 = new Float(i);
            x1 = (position.x-5)/32;
            y1 = ((position.y+i1)/32);
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
            x1 = (position.x+64)/32;
            y1 = ((position.y+i1)/32);
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
                x1 = (position.x+j1)/32;
                y1 = (position.y/32);
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
                x1 = (position.x+j1)/32;
                y1 = (position.y/32);
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
        x1 = (position.x+32)/32;
        y1 = (position.y+64)/32;
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

// 		Update deltaTime & animationTime
        deltaTime = Gdx.graphics.getDeltaTime();
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

                hero.setHero1Frame(hero.isFacingRight() ? hero.getAnimationWalkingRight().getKeyFrame(animationTime, true) : hero.getAnimationWalkingLeft().getKeyFrame(animationTime, true));

                collisionLeft = false;
                collisionRight = false;
                if (hero.isFacingRight()){
                    collisionRight();//偵測是否碰撞障礙物
                    isTouchBoss(position.x, position.y);
                    if (collisionRight){
                        velocity.x = 0;
                    } else if(touchBossP1){
                        velocity.x = 0;
                    } else if(touchBossP2){
                        velocity.x = 300;
                    } else{
                        velocity.x = 300;
                    }
                }
                if (!hero.isFacingRight()){
                    collisionLeft();
                    isTouchBoss(position.x, position.y);
                    if (collisionLeft){
                        velocity.x = 0;
                    } else if(touchBossP1){
                        velocity.x = -300;
                    } else if(touchBossP2){
                        velocity.x = 0;
                    } else {
                        velocity.x = -300;
                    }
                }
                position.x = position.x + (velocity.x * deltaTime);

                collisionBottom=false;
                collisionBottom();
                if (!collisionBottom){
                    hero.setCurrentAction("Jumping");
                    velocity.y=-300;
                }
//********************************************************************************
            }else if (hero.getCurrentAction().equals("Jumping")){

                if (hero.isJumpAndWalk()){
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

                collisionTop=false;
                collisionTop();
                if (collisionTop){
                    velocity.y = -100;
                    hero.setCurrentAction("Jumping");
                }

//            velocity.y -= MaxVelocity * deltaTime*1.45f;
////	   Clamp velocity (Terminal Velocity)終端速度
//            if (velocity.y < -MaxVelocity){
//                velocity.y = -MaxVelocity;
//            }
//            position.y = position.y + (velocity.y * deltaTime);

                position.y = calJumpY();

                if(hero.isFacingRight()){//向右跳躍
                    if(position.y > beforePosition.y){//跳躍上升中
                        hero.setHero1Frame(hero.getJumpingRightUp());
                        System.out.println("[向右跳躍]--[跳躍上升中]");
                    }else{//跳躍下降中
                        hero.setHero1Frame(hero.getJumpingRightDown());
                        System.out.println("[向右跳躍]--[跳躍下降中]");
                    }
                }else{//向左跳躍
                    if(position.y > beforePosition.y){//跳躍上升中
                        hero.setHero1Frame(hero.getJumpingLeftUp());
                    }else{//跳躍下降中
                        hero.setHero1Frame(hero.getJumpingLeftDown());
                    }
                }

                collisionBottom=false;
                collisionBottom();
                if (collisionBottom){//已著地
                    velocity.y = 0;
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
                runTime+=Gdx.graphics.getDeltaTime();
                hero.setHero1Frame(hero.isFacingRight() ? hero.getAnimationAttaRight().getKeyFrame(animationTime, true) : hero.getAnimationAttaLeft().getKeyFrame(animationTime, true));

//                //動畫中,每一動作的持續時間
//                System.out.println("hero.getAnimationAttaRight().getFrameDuration() = " + hero.getAnimationAttaRight().getFrameDuration());
//
//                //此動畫的總時間 = (每一格的持續時間 X 總動畫格數)
//                System.out.println("hero.getAnimationAttaRight().getAnimationDuration() = "+hero.getAnimationAttaRight().getAnimationDuration());

                if(hero.getAnimationAttaRight().isAnimationFinished(runTime)){

                    if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                        hero.setCurrentAction("Walking");
                    }else{
                        hero.setCurrentAction("Standing");
                    }

                    runTime = 0.0f;
                }

                collisionBottom=false;
                collisionBottom();
                if (!collisionBottom){
                    hero.setCurrentAction("Jumping");
                    velocity.y=-300;
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




//            //將魔王的方向面對英雄
//            if(position.x<=bossPosition.x){
//                boss.setIsFacingRight(false);
//            }else{
//                boss.setIsFacingRight(true);
//            }
//
//            //step1. 亂數選擇魔王的行為
//            if(!isSearchHero){
//
//            }
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

            sprite.setPosition(position.x, position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(bossPosition.x, bossPosition.y);//設定魔王位置
            spriteBoss.setScale(1.8f);//設定魔王大小

            tiledMapRenderer.setView(camera);

            //調整鏡頭位置
            if(position.x>=screenWidth/2 && position.x<=(stageWidth-(screenWidth/2))){//中間區域
                //跟隨英雄
                camera.position.x = position.x;
            }else if(position.x<screenWidth/2){//最左邊
                camera.position.x = screenWidth/2;
            }else if(position.x>(stageWidth-(screenWidth/2))){//最右邊
                camera.position.x = stageWidth-(screenWidth/2);
            }

            tiledMapRenderer.render(background);
            tiledMapRenderer.render(foreground);
//        tiledMapRenderer.render(upperlayer);
//	    Display on Screen
            batch.begin();
            sprite.draw(batch);
            spriteBoss.draw(batch);

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
            beforePosition.x = position.x;
            beforePosition.y = position.y;




        }else if(gameStatus.equals("Sotp")){

        }else if(gameStatus.equals("Win")){

        }else if(gameStatus.equals("Lose")){

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
            bullet = new Bullet(position, bulletVelocityX);
        }
        else{
            bullet = new Bullet(position, -bulletVelocityX);
        }

        bulletManager.add(bullet);
    }

    public void jump(){
        hero.setCurrentAction("Jumping");

        if(isRightTouchDown){//當在跑步中按住方向鍵時,額外給予x軸與y軸方向動力
            hero.setIsJumpAndWalk(true);
            isRightSprintJump = true;
            velocity.x = 850;
            velocity.y = 350;
        }else if(isLeftTouchDown){
            hero.setIsJumpAndWalk(true);
            isLeftSprintJump = true;
            velocity.x = -850;
            velocity.y = 350;
        }else{//原地跳
            hero.setIsJumpAndWalk(false);
            isRightSprintJump = false;
            isLeftSprintJump = false;
            velocity.y = 300;
        }

        jumpY = position.y;
    }


    public void atk1(){
        hero.setCurrentAction("Atking1");
        runTime = 0.0f;
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
        float tempY = 0.0f;

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
                jumpY = 0.0f;
            }else{//未到達頂點
                v0 = 300.0f;
            }
        }else{//跳躍下降中

        }




        return tempY;
    }

}

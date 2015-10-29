package com.mygdx.game.levels;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.BackgroundSound;
import com.mygdx.game.Boss;
import com.mygdx.game.BossKing1;
import com.mygdx.game.dao.CollisionDao;
import com.mygdx.game.HomeScreen;
import com.mygdx.game.role.hero.Rshana;

import java.util.ArrayList;
import java.util.List;

/**遊戲第3關
 * Created by 6193 on 2015/10/19.
 */
public class PlayScreen3 extends PlayBase {

//    private Vector2 bossPosition = new Vector2();//魔王當前位置
    private float MaxVelocity = 800f;//人物空中下降終端速度

    //所有動畫展示時間
    private float bossHurtRunTime = 0.0f;//魔王受傷動畫累積時間
    private float heroResultRunTime = 0.0f;//英雄戰鬥結果動畫累積時間
    private float bossResultRunTime = 0.0f;//魔王戰鬥結果動畫累積時間
    private float animationTime = 0.0f;

    //背景地圖
    private int[] background = new int[] {0};
    private int[] foreground = new int[] {1};

    private Sprite sprite;//英雄
    private Sprite spriteBoss;//BOSS
    private SpriteBatch batch;//繪製主要腳色
    private SpriteBatch controlBatch;//繪製玩家操控按鈕
    private SpriteBatch HUDBatch;//繪製玩家生命值相關資訊

    private float deltay = 0.0f;

    private BitmapFont font1;//英雄血量文字
    private BitmapFont font2;//BOSS血量文字

    private boolean heroResultKeep = false;//是否開始撥放英雄戰鬥結果(持續)動畫
    private boolean bossResultKeep = false;//是否開始撥放BOSS戰鬥結果(持續)動畫


    BackgroundSound bgSound1;//背景音樂

    BossKing1 boss;//魔王
    private int HERO_SCORE = 100;
    private int BOSS_SCORE = 1000;

    List<Boss> monster = new ArrayList<Boss>();

    boolean hitOnBoss = false;//是否擊中BOSS

    String gameStatus = "Running";//Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗

    public PlayScreen3(Game game){
        this.game = game;
        init();
    }


    @Override
    public void init () {
        super.init();
        Gdx.app.log("==PlayScreen3.init()===", "start init()");

        //設定本關卡地圖
        tiledMap = new TmxMapLoader().load("map/newmap2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("foreground");
        stageWidth = foregroundLayer.getWidth()*foregroundLayer.getTileWidth();

        //建立繪圖精靈
        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();
        controlBatch = new SpriteBatch();

        //建立碰撞判定邏輯物件
        collisionDao = new CollisionDao();
        collisionDao.setForegroundLayer(foregroundLayer);

        //初始化BOSS
        boss = new BossKing1();
        boss.bossPosition.x = 800;
        boss.bossPosition.y = 86;

        //把BOOS加入本關卡怪物行列
        monster.add(boss);

        //初始化英雄
        hero = new Rshana();
        hero.setCollisionDao(collisionDao);
        hero.position.x = screenWidth/2;//英雄初始位置X
        hero.position.y = 250;//英雄初始位置Y
        hero.velocity.x = 300;//英雄X軸初始速度
        hero.velocity.y = -150;//英雄Y軸初始速度
        hero.setTarget(monster);



        //設定本關卡背景音樂
        bgSound1 = new BackgroundSound();

        //設定英雄/BOSS血量顯示文字
        font1 = new BitmapFont();
        font1.setColor(Color.YELLOW);
        font2 = new BitmapFont();
        font2.setColor(Color.RED);

        //設定英雄特殊按鈕/技能
        hero.setSpecialBtn();
        stage.addActor(((Rshana)hero).btn_atk1);
        stage.addActor(((Rshana)hero).btn_satk1);

        Gdx.input.setInputProcessor(stage);//將場景加入輸入(觸控)偵測
    }

    @Override
    public void backHome() {
        homeScreen = new HomeScreen(game);
        this.game.setScreen(homeScreen);
        dispose();
    }


//    /**
//     * 判斷普通攻擊是否攻擊到魔王
//     * @param x
//     * @param y
//     * @return
//     */
//    private	boolean isHitBoss2(float x, float y, float w, float h){
//        if(x+w>=boss.bossPosition.x-10 && x<=boss.bossPosition.x){
//            hitOnBoss = true;
//            return true;
//        }else if(x-10<=boss.bossPosition.x+boss.getHero1Frame().getRegionWidth() && x>boss.bossPosition.x){
//            hitOnBoss = true;
//            return true;
//        }else{
//            hitOnBoss = false;
//        }
//
//        return false;
//    }


    @Override
    public void show() {
        Gdx.app.log("show","XXXXXXXXXXXXXXXXXXXXXX");
        //在render之前執行(僅執行一次)
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        animationTime += Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        //Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗
        if(gameStatus.equals("Running")){

            //刷新英雄動作
            hero.updateHeroAction(deltaTime, animationTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

            if(sprite!=null){
                sprite.setRegion(hero.getHero1Frame());
                sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            }else{
                sprite = new Sprite(hero.getHero1Frame());
            }

            //將魔王的方向面對英雄
            if(hero.position.x<=boss.bossPosition.x){
                boss.setIsFacingRight(false);
            }else{
                boss.setIsFacingRight(true);
            }

            if(boss.getCurrentAction().equals("Hurt")){
                bossHurtRunTime+=Gdx.graphics.getDeltaTime();
                boss.setHero1Frame(boss.isFacingRight() ? boss.getAnimationHurtRight().getKeyFrame(animationTime, true) : boss.getAnimationHurtLeft().getKeyFrame(animationTime, true));
                boss.setCurrentAnimation(boss.isFacingRight() ? boss.getAnimationHurtRight() : boss.getAnimationHurtLeft());

                if(boss.isFacingRight()){
                    boss.bossPosition.x = boss.bossPosition.x-1;
                }else{
                    boss.bossPosition.x = boss.bossPosition.x+1;
                }

                if(boss.getCurrentAnimation().isAnimationFinished(bossHurtRunTime)){

                    boss.setCurrentAction("Standing");
                    boss.setCurrentAnimation(boss.isFacingRight() ? boss.getAnimationStandingRight() : boss.getAnimationStandingLeft());

                    bossHurtRunTime = 0.0f;
                }
            } else {
                boss.setCurrentAction("Standing");
                boss.setHero1Frame(boss.isFacingRight()?boss.getAnimationStandingRight().getKeyFrame(animationTime, true):boss.getAnimationStandingLeft().getKeyFrame(animationTime, true));
            }

//            //判斷英雄是否擊中目標
//            isHitBoss2(hero.position.x, hero.position.y, hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
//            if (hitOnBoss){//擊中
//                boss.setCurrentAction("Hurt");
//                BOSS_SCORE = BOSS_SCORE-1;
//                boss.HP = boss.HP-1;
//            }

            if(spriteBoss!=null){
                spriteBoss.setRegion(boss.getHero1Frame());
                spriteBoss.setSize(boss.getHero1Frame().getRegionWidth(), boss.getHero1Frame().getRegionHeight());
                spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);
            }else{
                spriteBoss = new Sprite(boss.getHero1Frame());
            }

            //人物著地後繪圖位置修正量
            if(collisionDao.collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(boss.bossPosition.x, boss.bossPosition.y);//設定魔王位置
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
                    boss.bossPosition.x = boss.bossPosition.x-2;
                }else{
                    boss.bossPosition.x = boss.bossPosition.x+2;
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
            if(collisionDao.collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(boss.bossPosition.x, boss.bossPosition.y);//設定魔王位置
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
            if(collisionDao.collisionBottom){
                deltay = 22.0f;
            }else{
                deltay = 0.0f;
            }

            sprite.setPosition(hero.position.x, hero.position.y + deltay);//設定人物位置
            sprite.setScale(1.8f);//設定人物大小

            spriteBoss.setPosition(boss.bossPosition.x, boss.bossPosition.y);//設定魔王位置
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
        font2.draw(HUDBatch, "BOSS:" + boss.HP, 750, 700);
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
    public void dispose() {
        super.dispose();

        batch.dispose();
        hero.getHero1Atlas().dispose();
        boss.getHero1Atlas().dispose();
        HUDBatch.dispose();
        controlBatch.dispose();
        font1.dispose();
        bgSound1.music.dispose();
    }

}

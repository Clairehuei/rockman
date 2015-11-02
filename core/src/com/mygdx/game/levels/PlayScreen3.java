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
import com.mygdx.game.levels.monsterList.MonsterGroup;
import com.mygdx.game.levels.monsterList.Mplay03;
import com.mygdx.game.role.monster.Rboss;
import com.mygdx.game.role.monster.Rmonster;
import com.mygdx.game.sound.background.BackgroundSound;
import com.mygdx.game.dao.CollisionDao;
import com.mygdx.game.HomeScreen;
import com.mygdx.game.role.hero.Rshana;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**遊戲第3關
 * Created by 6193 on 2015/10/19.
 */
public class PlayScreen3 extends PlayBase {

    String gameStatus = "Running";//Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗

    //所有動畫展示時間
    private float heroResultRunTime = 0.0f;//英雄戰鬥結果動畫累積時間
    private float bossResultRunTime = 0.0f;//魔王戰鬥結果動畫累積時間
    private float animationTime = 0.0f;

    //背景地圖
    private int[] background = new int[] {0};
    private int[] foreground = new int[] {1};

    private Sprite sprite;//英雄
    private Sprite spriteMonster;//所有怪物
    private SpriteBatch batch;//繪製主要腳色
    private SpriteBatch HUDBatch;//繪製玩家生命值相關資訊

    private BitmapFont font1;//英雄血量文字
    private BitmapFont font2;//BOSS血量文字
    private BitmapFont font3;//怪物1號血量文字

    private boolean heroResultKeep = false;//是否開始撥放英雄戰鬥結果(持續)動畫
    private boolean bossResultKeep = false;//是否開始撥放BOSS戰鬥結果(持續)動畫

    MonsterGroup monsterList;
    List<Rmonster> monster = new ArrayList<Rmonster>();//本關卡的怪物集合
    Iterator<Rmonster> imonster;
    Rmonster t;

    BackgroundSound bgSound1;//背景音樂

    public PlayScreen3(Game game){
        this.game = game;
        init();
    }


    @Override
    public void init () {
        super.init();
        Gdx.app.log("==PlayScreen3.init()===", "start init()");

        //設定本關卡地圖
        tiledMap = new TmxMapLoader().load("map/newmap4.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("foreground");
        stageWidth = foregroundLayer.getWidth()*foregroundLayer.getTileWidth();

        //建立繪圖精靈
        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();

        //建立碰撞判定邏輯物件
        collisionDao = new CollisionDao();
        collisionDao.setForegroundLayer(foregroundLayer);

        //初始化怪物列表
        monsterList = new Mplay03();
        monsterList.init();
        monster = monsterList.getMonsters();

        //初始化英雄
        hero = new Rshana();
        hero.setCollisionDao(collisionDao);
        hero.position.x = screenWidth/2;//英雄初始位置X
        hero.position.y = 382;//英雄初始位置Y
        hero.velocity.x = 300;//英雄X軸初始速度
        hero.velocity.y = -150;//英雄Y軸初始速度
        hero.setTarget(monster);

        monsterList.setTarget(hero);

        //設定本關卡背景音樂
        bgSound1 = new BackgroundSound();

        //設定英雄/BOSS血量顯示文字
        font1 = new BitmapFont();
        font1.setColor(Color.YELLOW);
        font2 = new BitmapFont();
        font2.setColor(Color.RED);
        font3 = new BitmapFont();
        font3.setColor(Color.GREEN);

        //設定英雄特殊按鈕/技能
        hero.setSpecialBtn();
        stage.addActor(((Rshana)hero).btn_atk1);
        stage.addActor(((Rshana) hero).btn_satk1);

        Gdx.input.setInputProcessor(stage);//將場景加入輸入(觸控)偵測
    }

    @Override
    public void backHome() {
        homeScreen = new HomeScreen(game);
        this.game.setScreen(homeScreen);
        dispose();
    }

    @Override
    public void checkResult() {
//        if(boss.HP<=0){
//            gameStatus = "Win";
//        }else if(hero.HP<=0){
//            gameStatus = "Lose";
//        }
    }


    @Override
    public void show() {
        //show();會在render之前執行(僅執行一次)
        Gdx.app.log("show", "XXXXXXXXXXXXXXXXXXXXXX");
    }


    public void gameRun(){

        //刷新英雄動作
        hero.updateHeroAction(deltaTime, animationTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

        if(sprite!=null){
            sprite.setRegion(hero.getHero1Frame());
            sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        }else{
            sprite = new Sprite(hero.getHero1Frame());
        }


        imonster = monster.iterator();
        while (imonster.hasNext()) {
            t = imonster.next();

//            //將怪物的方向面對英雄
//            if(hero.position.x<=t.position.x){
//                t.setIsFacingRight(false);
//            }else{
//                t.setIsFacingRight(true);
//            }

            //刷新怪物動作
            t.updateMonsterAction(deltaTime, animationTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

            if(t.HP<=0){
                if(t.getCurrentAction().equals("cleanMe")){
                    Gdx.app.log("cleanMe","remove 1 ");
//                    imonster.remove();
                }else{
                    Gdx.app.log("cleanMe","remove 0 ");
                    t.setCurrentAction("Lose");
                }
            }
            else{
//                Gdx.app.log("cleanMe","---------------------------------------");
            }
        }


        sprite.setPosition(hero.position.x, hero.position.y);//設定英雄位置
        sprite.setScale(1.8f);//設定英雄大小

        tiledMapRenderer.setView(camera);

        //調整鏡頭位置
        moveCamera();

        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);

        //開始繪製畫面
        batch.begin();

             //先繪製所有怪物
             imonster = monster.iterator();
             while (imonster.hasNext()) {
                 t = imonster.next();

                 spriteMonster = new Sprite(t.getMonsterFrame());
                 spriteMonster.setRegion(t.getMonsterFrame());
                 spriteMonster.setSize(t.getMonsterFrame().getRegionWidth(), t.getMonsterFrame().getRegionHeight());
                 spriteMonster.setOrigin(spriteMonster.getWidth() / 2, spriteMonster.getHeight() / 2);
                 spriteMonster.setPosition(t.position.x, t.position.y);//設定怪物位置
                 spriteMonster.setScale(1.8f);//設定魔王大小

                 spriteMonster.draw(batch);
             }

             //再繪製英雄
             sprite.draw(batch);

         batch.end();

        //設定英雄前一個位置
        hero.beforePosition.x = hero.position.x;
        hero.beforePosition.y = hero.position.y;

        checkResult();//判斷遊戲結果
    }


    public void gameSotp(){

    }


    public void gameWin(){
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

//        if(!bossResultKeep){//第一次顯示魔王戰鬥結果
////            bossResultRunTime+=Gdx.graphics.getDeltaTime();
////            boss.setCurrentAction("Lose");
////            boss.setMonsterFrame(boss.isFacingRight() ? ((Rboss)boss).getAnimationLoseRight().getKeyFrame(animationTime, true) : ((Rboss)boss).getAnimationLoseLeft().getKeyFrame(animationTime, true));
////            boss.setCurrentAnimation(boss.isFacingRight() ? ((Rboss)boss).getAnimationLoseRight() : ((Rboss)boss).getAnimationLoseLeft());
////
////            if(boss.isFacingRight()){
////                boss.position.x = boss.position.x-2;
////            }else{
////                boss.position.x = boss.position.x+2;
////            }
////
////            if(boss.getCurrentAnimation().isAnimationFinished(bossResultRunTime)){
////                bossResultRunTime = 0.0f;
////                bossResultKeep = true;
////            }
//
////            bossResultKeep = monsterList.showResult();
//
//        }else{//持續顯示魔王戰鬥結果
////            boss.setCurrentAction("LoseKeep");
////            boss.setMonsterFrame(boss.isFacingRight() ? ((Rboss)boss).getAnimationLoseKeepRight().getKeyFrame(animationTime, true) : ((Rboss)boss).getAnimationLoseKeepLeft().getKeyFrame(animationTime, true));
////            boss.setCurrentAnimation(boss.isFacingRight()?((Rboss)boss).getAnimationLoseKeepRight():((Rboss)boss).getAnimationLoseKeepLeft());
////            monsterList.showResultKeep();
//        }

//        spriteBoss.setRegion(boss.getMonsterFrame());
//        spriteBoss.setSize(boss.getMonsterFrame().getRegionWidth(), boss.getMonsterFrame().getRegionHeight());
//        spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);


        sprite.setPosition(hero.position.x, hero.position.y);//設定人物位置
        sprite.setScale(1.8f);//設定人物大小

//        spriteBoss.setPosition(boss.position.x, boss.position.y);//設定魔王位置
//        spriteBoss.setScale(1.8f);//設定魔王大小

        tiledMapRenderer.setView(camera);

        //調整鏡頭位置
        moveCamera();

        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);
//	    Display on Screen
        batch.begin();
//        spriteBoss.draw(batch);
        sprite.draw(batch);
        batch.end();
    }


    public void gameLose(){
//        if(!heroResultKeep){//第一次顯示英雄戰鬥結果
//            heroResultRunTime+=Gdx.graphics.getDeltaTime();
//            hero.setCurrentAction("Lose");
//            hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationLoseRight().getKeyFrame(animationTime, true):hero.getAnimationLoseLeft().getKeyFrame(animationTime, true));
//            hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationLoseRight() : hero.getAnimationLoseLeft());
//            if(hero.isFacingRight()){
//                hero.position.x = hero.position.x-2;
//            }else{
//                hero.position.x = hero.position.x+2;
//            }
//            if(hero.getCurrentAnimation().isAnimationFinished(heroResultRunTime)){
//                heroResultRunTime = 0.0f;
//                heroResultKeep = true;
//            }
//        }else{//持續顯示英雄戰鬥結果
//            hero.setCurrentAction("LoseKeep");
//            hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationLoseKeepRight().getKeyFrame(animationTime, true):hero.getAnimationLoseKeepLeft().getKeyFrame(animationTime, true));
//            hero.setCurrentAnimation(hero.isFacingRight() ? hero.getAnimationLoseKeepRight() : hero.getAnimationLoseKeepLeft());
//        }
//
//        sprite.setRegion(hero.getHero1Frame());
//        sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
//        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
//
//        if(!bossResultKeep){//第一次顯示魔王戰鬥結果
//            bossResultRunTime+=Gdx.graphics.getDeltaTime();
//            boss.setCurrentAction("Win");
//            boss.setMonsterFrame(((Rboss)boss).getAnimationWin().getKeyFrame(animationTime, true));
//            boss.setCurrentAnimation(((Rboss)boss).getAnimationWin());
//            if(boss.getCurrentAnimation().isAnimationFinished(bossResultRunTime)){
//                bossResultRunTime = 0.0f;
//                bossResultKeep = true;
//            }
//        }else{//持續顯示魔王戰鬥結果
//            boss.setCurrentAction("WinKeep");
//            boss.setMonsterFrame(((Rboss)boss).getAnimationWinKeep().getKeyFrame(animationTime, true));
//            boss.setCurrentAnimation(((Rboss)boss).getAnimationWinKeep());
//        }
//
//        spriteBoss.setRegion(boss.getMonsterFrame());
//        spriteBoss.setSize(boss.getMonsterFrame().getRegionWidth(), boss.getMonsterFrame().getRegionHeight());
//        spriteBoss.setOrigin(spriteBoss.getWidth() / 2, spriteBoss.getHeight() / 2);
//
//
//        sprite.setPosition(hero.position.x, hero.position.y);//設定人物位置
//        sprite.setScale(1.8f);//設定人物大小
//
//        spriteBoss.setPosition(boss.position.x, boss.position.y);//設定魔王位置
//        spriteBoss.setScale(1.8f);//設定魔王大小
//
//        tiledMapRenderer.setView(camera);
//
//        //調整鏡頭位置
//        moveCamera();
//
//        tiledMapRenderer.render(background);
//        tiledMapRenderer.render(foreground);
//
////	    Display on Screen
//        batch.begin();
//        spriteBoss.draw(batch);
//        sprite.draw(batch);
//        batch.end();
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        animationTime += Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        //Running:進行中  Sotp:暫停  Win:勝利  Lose:失敗
        if(gameStatus.equals("Running")){
            gameRun();
        }else if(gameStatus.equals("Sotp")){
            gameSotp();
        }else if(gameStatus.equals("Win")){
            gameWin();
        }else if(gameStatus.equals("Lose")){
            gameLose();
        }

        //顯示分數or生命值
        HUDBatch.begin();
        font1.draw(HUDBatch, "HERO:" + hero.HP, 550, 700);
//        font2.draw(HUDBatch, "BOSS:" + ((Rboss)monster.get(0)).HP, 750, 700);
//        font3.draw(HUDBatch, "MONSTER:" + ((Rboss)monster.get(1)).HP, 1000, 700);
        HUDBatch.end();


        //顯示控制按鈕
        stage.act(delta);
        stage.draw();
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
        monsterList.cleanAllMonster();
        HUDBatch.dispose();
        font1.dispose();
        font2.dispose();
        font3.dispose();
        bgSound1.music.dispose();
    }

}

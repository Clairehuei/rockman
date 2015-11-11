package com.mygdx.game.levels;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    //本關卡的怪物集合
    MonsterGroup monsterList;
    List<Rmonster> monster = new ArrayList<Rmonster>();
    Iterator<Rmonster> imonster;
    Rmonster t;

    BackgroundSound bgSound1;//背景音樂

    public PlayScreen3(Game game){
        this.game = game;
        init();
    }

//    public TextureAtlas uiAtlas;
//    public TextureRegion heroblood;
//    public TextureRegion blood;
//    private Sprite spriteUi;

    @Override
    public void init () {
        super.init();
        Gdx.app.log("==PlayScreen3.init()===", "start init()");


//        //讀取hud圖檔資源
//        uiAtlas = new TextureAtlas(Gdx.files.internal("ui/hud.pack"));
//        heroblood = uiAtlas.findRegion("heroBlood");
//        blood = uiAtlas.findRegion("blood");
//        spriteUi = new Sprite(heroblood);



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
        hero.setTarget(monster);//設定英雄攻擊目標清單

        monsterList.setTarget(hero);//設定所有怪物的攻擊目標

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
        stage.addActor(((Rshana) hero).btn_atk1);
        stage.addActor(((Rshana) hero).btn_satk1);

        spriteMonster = new Sprite();
        sprite = new Sprite();

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
        if(monsterList.victoryCondition()){
            gameStatus = "Win";
        }else if(hero.HP<=0){
            gameStatus = "Lose";
        }
    }


    @Override
    public void show() {
        //show();會在render之前執行(僅執行一次)
        Gdx.app.log("show", "XXXXXXXXXXXXXXXXXXXXXX");
    }


    public void gameRun(){

        //刷新英雄動作
        hero.updateHeroAction(deltaTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

        //設定英雄圖片
        sprite.setRegion(hero.getHero1Frame());
        sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(hero.position.x, hero.position.y);//設定英雄位置
        sprite.setScale(1.8f);//設定英雄大小


        //刷新所有怪物動作
        imonster = monster.iterator();
        while (imonster.hasNext()) {
            t = imonster.next();

            //將怪物的方向面對英雄
            if(hero.position.x<=t.position.x){
                t.setIsFacingRight(false);
            }else{
                t.setIsFacingRight(true);
            }

            //刷新各別怪物動作
            t.updateMonsterAction(deltaTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

            //將HP小於0的怪物從怪物清單中剃除
            if(t.HP<=0){
                if(t.getCurrentAction().equals("cleanMe")){
                    imonster.remove();
                } else if(t.getCurrentAction().equals("LoseKeep")){
                    t.setCurrentAction("LoseKeep");
                } else{
                    t.setCurrentAction("Lose");
                }
            }
        }

        //調整鏡頭位置
        moveCamera();

        //繪製地圖
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);

        //開始繪製畫面
        batch.begin();

             //先繪製所有怪物
             imonster = monster.iterator();
             while (imonster.hasNext()) {
                 t = imonster.next();

                 spriteMonster.setRegion(t.getMonsterFrame());
                 spriteMonster.setSize(t.getMonsterFrame().getRegionWidth(), t.getMonsterFrame().getRegionHeight());
                 spriteMonster.setOrigin(spriteMonster.getWidth() / 2, spriteMonster.getHeight() / 2);
                 spriteMonster.setPosition(t.position.x, t.position.y);//設定怪物位置
                 spriteMonster.setScale(1.8f);//設定怪物大小

                 spriteMonster.draw(batch);
             }

             //再繪製英雄
             sprite.draw(batch);

         batch.end();

        checkResult();//判斷遊戲結果
    }


    public void gameSotp(){

    }


    public void gameWin(){
        if(!hero.getCurrentAction().equals("Win") && !hero.getCurrentAction().equals("WinKeep")){
            hero.setCurrentAction("Win");
        }

        //刷新英雄動作
        hero.updateHeroAction(deltaTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

        //設定英雄圖片
        sprite.setRegion(hero.getHero1Frame());
        sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(hero.position.x, hero.position.y);//設定人物位置
        sprite.setScale(1.8f);//設定人物大小

        //調整鏡頭位置
        moveCamera();

        //繪製地圖
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);

        //開始繪製畫面
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }


    public void gameLose(){
        if(!hero.getCurrentAction().equals("Lose") && !hero.getCurrentAction().equals("LoseKeep")){
            hero.setCurrentAction("Lose");
        }

        //刷新英雄動作
        hero.updateHeroAction(deltaTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);

        //設定英雄圖片
        sprite.setRegion(hero.getHero1Frame());
        sprite.setSize(hero.getHero1Frame().getRegionWidth(), hero.getHero1Frame().getRegionHeight());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(hero.position.x, hero.position.y);//設定人物位置
        sprite.setScale(1.8f);//設定人物大小


        //刷新所有怪物動作
        imonster = monster.iterator();
        while (imonster.hasNext()) {
            t = imonster.next();

            //將怪物的方向面對英雄
            if(hero.position.x<=t.position.x){
                t.setIsFacingRight(false);
            }else{
                t.setIsFacingRight(true);
            }

            //刷新各別怪物勝利動作
            t.setCurrentAction("Win");
            t.updateMonsterAction(deltaTime, isLeftTouchDown, isRightTouchDown, isLeftSprintJump, isRightSprintJump);
        }

        //調整鏡頭位置
        moveCamera();

        //繪製地圖
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);

        //開始繪製畫面
        batch.begin();

            //先繪製所有怪物
            imonster = monster.iterator();
            while (imonster.hasNext()) {
                t = imonster.next();

                spriteMonster.setRegion(t.getMonsterFrame());
                spriteMonster.setSize(t.getMonsterFrame().getRegionWidth(), t.getMonsterFrame().getRegionHeight());
                spriteMonster.setOrigin(spriteMonster.getWidth() / 2, spriteMonster.getHeight() / 2);
                spriteMonster.setPosition(t.position.x, t.position.y);//設定怪物位置
                spriteMonster.setScale(1.8f);//設定怪物大小

                spriteMonster.draw(batch);
            }

            sprite.draw(batch);

        batch.end();
    }


    @Override
    public void render(float delta) {
        super.render(delta);

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
        font2.draw(HUDBatch, "BOSS:" + ((Rboss)monster.get(0)).HP, 750, 700);
        font3.draw(HUDBatch, "MONSTER:" + ((Rboss)monster.get(1)).HP, 1000, 700);
//        drawHud();
        HUDBatch.end();


        //顯示控制按鈕
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }


    public void drawHud(){
//        spriteUi.setRegion(heroblood);//設定怪物位置
//        spriteUi.setPosition(90, 655);//設定怪物位置
//        spriteUi.setScale(1.8f);//設定怪物大小
//        spriteUi.draw(HUDBatch);
//
//        spriteUi.setRegion(blood);
//        spriteUi.setPosition(120, 665);//設定怪物位置
////        spriteUi.setScale(1.8f);//設定怪物大小
//        spriteUi.setScale(0.6f);//設定怪物大小
//        spriteUi.draw(HUDBatch);
    }


    @Override
    public void dispose() {
        super.dispose();
//        saveGame();
        batch.dispose();
        hero.getHero1Atlas().dispose();
        monsterList.cleanAllMonster();
        HUDBatch.dispose();
        font1.dispose();
        font2.dispose();
        font3.dispose();
        bgSound1.music.dispose();

//        uiAtlas.dispose();
    }


    /**
     * 儲存遊戲進度
     */
    public void saveGame(){
        FileHandle file = Gdx.files.local("gameData.txt");
        file.writeString(String.valueOf(((Rboss) monster.get(0)).HP), false);
    }

}

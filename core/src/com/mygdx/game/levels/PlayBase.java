package com.mygdx.game.levels;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.dao.CollisionDao;
import com.mygdx.game.HomeScreen;
import com.mygdx.game.role.hero.Rhero;

/**遊戲場景父類別
 * Created by User on 2015/10/26.
 */
public abstract class PlayBase implements Screen {

    Game game;
    HomeScreen homeScreen;//返回城鎮(關卡選擇)

    //偵測手機螢幕的寬/高
    float screenWidth;
    float screenHeight;

    float deltaTime = 0.0f;//每偵間格時間(因應手機效能有所不同)

    //地圖資源
    TiledMapTileLayer foregroundLayer;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    float stageWidth = 0.0f;//背景地圖的長度(寬)

    //攝影機變數
    OrthographicCamera camera;
    Viewport viewport;

    Rhero hero;//英雄人物

    Skin btnSkin;//按鈕風格
    Stage stage;

    CollisionDao collisionDao;//碰撞邏輯

    //玩家介面操控按鈕 [方向鍵] + [技能鍵]
    Button  btn_right;
    Button  btn_left;
    Button  btn_jump;
    Button  btn_home;

    boolean isRightTouchDown = false;//判斷是否持續按住 [向右方向鍵]
    boolean isLeftTouchDown = false;//判斷是否持續按住 [向左方向鍵]
    boolean isLeftSprintJump = false;//判斷是否為衝刺跳躍[左大跳]
    boolean isRightSprintJump = false;//判斷是否衝刺跳躍[右大跳]

    float jumpY = 0.0f;//跳躍基準位置

    public void init () {
        Gdx.app.log("==PlayBase.init()===", "start init()");
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);//設置鏡頭大小
        camera.setToOrtho(false, screenWidth, screenHeight);//y軸向上
        camera.update();

        stage = new Stage();
        btnSkin = new Skin(Gdx.files.internal("data/btn.json"),new TextureAtlas("data/btn.pack"));

        //設定功能按鈕
        setBtnDirection();
        setBtnJump();
        setBtnHome();

        //場景加入演員(固定班底:操作按鈕)
        stage.addActor(btn_right);
        stage.addActor(btn_left);
        stage.addActor(btn_jump);
        stage.addActor(btn_home);
    }


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


    public void moveCamera(){
        //調整鏡頭位置
        if(hero.position.x>=screenWidth/2 && hero.position.x<=(stageWidth-(screenWidth/2))){//中間區域
            //跟隨英雄
            camera.position.x = hero.position.x;
        }else if(hero.position.x<screenWidth/2){//最左邊
            camera.position.x = screenWidth/2;
        }else if(hero.position.x>(stageWidth-(screenWidth/2))){//最右邊
            camera.position.x = stageWidth-(screenWidth/2);
        }
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
     * 返回城鎮鍵設定
     */
    private void setBtnHome(){
        btn_home = new Button(btnSkin, "jumpbutton");
        btn_home.setPosition(0, 610);
        btn_home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backHome();
            }
        });
    }


    public void jump(){

     if(hero.getCurrentAction().equals("Jumping")){
         //僅限一段跳躍
         //TODO...
     }else{
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
         hero.jumpY = jumpY;
     }


    }


    //返回城鎮
    public abstract void backHome ();

    //判斷遊戲結果
    public abstract void checkResult ();


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //清除螢幕
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}

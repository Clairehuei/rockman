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

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private float MaxVelocity = 300f;
    private float deltaTime = 0.0f;
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
    private TiledMapTileLayer.Cell cell;
    Texture bulletRight;
    TextureRegion bulletLeft;
    Bullet bullet, currentBullet;
    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();

    private SpriteBatch HUDBatch;
    private BitmapFont font1;

    //	Sound sound;
    BackgroundSound bgSound1;
    Sound gunSound;

    //碰撞判斷
    private boolean collisionLeft, collisionRight, collisionBottom, collisionTop;

    TiledMapTileLayer foregroundLayer;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    private Skin btnSkin;
    private Stage stage;
    private Button btn_fire;
    private Button  btn_jump;
    private Button  btn_right;
    private Button  btn_left;
    private Button  btn_home;

    private boolean isRightTouchDown = false;
    private boolean isLeftTouchDown = false;

    Game game;
    HomeScreen homeScreen;

    HeroShana hero;

    public PlayScreenTest(Game game){
        this.game=game;
        init();
    }


    public void init () {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        batch = new SpriteBatch();
        HUDBatch = new SpriteBatch();

        hero = new HeroShana();

        position.x = 330;
        position.y = screenHeight;
        velocity.x = 300;
        velocity.y = -300;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

//		camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("foreground");

        bulletRight = new Texture("data/bullet.png");
        bulletLeft = new TextureRegion(bulletRight);
        bulletLeft.flip(true, false);


        bgSound1 = new BackgroundSound();

        font1 = new BitmapFont();
        font1.setColor(Color.YELLOW);

        stage = new Stage();

        btnSkin = new Skin(Gdx.files.internal("data/btn.json"),new TextureAtlas("data/btn.pack"));

        //設定功能按鈕
        setBtnDirection();
        setBtnFire();
        setBtnJump();
        setBtnHome();

        stage.addActor(btn_right);
        stage.addActor(btn_left);
        stage.addActor(btn_fire);
        stage.addActor(btn_jump);
        stage.addActor(btn_home);

        Gdx.input.setInputProcessor(stage);
    }


    //***************************************************************************************************************
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
//  Collision Detection Method
    private	boolean isCellBlocked( float x, float y ){

        cell = foregroundLayer.getCell( (int)x, (int)y);
        if (cell != null &&
                cell.getTile() != null &&
                cell.getTile().getProperties().containsKey("blocked")){
            return  true;
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
                if (tempcount1 > 0)
                    collisionBottom = true;
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
                if (tempcount1 > 0)
                    collisionBottom = true;
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

//		Set hero1's Frame & X, Y Positions
        hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationStandingRight().getKeyFrame(animationTime, true):hero.getAnimationStandingLeft().getKeyFrame(animationTime, true));

        if (hero.getCurrentAction().equals("Walking")){

            hero.setHero1Frame(hero.isFacingRight() ? hero.getAnimationWalkingRight().getKeyFrame(animationTime, true) : hero.getAnimationWalkingLeft().getKeyFrame(animationTime, true));

            collisionLeft = false;
            collisionRight = false;
            if (hero.isFacingRight()){
                collisionRight();
                if (collisionRight)
                    velocity.x = 0;
                else velocity.x = 300;
            }
            if (!hero.isFacingRight()){
                collisionLeft();
                if (collisionLeft)
                    velocity.x = 0;
                else velocity.x = -300;
            }
            position.x = position.x + (velocity.x * deltaTime);

            collisionBottom=false;
            collisionBottom();
            if (!collisionBottom){
                hero.setCurrentAction("Jumping");
                velocity.y=-300;
            }
//********************************************************************************
        }else  if (hero.getCurrentAction().equals("Jumping")){
            hero.setHero1Frame(hero.isFacingRight()?hero.getAnimationJumpingRight().getKeyFrame(animationTime, true):hero.getAnimationJumpingLeft().getKeyFrame(animationTime, true));
            if (hero.isJumpAndWalk()){

                if(isRightTouchDown){//當在空中按住方向鍵時,給予x軸方向動力
                    velocity.x = 300;
                }else if(isLeftTouchDown){
                    velocity.x = -300;
                }

                position.x = position.x + (velocity.x * deltaTime*0.3f);
            }

            collisionTop=false;
            collisionTop();
            if (collisionTop){
                velocity.y = -100;
                hero.setCurrentAction("Jumping");
            }

            velocity.y -= MaxVelocity * deltaTime;
//	   Clamp velocity (Terminal Velocity)
            if (velocity.y < -MaxVelocity){
                velocity.y = -MaxVelocity;
            }

            position.y = position.y + (velocity.y * deltaTime);

            collisionBottom=false;
            collisionBottom();
            if (collisionBottom){
                velocity.y = 0;
                hero.setCurrentAction("Standing");
                if(isLeftTouchDown || isRightTouchDown){//當持續按住方向鍵時,人物狀態繼續設定為walking
                    hero.setCurrentAction("Walking");
                }
            }else{
                hero.setCurrentAction("Jumping");
                if(isLeftTouchDown || isRightTouchDown){//當在空中按住方向鍵時,isJumpAndWalk設為true
                    hero.setIsJumpAndWalk(true);
                }
            }
        }

//********************************************************************************


//	    Store Spritesheet to sprite
        sprite = new Sprite(hero.getHero1Frame());
        sprite.setPosition(position.x,position.y);

        tiledMapRenderer.setView(camera);
        camera.position.x = position.x;
        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);
        tiledMapRenderer.render(upperlayer);
//	    Display on Screen
        batch.begin();
        sprite.draw(batch);

//********************************************************************************
        int bulletCounter = 0;
        while (bulletCounter < bulletManager.size())
        {
            currentBullet = bulletManager.get(bulletCounter);
            currentBullet.update();

            if(currentBullet.TempbulletPosition.x> -100 &&
                    currentBullet.TempbulletPosition.x < (screenWidth*2 + 100)){

                if (hero.isFacingRight()){
                    batch.draw(bulletRight, currentBullet.TempbulletPosition.x+32,
                            currentBullet.TempbulletPosition.y+16);
                }
                else
                    batch.draw(bulletLeft, currentBullet.TempbulletPosition.x,
                            currentBullet.TempbulletPosition.y+16);
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

//	    Display HUD (Score & Lives)
        HUDBatch.begin();

        font1.draw(HUDBatch, "SCORE:100", 20, 900);
        hero.getSpriteHero1().draw(HUDBatch);
        hero.getSpriteHero2().draw(HUDBatch);
        hero.getSpriteHero3().draw(HUDBatch);

        btn_right.draw(HUDBatch, 1f);
        btn_left.draw(HUDBatch, 1f);
        btn_fire.draw(HUDBatch, 1f);
        btn_jump.draw(HUDBatch, 1f);
        btn_home.draw(HUDBatch, 1f);

        HUDBatch.end();
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
        bulletRight.dispose();
        HUDBatch.dispose();
        font1.dispose();
        bgSound1.music.dispose();

        if(gunSound!=null){
            gunSound.dispose();
        }
    }


    public void openFire(){

        if(gunSound==null){
            gunSound = Gdx.audio.newSound(Gdx.files.internal("sound/gun1.ogg"));
        }

        gunSound.play(0.5f);
        if(hero.isFacingRight()){
            bullet = new Bullet(position, bulletVelocityX);
        }
        else
            bullet = new Bullet(position, -bulletVelocityX);
        bulletManager.add(bullet);
    }

    public void jump(){
        velocity.y = MaxVelocity;
        hero.setCurrentAction("Jumping");
        hero.setIsJumpAndWalk(true);
    }


    public void backHome () {
        homeScreen=new HomeScreen(game);
        this.game.setScreen(homeScreen);
        dispose();
    }

}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by 6193 on 2015/10/20.
 */
public class PlayScreen2 implements Screen {
//    private static final float FRAME_DURATION = 1.0f / 8.0f;//站立
    private static final float FRAME_DURATION = 1.0f / 15.0f;//跑步
    Game game;
    private Animation animation;
    private TextureAtlas hero1Atlas;
    private float animationTime;
    private Sprite sprite;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float deltaTime;
    private float speed = 150f;
    private float xPosition;
    private Texture texture;
    private static final float SCENE_WIDTH = Gdx.graphics.getWidth();
    private static final float SCENE_HEIGHT = Gdx.graphics.getHeight();
    private SpriteBatch HUDBatch;
    private BitmapFont font1;
    //地圖資源
    TiledMapTileLayer foregroundLayer;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private int[] background = new int[] {0};
    private int[] foreground = new int[] {1};



    public PlayScreen2(Game game){
        this.game=game;
        init();
    }

    public void init(){
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);



        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);//設置鏡頭大小
        camera.setToOrtho(false, w, h);//y軸向上
        camera.update();

        batch = new SpriteBatch();
        deltaTime = 0.0f;
        xPosition = 0 ;

        // Load atlases and textures
//        hero1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/RunningRight.pack"));
        hero1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/heroShana.pack"));


        TextureRegion[] frameWalkingRight = new TextureRegion[8];
        frameWalkingRight[0] = hero1Atlas.findRegion("RunningRight1");
        frameWalkingRight[1] = hero1Atlas.findRegion("RunningRight2");
        frameWalkingRight[2] = hero1Atlas.findRegion("RunningRight3");
        frameWalkingRight[3] = hero1Atlas.findRegion("RunningRight4");
        frameWalkingRight[4] = hero1Atlas.findRegion("RunningRight5");
        frameWalkingRight[5] = hero1Atlas.findRegion("RunningRight6");
        frameWalkingRight[6] = hero1Atlas.findRegion("RunningRight7");
        frameWalkingRight[7] = hero1Atlas.findRegion("RunningRight8");

        // Load animations
//        animation = new Animation(FRAME_DURATION, hero1Atlas.getRegions(), Animation.PlayMode.LOOP);
        animation = new Animation(FRAME_DURATION, frameWalkingRight);

        HUDBatch = new SpriteBatch();
        font1 = new BitmapFont();
        font1.setColor(Color.YELLOW);

        tiledMap = new TmxMapLoader().load("map/newmap2.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get("foreground");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update deltaTime & animationTime
        deltaTime = Gdx.graphics.getDeltaTime();
        animationTime += Gdx.graphics.getDeltaTime();

        //Store Spritesheet to sprite
        sprite = new Sprite(animation.getKeyFrame(animationTime, true));
        sprite.setPosition(xPosition,0);
        sprite.setScale(1.8f);

        //Set camera to batch and undate camera
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(background);
        tiledMapRenderer.render(foreground);


        //Display on Screen
        batch.begin();
        sprite.draw(batch);
        batch.end();

        //update xPosition
        xPosition = xPosition + (speed*deltaTime);

        HUDBatch.begin();
        font1.draw(HUDBatch, "SCORE:100", 100, 450);
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
        texture.dispose();
        batch.dispose();
        hero1Atlas.dispose();
        HUDBatch.dispose();
        font1.dispose();
    }
}

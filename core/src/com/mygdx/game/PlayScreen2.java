package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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




    public PlayScreen2(Game game){
        this.game=game;
        init();
    }

    public void init(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();
        deltaTime = 0.0f;
        xPosition = -(SCENE_WIDTH/2) ;

        // Load atlases and textures
        hero1Atlas = new TextureAtlas(Gdx.files.internal("hero/shana/RunningRight.pack"));

        // Load animations
        animation = new Animation(FRAME_DURATION, hero1Atlas.getRegions(), Animation.PlayMode.LOOP);

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

        //Set camera to batch and undate camera
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        //Display on Screen
        batch.begin();
        sprite.draw(batch);
        batch.end();

        //update xPosition
        xPosition = xPosition + (speed*deltaTime);

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
    }
}

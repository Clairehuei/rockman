package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by User on 2015/10/26.
 */
public class PlayBase implements Screen {

    float deltaTime = 0.0f;//每偵間格時間(因應手機效能有所不同)
    Game game;
    HomeScreen homeScreen;//返回城鎮(關卡選擇)
    //偵測手機螢幕的寬/高
    float screenWidth;
    float screenHeight;

    //地圖資源
    TiledMapTileLayer foregroundLayer;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    float stageWidth = 0.0f;//背景地圖的長度(寬)

    Rhero hero;//英雄人物

    public void init () {
        Gdx.app.log("==PlayBase.init()===", "start init()");
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }


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

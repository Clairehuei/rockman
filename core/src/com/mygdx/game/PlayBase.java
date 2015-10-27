package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by User on 2015/10/26.
 */
public class PlayBase implements Screen {

    //每偵間格時間(因應手機效能有所不同)
    private float deltaTime = 0.0f;

    Game game;
    HomeScreen homeScreen;//返回城鎮(關卡選擇)

    public void init () {
        Gdx.app.log("==PlayBase.init()===", "start init()");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

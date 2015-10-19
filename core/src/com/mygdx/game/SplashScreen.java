package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**應用程式初始開場動畫
 * Created by 6193 on 2015/10/19.
 */
public class SplashScreen implements Screen {
    private Texture texture=new Texture(Gdx.files.internal("splashscreen.png"));
    private Image splashImage=new Image(texture);
    private Stage stage=new Stage();
    MenuScreen menuScreen;
    Game game;


    public SplashScreen(Game game){
        this.game=game;
        splashImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0)
                , Actions.fadeIn(4.0f), Actions.delay(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                menuScreen=new MenuScreen(game);
                game.setScreen(menuScreen);
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();//更新所有Actor
        stage.draw();//把所有Actor顯示在螢幕上
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("===SplashScreen===width = "+width+"   height = "+height);
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();

    }

}

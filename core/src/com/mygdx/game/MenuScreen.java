package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**應用程式開始畫面
 * Created by 6193 on 2015/10/19.
 */
public class MenuScreen implements Screen, InputProcessor {
    private Texture texture = new Texture(Gdx.files.internal("menuscreen.jpg"));
    private Texture touchTxtTexture = new Texture(Gdx.files.internal("ui/touchScreen.png"));
    private Image splashImage = new Image(texture);
    private Image touchImage = new Image(touchTxtTexture);
    private Stage stage = new Stage();
    SpriteBatch batch = new SpriteBatch();
    Game game;
    HomeScreen homeScreen;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();

    public MenuScreen(){
    }

    public MenuScreen(Game game){
        this.game = game;
        splashImage.setSize(screenWidth, screenHeight);
    }

    @Override
    public void show() {
        //設定遊戲登入畫面背景
        stage.addActor(splashImage);

        //設定提示玩家點擊螢幕之訊息
        touchImage.setPosition(screenWidth / 2 - touchImage.getWidth() / 2, screenHeight / 4 - touchImage.getHeight());
        stage.addActor(touchImage);
        //加入閃爍效果
        touchImage.addAction(Actions.forever(Actions.sequence(
                                                             Actions.alpha(0),
                                                             Actions.fadeIn(1.0f),
                                                             Actions.delay(0.5f),
                                                             Actions.fadeOut(1.0f),
                                                             Actions.alpha(0)
                                                           )
                                           )
                             );

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();//更新所有Actor
        stage.draw();//把所有Actor顯示在螢幕上
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("===MenuScreen===width = " + width + "   height = " + height);
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
        batch.dispose();
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        homeScreen = new HomeScreen(game);
        game.setScreen(homeScreen);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}

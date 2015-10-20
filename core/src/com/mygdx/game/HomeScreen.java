package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**主要畫面/選關卡(城鎮)
 * Created by 6193 on 2015/10/19.
 */
public class HomeScreen implements Screen {

    private Texture texture=new Texture(Gdx.files.internal("home.png"));
    private Image splashImage=new Image(texture);
    private Image imageStage1, imageStage2, imageStage3, imageStage4, imageStage5;
    private Stage stage = new Stage();
    SpriteBatch batch = new SpriteBatch();
    Game game;

//    PlayScreen1 playScreen1;
    PlayScreenTest playScreenTest;
    PlayScreen2 playScreen2;

    private TextureAtlas stage1Atlas;
    private TextureRegion stage1, stage2, stage3, stage4, stage5;

    public HomeScreen(){
    }

    public HomeScreen(Game game){
        this.game=game;
        inti();
    }

    public void inti(){
        splashImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage1Atlas = new TextureAtlas(Gdx.files.internal("stage/stage.pack"));
        stage1 = stage1Atlas.findRegion("stage1");
        stage2 = stage1Atlas.findRegion("stage2");
        stage3 = stage1Atlas.findRegion("stage3");
        stage4 = stage1Atlas.findRegion("stage4");
        stage5 = stage1Atlas.findRegion("stage5");



        imageStage1 = new Image(stage1);
        imageStage2 = new Image(stage2);
        imageStage3 = new Image(stage3);
        imageStage4 = new Image(stage4);
        imageStage5 = new Image(stage5);

        imageStage1.setPosition(0, Gdx.graphics.getHeight()-imageStage1.getHeight());
        imageStage2.setPosition(imageStage3.getWidth()*1, Gdx.graphics.getHeight()-imageStage2.getHeight());
        imageStage3.setPosition(imageStage3.getWidth()*2, Gdx.graphics.getHeight()-imageStage3.getHeight());
        imageStage4.setPosition(imageStage3.getWidth()*3, Gdx.graphics.getHeight()-imageStage4.getHeight());
        imageStage5.setPosition(imageStage3.getWidth()*4, Gdx.graphics.getHeight()-imageStage5.getHeight());

        imageStage1.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                playScreen1 = new PlayScreen1(game);
//                game.setScreen(playScreen1);

                playScreenTest = new PlayScreenTest(game);
                game.setScreen(playScreenTest);

                return true;
            }
        });

        imageStage2.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playScreen2 = new PlayScreen2(game);
                game.setScreen(playScreen2);

                return true;
            }
        });
    }

    @Override
    public void show() {
        stage.addActor(splashImage);
        stage.addActor(imageStage1);
        stage.addActor(imageStage2);
        stage.addActor(imageStage3);
        stage.addActor(imageStage4);
        stage.addActor(imageStage5);
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
}

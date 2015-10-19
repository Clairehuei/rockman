package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Created by 6193 on 2015/10/19.
 */
public class GameMain extends Game  {
    SplashScreen splashScreen;

    @Override
    public void create () {
        splashScreen=new SplashScreen(this);
        setScreen(splashScreen);
    }

}

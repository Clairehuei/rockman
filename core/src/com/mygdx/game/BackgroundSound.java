package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by 6193 on 2015/10/16.
 */
public class BackgroundSound {
    Sound sound;
    public BackgroundSound(){
        sound = Gdx.audio.newSound(Gdx.files.internal("sound/bgsound1.mp3"));
        sound.loop(0.5f);
    }

}

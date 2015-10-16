package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by 6193 on 2015/10/16.
 */
public class GunSound {
    Sound sound;
    public GunSound(){

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/gun1.mp3"));
        sound.play(0.5f);
    }

}

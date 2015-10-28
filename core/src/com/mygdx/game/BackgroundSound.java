package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by 6193 on 2015/10/16.
 */
public class BackgroundSound {

    public Music music;
    public BackgroundSound(){

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/bgsound2.ogg"));//.ogg的音檔loop才沒有問題,並且檔案大小也比.mp3還小(使用.map會有問題,貌似loop上限,會自動停止)
        music.setLooping(true);
        music.play();
    }

}
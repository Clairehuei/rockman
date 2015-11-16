package com.mygdx.game.fly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**夏娜的劍氣01
 * Created by 6193 on 2015/11/16.
 */
public class Fly01 extends FlyImpl {

    public TextureAtlas fly1Atlas;//圖檔資源
    private static final float FRAME_DURATION_FLY = 1.0f / 15.0f;//氣功波的播放速度

    public Fly01(){
        init();
    }

    public Fly01 (Vector2 bulletPosition, int bullVelocityX)
    {
        TempbulletPosition = new Vector2(bulletPosition.x, bulletPosition.y);
        TempbulletVelocityX = bullVelocityX;
        init();
    }

    @Override
    public void update(){
        TempbulletPosition.x += TempbulletVelocityX;
    }

    public void init(){
        //讀取氣功圖檔資源
        fly1Atlas = new TextureAtlas(Gdx.files.internal("fly/hero/heroFly.pack"));

        TextureRegion[] frameFlyRight = new TextureRegion[6];
        frameFlyRight[0] = fly1Atlas.findRegion("aFlyRight1");
        frameFlyRight[1] = fly1Atlas.findRegion("aFlyRight2");
        frameFlyRight[2] = fly1Atlas.findRegion("aFlyRight3");
        frameFlyRight[3] = fly1Atlas.findRegion("aFlyRight4");
        frameFlyRight[4] = fly1Atlas.findRegion("aFlyRight5");
        frameFlyRight[5] = fly1Atlas.findRegion("aFlyRight6");
        animationFlyRight = new Animation(FRAME_DURATION_FLY, frameFlyRight);


        TextureRegion[] frameFlyLeft = new TextureRegion[6];
        frameFlyLeft[0] = fly1Atlas.findRegion("aFlyLeft1");
        frameFlyLeft[1] = fly1Atlas.findRegion("aFlyLeft2");
        frameFlyLeft[2] = fly1Atlas.findRegion("aFlyLeft3");
        frameFlyLeft[3] = fly1Atlas.findRegion("aFlyLeft4");
        frameFlyLeft[4] = fly1Atlas.findRegion("aFlyLeft5");
        frameFlyLeft[5] = fly1Atlas.findRegion("aFlyLeft6");
        animationFlyLeft = new Animation(FRAME_DURATION_FLY, frameFlyLeft);
    }

}

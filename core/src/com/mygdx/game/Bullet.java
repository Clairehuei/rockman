package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by 6193 on 2015/10/16.
 */
public class Bullet {
    public Vector2 TempbulletPosition ;
    public int TempbulletVelocityX;

    public Bullet (Vector2 bulletPosition, int bullVelocityX)
    {
        TempbulletPosition = new Vector2(bulletPosition.x, bulletPosition.y);
        TempbulletVelocityX = bullVelocityX;
    }

    public void update(){
        TempbulletPosition.x += TempbulletVelocityX;
    }

}

package com.mygdx.game.fly;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**實作飛行介面的抽象類別
 * Created by 6193 on 2015/11/16.
 */
public abstract class FlyImpl implements BaseFly {
    public int TempbulletVelocityX;
    public Vector2 TempbulletPosition ;
    public TextureRegion flyFrame;//當前飛行物件的畫面
    public Animation animationFlyRight;//氣功(右)動畫
    public Animation animationFlyLeft;//氣功(左)動畫

    //*********************************setter/getter***************************************
    public TextureRegion getFlyFrame() {
        return flyFrame;
    }

    public void setFlyFrame(TextureRegion flyFrame) {
        this.flyFrame = flyFrame;
    }
    public Animation getAnimationFlyRight() {
        return animationFlyRight;
    }
    public void setAnimationFlyRight(Animation animationFlyRight) {
        this.animationFlyRight = animationFlyRight;
    }
    public Animation getAnimationFlyLeft() {
        return animationFlyLeft;
    }
    public void setAnimationFlyLeft(Animation animationFlyLeft) {
        this.animationFlyLeft = animationFlyLeft;
    }
}

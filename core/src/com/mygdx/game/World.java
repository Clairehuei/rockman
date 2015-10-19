package com.mygdx.game;

import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by 6193 on 2015/10/19.
 */
public class World<T> {

    //相当于我们游戏世界的大小。只是为了方便控制人物大小，移动速度等
    //手机屏幕划分为10*6方格,具体每个方格大小不知道
    private final float WORLD_WIDTH = 10f;
    private final float WORLD_HEIGHT = 6f;
    //也就是把屏幕分成10*6网格后每个单元格的x，y轴的像素数
    //下面的方法中会根据屏幕大小自动计算出
    private float pixelsX;
    private float pixelsY;

    private int width;
    private int height;

    public float getWorldWidth() {
        return WORLD_WIDTH;
    }

    public float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    /**
     * 屏幕的实际宽度
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 屏幕的实际高度
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }
    //计算出每个方格的像素数
    //例如800*480的屏幕 800/10 480/6  每单位x,y像素为80的方格
    //这样方便我们控制世界中的物体的大小和比例

    public void setPixelsXY(int width, int height) {
        this.width = width;
        this.height = height;
        pixelsX = (float) width / WORLD_WIDTH;
        pixelsY = (float) height / WORLD_HEIGHT;
    }

    public float getPixelsX() {
        return this.pixelsX;
    }

    public float getPixelsY() {
        return this.pixelsY;
    }

    ArrayMap<String, T> actors = new ArrayMap<String, T>();


    public void addActors(String name, T actor) {

        actors.put(name, actor);

    }

    public T getActors(String name) {
        return actors.get(name);
    }
}

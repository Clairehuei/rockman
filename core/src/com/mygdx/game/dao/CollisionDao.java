package com.mygdx.game.dao;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**碰撞邏輯
 * Created by 6193 on 2015/10/28.
 */
public class CollisionDao {

    //碰撞方向判斷
    public boolean collisionLeft;
    public boolean collisionRight;
    public boolean collisionBottom;
    public boolean collisionTop;

    //與背景地圖障礙物碰撞參數
    float x1, y1, i1, j1;
    int tempcount, tempcount1;

    //地圖資訊
    TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer.Cell cell;
    private String collisionKey = "blocked";//障礙物屬性


    /**
     * 判斷是否為障礙物
     * @param x
     * @param y
     * @return
     */
    private	boolean isCellBlocked( float x, float y ){
        cell = foregroundLayer.getCell( (int)x, (int)y);
        if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(collisionKey)){
            return  true;
        }
        return false;
    }


    public	boolean collisionLeft(float x, float y) {
        tempcount=0;
        for (int i = 32 ; i<=64; i +=32){
            i1 = new Float(i);
            x1 = (x-5)/32;
            y1 = ((y+i1)/32);
            if (isCellBlocked(x1, y1)){
                tempcount = tempcount+1;
            }
        }
        if (tempcount > 0)
            collisionLeft = true;
        return false;
    }


    public	boolean collisionRight(float x, float y) {
        tempcount=0;
        for (int i = 32 ; i<=64; i +=32){
            i1 = new Float(i);
            x1 = (x+64)/32;
            y1 = ((y+i1)/32);
            if (isCellBlocked(x1, y1)){
                tempcount = tempcount+1;
            }
        }
        if (tempcount > 0)
            collisionRight = true;
        return false;
    }


    public float collisionBottom(float x, float y, boolean isFacingRight, float v0) {
        tempcount1=0;
        collisionBottom = false;
        float v = v0;

        if (!isFacingRight){
            for (int j = 32 ; j<=64; j +=32){
                j1 = new Float(j);
                x1 = (x+j1)/32;
                y1 = (y/32);
                if (isCellBlocked(x1, y1)){
                    tempcount1 = tempcount1+1;
                }
                if (tempcount1 > 0){
                    collisionBottom = true;
                    v = 300.0f;
                }

            }
        }
        else {
            for (int j = 0 ; j<=32; j +=32){
                j1 = new Float(j);
                x1 = (x+j1)/32;
                y1 = (y/32);
                if (isCellBlocked(x1, y1)){
                    tempcount1 = tempcount1+1;
                }
                if (tempcount1 > 0){
                    collisionBottom = true;
                    v = 300.0f;
                }

            }
        }
        return v;
    }


    public	boolean collisionTop(float x, float y) {
        tempcount1=0;
        collisionTop = false;
        x1 = (x+32)/32;
        y1 = (y+64)/32;
        if (isCellBlocked(x1, y1)){
            tempcount1 = tempcount1+1;
        }
        if (tempcount1 > 0)
            collisionTop = true;
        return false;
    }


    /**
     * 判斷自己是否碰觸目標
     * @param x
     * @param y
     * @return
     */
    public	int isTouchBoss(float x, float y, int selfW, int selfH ,float targetX, float targetY, int targetW, int targetH){
        // 0:未碰觸   1:從左邊碰觸(self(碰觸)target)   2:從右邊碰觸(target(碰觸)self)
        if(x + selfW >= targetX-5 && x + selfW <= targetX){
            return 1;
        }else if(x-5 <= targetX + targetW && x + selfW >= targetX + targetW){
            return 2;
        }else{
            return 0;
        }
    }


    public	int isBeAttack(float x, float y, int selfW, int selfH ,float targetX, float targetY, int targetW, int targetH){
        // 0:未碰觸   1:從左邊碰觸(self(碰觸)target)   2:從右邊碰觸(target(碰觸)self)
        if(x + selfW >= targetX-5 && x + selfW <= targetX){
            return 1;
        }else if(x-5 <= targetX + targetW && x + selfW >= targetX + targetW){
            return 2;
        }else{
            return 0;
        }
    }


    public	int serchTarget(float x, float y, int selfW, int selfH ,float targetX, float targetY, int serchRange){
        // 0:未碰觸   1:從左邊碰觸(self(碰觸)target)   2:從右邊碰觸(target(碰觸)self)
        if(x + selfW >= targetX-serchRange && x + selfW <= targetX){
            return 1;
        }else if(x-serchRange <= targetX && x + selfW >= targetX){
            return 2;
        }else{
            return 0;
        }
    }


    public String getCollisionKey() {
        return collisionKey;
    }

    public void setCollisionKey(String collisionKey) {
        this.collisionKey = collisionKey;
    }

    public TiledMapTileLayer getForegroundLayer() {
        return foregroundLayer;
    }

    public void setForegroundLayer(TiledMapTileLayer foregroundLayer) {
        this.foregroundLayer = foregroundLayer;
    }

}

package com.acupt.world;

/**
 * Created by liujie on 2018/3/3.
 */
public class Bean {

    private String wxid;

    private World world;

    private int x;

    private int y;

    public Bean() {
    }

    public Bean(String wxid, World world) {
        this.wxid = wxid;
        this.world = world;
    }

    public Point point() {
        return world.getPoint(x, y);
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

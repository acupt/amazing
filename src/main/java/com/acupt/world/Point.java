package com.acupt.world;

/**
 * Created by liujie on 2018/3/2.
 */
public class Point {

    private PointOrigin origin;

    public Point() {
    }

    public Point(PointOrigin origin) {
        this.origin = origin;
    }

    public String getMsg() {
        return origin.name();
    }

    public PointOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(PointOrigin origin) {
        this.origin = origin;
    }


}

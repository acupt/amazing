package com.acupt.world;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujie on 2018/3/2.
 */
public class World {

    private int size;

    private Point[][] pointMap;

    private Map<String, Bean> wxidBeanMap = new ConcurrentHashMap<>();

    public World(int size) {
        this.size = size;
        pointMap = new Point[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pointMap[i][j] = new Point(PointOrigin.create());
            }
        }
    }

    public Point move(String wxid, String order) throws WorldException {
        Bean bean = wxidBeanMap.getOrDefault(wxid, new Bean(wxid, this));
        if (order == null) {
            throw new WorldException("你要爪子");
        }
        order = order.toLowerCase().trim();
        if ("w".endsWith(order)) {
            if (bean.getY() + 1 >= size) {
                return null;
            }
            bean.setY(bean.getY() + 1);
        } else if ("s".endsWith(order)) {
            if (bean.getY() - 1 < 0) {
                return null;
            }
            bean.setY(bean.getY() - 1);
        } else if ("a".endsWith(order)) {
            if (bean.getX() - 1 < 0) {
                return null;
            }
            bean.setX(bean.getX() - 1);
        } else if ("d".endsWith(order)) {
            if (bean.getX() + 1 >= size) {
                return null;
            }
            bean.setX(bean.getX() + 1);
        } else {
            throw new WorldException("你要爪子");
        }
        return pointMap[bean.getX()][bean.getY()];
    }

    public Point getPoint(int x, int y) {
        return pointMap[x][y];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point[][] getPointMap() {
        return pointMap;
    }

    public void setPointMap(Point[][] pointMap) {
        this.pointMap = pointMap;
    }

}

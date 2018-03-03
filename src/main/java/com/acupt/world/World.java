package com.acupt.world;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujie on 2018/3/2.
 */
public class World {

    private int size;

    private Point[][] pointMap;

    private Map<String, Bean> wxidBeanMap = new ConcurrentHashMap<>();

    private static Set<String> moveOrder = new HashSet<>();

    private static String MSG_OUT_RANGE = "此路不通";

    static {
        moveOrder.add("w");
        moveOrder.add("s");
        moveOrder.add("a");
        moveOrder.add("d");
    }

    public World(int size) {
        this.size = size;
        pointMap = new Point[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pointMap[i][j] = new Point(PointOrigin.create());
            }
        }
    }

    public Bean move(String wxid, String order) throws WorldException {
        Bean bean = getBean(wxid);
        if (order == null) {
            throw new WorldException("你要爪子");
        }
        order = order.toLowerCase().trim();
        if ("w".endsWith(order)) {
            if (bean.getY() + 1 >= size) {
                throw new WorldException(MSG_OUT_RANGE);
            }
            bean.setY(bean.getY() + 1);
        } else if ("s".endsWith(order)) {
            if (bean.getY() - 1 < 0) {
                throw new WorldException(MSG_OUT_RANGE);
            }
            bean.setY(bean.getY() - 1);
        } else if ("a".endsWith(order)) {
            if (bean.getX() - 1 < 0) {
                throw new WorldException(MSG_OUT_RANGE);
            }
            bean.setX(bean.getX() - 1);
        } else if ("d".endsWith(order)) {
            if (bean.getX() + 1 >= size) {
                throw new WorldException(MSG_OUT_RANGE);
            }
            bean.setX(bean.getX() + 1);
        } else {
            throw new WorldException("你要爪子");
        }
        return bean;
    }

    public String map(String wxid) {
        int msize = 4;
        Bean bean = getBean(wxid);
        int x1 = 0;
        int x2 = size - 1;
        int y1 = 0;
        int y2 = size - 1;
        if (bean.getX() - msize / 2 < 0) {
            x1 = 0;
            x2 = msize;
        } else if (bean.getX() + msize / 2 >= size) {
            x2 = size - 1;
            x1 = x2 - msize;
        } else {
            x1 = bean.getX() - msize / 2;
            x2 = bean.getX() + msize / 2;
        }
        if (bean.getY() - msize / 2 < 0) {
            y1 = 0;
            y2 = msize;
        } else if (bean.getY() + msize / 2 >= size) {
            y2 = size - 1;
            y1 = y2 - msize;
        } else {
            y1 = bean.getY() - msize / 2;
            y2 = bean.getY() + msize / 2;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = y2; j >= 0; j--) {
            for (int i = x1; i <= x2; i++) {
                if (i == bean.getX() && j == bean.getY()) {
                    sb.append("我");
                } else {
                    sb.append(getPoint(i, j).getOrigin().getName());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static boolean isMoveOrder(String o) {
        if (o == null) {
            return false;
        }
        return moveOrder.contains(o.toLowerCase());
    }

    public Bean getBean(String wxid) {
        Bean bean = wxidBeanMap.getOrDefault(wxid, new Bean(wxid, this));
        wxidBeanMap.put(wxid, bean);
        return bean;
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

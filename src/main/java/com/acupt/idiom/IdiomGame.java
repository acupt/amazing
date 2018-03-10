package com.acupt.idiom;

import com.acupt.domain.Result;
import com.acupt.entity.Idiom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by liujie on 2018/3/10.
 */
public class IdiomGame {

    private String id;

    private Map<String, List<Idiom>> firstSpellMap = new HashMap<>();

    private List<Idiom> godRecord = new ArrayList<>();

    private List<Idiom> guyRecord = new ArrayList<>();

    private long lastTime;

    private int errTime;

    public Idiom getLastGodRecord() {
        if (godRecord.isEmpty()) {
            return null;
        }
        return godRecord.get(godRecord.size() - 1);
    }

    public void startWith(Idiom idiom) {
        lastTime = System.currentTimeMillis();
        godRecord.add(idiom);
        List<Idiom> list = firstSpellMap.get(idiom.getCharSpell()[0]);
        list.remove(idiom);
    }

    public Result<String> next(String word) {
        if ("认输".equals(word)) {
            over("我也不想这么棒棒啊");
        }
        if (!isValid()) {
            return over("我等到花儿都谢了");
        }
        lastTime = System.currentTimeMillis();
        if (!isManTime()) {
            return error("慌个锤子，这个词无效");
        }
        Idiom idiom = IdiomFactory.getByWord(word);
        if (idiom == null) {
            return error("这个词不认识，我觉得不OK");
        }
        Idiom godIdiom = godRecord.get(godRecord.size() - 1);
        if (!godIdiom.getCharSpell()[godIdiom.getCharSpell().length - 1].equals(idiom.getCharSpell()[0])) {
            return error("对仗不工整，我觉得不OK");
        }
        if (godRecord.contains(idiom)) {
            return over("我已经说过了");
        }
        if (guyRecord.contains(idiom)) {
            return over("你已经说过了");
        }
        List<Idiom> list = firstSpellMap.get(idiom.getCharSpell()[idiom.getCharSpell().length - 1]);
        if (list == null || list.isEmpty()) {
            return over("我竟无言以对，是老子输了");
        }
        Idiom godRes = list.get(0);
        list.remove(godRes);
        godRecord.add(godRes);
        guyRecord.add(idiom);
        errTime = 0;
        return new Result<>(0, godRes.getWord());
    }

    private Result<String> error(String msg) {
        errTime++;
        if (errTime >= 3) {
            return new Result<>(2, msg + "，我建议你输入'认输'");
        }
        return new Result<>(2, msg);
    }

    private Result<String> over(String msg) {
        return new Result<>(1, msg + "，游戏结束，你得了" + guyRecord.size() + "分");
    }

    private boolean isValid() {
        return System.currentTimeMillis() - lastTime < 30000;
    }

    public boolean isGodTime() {
        return !isManTime();
    }

    public boolean isManTime() {
        return godRecord.size() > guyRecord.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<Idiom>> getFirstSpellMap() {
        return firstSpellMap;
    }

    public void setFirstSpellMap(Map<String, List<Idiom>> firstSpellMap) {
        this.firstSpellMap = firstSpellMap;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public static void main(String[] args) {
        IdiomGame game = IdiomFactory.newGame("lj");
        System.out.println(game.getLastGodRecord().getWord());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine().trim();
            Result<String> res = game.next(s);
            System.out.println(res.getMsg());
            if (res.getCode() == 1) {
                break;
            }

        }
    }
}

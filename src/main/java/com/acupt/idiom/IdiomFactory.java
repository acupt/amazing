package com.acupt.idiom;

import com.acupt.entity.Idiom;
import com.acupt.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujie on 2018/3/10.
 */
public class IdiomFactory {

    private static List<Idiom> list = new ArrayList<>(42000);

    private static Map<String, Idiom> wordMap = new HashMap<>();

    private static Map<String, IdiomGame> gameMap = new ConcurrentHashMap<>();

    static {
        load();
    }

    public static Idiom getByWord(String w) {
        return wordMap.get(w);
    }

    public static IdiomGame newGame(String id) {
        IdiomGame game = new IdiomGame();
        game.setId(id);
        for (Idiom i : list) {
            List<Idiom> charList = game.getFirstSpellMap().get(i.getCharSpell()[0]);
            if (charList == null) {
                charList = new ArrayList<>();
                game.getFirstSpellMap().put(i.getCharSpell()[0], charList);
            }
            charList.add(i);
        }
        int i = (int) (Math.random() * list.size());
        game.startWith(list.get(i));
        gameMap.put(game.getId(), game);
        return game;
    }

    public static IdiomGame get(String id) {
        return gameMap.get(id);
    }

    public static IdiomGame remove(String id) {
        return gameMap.remove(id);
    }

    private static synchronized void load() {
        InputStream is = IdiomFactory.class.getClassLoader().getResourceAsStream("data/idiom.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                Idiom r = parse(tempString);
                if (r != null) {
                    wordMap.put(r.getWord(), r);
                    list.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static Idiom parse(String s) {
        if (StringUtil.isBlank(s)) {
            return null;
        }
        s = s.trim();
        String sep = "|";
        s = s.replace("拼音：", sep).replace("释义：", sep).replace("出处：", sep).replace("示例：", sep);
        String[] fields = s.split("\\|");
        if (fields.length != 5) {
            return null;
        }
        Idiom record = new Idiom();
        record.setWord(fields[0].trim());
        record.setSpell(fields[1].trim());
        record.setCharSpell(record.getSpell().split(" "));
        record.setParaphrase(fields[2]);
        record.setOrigin(fields[3]);
        record.setExample(fields[4]);
        return record;
    }
}

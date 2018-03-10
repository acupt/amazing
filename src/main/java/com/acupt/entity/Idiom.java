package com.acupt.entity;

import java.util.Arrays;

/**
 * Created by liujie on 2018/3/10.
 */
public class Idiom {
    private String word;
    private String spell;
    private String[] charSpell;
    private String paraphrase;
    private String origin;
    private String example;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String[] getCharSpell() {
        return charSpell;
    }

    public void setCharSpell(String[] charSpell) {
        this.charSpell = charSpell;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "Idiom{" +
                "word='" + word + '\'' +
                ", spell='" + spell + '\'' +
                ", charSpell=" + Arrays.toString(charSpell) +
                ", paraphrase='" + paraphrase + '\'' +
                ", origin='" + origin + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}

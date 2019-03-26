package com.example.mohea.aba_ep1_words.set;

public class CheckSet {

    int num;
    String score;
    String rate;

    public CheckSet(int num, String score, String rate) {
        this.num = num;
        this.score = score;
        this.rate = rate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

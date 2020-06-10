package com.example.demo.common;

import java.util.ArrayList;
import java.util.List;

public class Answer {
    private int score;
    private List<Integer>  whereWrong;

    public int getScore() {
        return score;
    }

    public List<Integer> getWhereWrong() {
        return whereWrong;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWhereWrong(List<Integer> whereWrong) {
        this.whereWrong = whereWrong;
    }

    public Answer(int score, List<Integer> whereWrong) {
        this.score = score;
        this.whereWrong = whereWrong;
    }

    public Answer(int score) {
        this.score = score;
        this.whereWrong = new ArrayList<Integer>();
    }

    public void pushW(int x){
        this.whereWrong.add(x);
    }

    public void addScore(){
        this.score++;
    }

    public void relocate(){this.score=0;this.whereWrong.clear();}
}

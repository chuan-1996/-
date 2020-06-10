package com.example.demo.common;

public class multi {
    private int end;
    private boolean flag;

    public int getEnd() {
        return end;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "multi{" +
                "end=" + end +
                ", flag=" + flag +
                '}';
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public multi(int end, boolean flag) {
        this.end = end;
        this.flag = flag;
    }
}

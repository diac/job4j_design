package ru.job4j.assertj;

public class Model {

    private final int top;
    private final double num;
    private final String line;
    private final boolean condition;

    public Model(int top, double num, String line, boolean condition) {
        this.top = top;
        this.num = num;
        this.line = line;
        this.condition = condition;
    }

    public int getTop() {
        return top;
    }

    public double getNum() {
        return num;
    }

    public String getLine() {
        return line;
    }

    public boolean isCondition() {
        return condition;
    }
}

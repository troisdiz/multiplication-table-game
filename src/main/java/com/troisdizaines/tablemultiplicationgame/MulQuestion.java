package com.troisdizaines.tablemultiplicationgame;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MulQuestion {
    private int int1;
    private int int2;
    private String answer;
    private boolean resultOK;
    private long time;

    public MulQuestion(int int1, int int2, String answer, boolean resultOK, long time) {
        this.int1     = int1;
        this.int2     = int2;
        this.answer   = answer;
        this.time     = time;
        this.resultOK = resultOK;
    }

    public int getInt1() {
        return int1;
    }

    public int getInt2() {
        return int2;
    }

    public String getAnswer() {
        return answer;
    }

    public long getTime() {
        return time;
    }

    public boolean isResultOK() {
        return resultOK;
    }

    public String render() {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        String vraiFaux = isResultOK() ? "VRAI" : "FAUX";
        double timeSec = getTime() / 1000000000d;
        printWriter.printf("%2d x %2d => %s (%s) en %2.2fs", getInt1(), getInt2(), answer, vraiFaux);
        return printWriter.toString();
    }
}

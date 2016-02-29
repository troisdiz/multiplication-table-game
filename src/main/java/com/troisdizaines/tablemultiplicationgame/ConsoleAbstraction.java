package com.troisdizaines.tablemultiplicationgame;

public interface ConsoleAbstraction {

    public void printf(String txt, Object... args);
    public String promptAndGetLine(String txt, Object... args);
}

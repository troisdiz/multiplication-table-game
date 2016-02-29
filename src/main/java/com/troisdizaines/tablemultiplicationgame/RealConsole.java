package com.troisdizaines.tablemultiplicationgame;

import java.io.Console;

public class RealConsole implements ConsoleAbstraction {

    private Console console;

    public RealConsole() {
        console = System.console();
    }

    @Override
    public void printf(String txt, Object ... args) {
        console.writer().printf(txt, args);
    }

    @Override
    public String promptAndGetLine(String txt, Object...args) {
        return console.readLine(txt, args);
    }
}

package com.troisdizaines.tablemultiplicationgame.scores;

import java.time.LocalDateTime;

public class GameLog {

    private String name;
    private long duration;
    private LocalDateTime startDate;

    public GameLog(String name, long duration, LocalDateTime startDate) {
        this.name = name;
        this.duration = duration;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}

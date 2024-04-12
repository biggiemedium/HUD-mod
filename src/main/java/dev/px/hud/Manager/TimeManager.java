package dev.px.hud.Manager;

public class TimeManager {

    private long totalElapsed;
    private long playtimeElapsed;

    public TimeManager() {
        this.totalElapsed = -1;
        this.playtimeElapsed = -1;
    }

    public long getTotalElapsed() {
        return totalElapsed;
    }

    public void setTotalElapsed(long totalElapsed) {
        this.totalElapsed = totalElapsed;
    }

    public long getPlaytimeElapsed() {
        return playtimeElapsed;
    }

    public void setPlaytimeElapsed(long playtimeElapsed) {
        this.playtimeElapsed = playtimeElapsed;
    }
}

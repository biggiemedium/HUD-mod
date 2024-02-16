package dev.px.hud.Util.API.Math;

public class Timer {

    private long time;

    public Timer() {
        this.time = -1L;
    }

    public boolean passed(long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getTime(long time) {
        return time / 1000000L;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}

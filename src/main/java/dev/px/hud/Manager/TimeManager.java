package dev.px.hud.Manager;

public class TimeManager {

    private long totalElapsed;
    private long playtimeElapsed;

    public TimeManager() {
        this.totalElapsed = -1;
        this.playtimeElapsed = -1;
    }

    // Stack overflow :)
    public String getTotalDuration(long mills) {
        int hours, minutes, remainder, totalSecondsNoFraction;
        double totalSeconds, seconds;

        totalSeconds = (double) mills / 1000000000.0;
        String s = Double.toString(totalSeconds);
        String [] arr = s.split("\\.");
        totalSecondsNoFraction = Integer.parseInt(arr[0]);
        hours = totalSecondsNoFraction / 3600;
        remainder = totalSecondsNoFraction % 3600;
        minutes = remainder / 60;
        seconds = remainder % 60;
        if(arr[1].contains("E")) seconds = Double.parseDouble("." + arr[1]);
        else seconds += Double.parseDouble("." + arr[1]);

        StringBuilder result = new StringBuilder(".");
        String sep = "", nextSep = " and ";
        if(seconds > 0)
        {
            result.insert(0, " seconds").insert(0, seconds);
            sep = nextSep;
            nextSep = ", ";
        }
        if(minutes > 0)
        {
            if(minutes > 1) result.insert(0, sep).insert(0, " minutes").insert(0, minutes);
            else result.insert(0, sep).insert(0, " minute").insert(0, minutes);
            sep = nextSep;
            nextSep = ", ";
        }
        if(hours > 0)
        {
            if(hours > 1) result.insert(0, sep).insert(0, " hours").insert(0, hours);
            else result.insert(0, sep).insert(0, " hour").insert(0, hours);
        }
        return result.toString();
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

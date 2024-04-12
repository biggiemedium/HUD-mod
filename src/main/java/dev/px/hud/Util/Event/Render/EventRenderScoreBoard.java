package dev.px.hud.Util.Event.Render;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class EventRenderScoreBoard extends Event {

    private ScoreObjective scoreboard;

    public EventRenderScoreBoard(ScoreObjective scoreboard) {
        this.scoreboard = scoreboard;
    }

    public ScoreObjective getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(ScoreObjective scoreboard) {
        this.scoreboard = scoreboard;
    }
}

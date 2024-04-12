package dev.px.hud.Rendering.HUD.Elements.Render;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class ScoreboardElement extends RenderElement {

    private Scoreboard scoreboard;

    public ScoreboardElement() {
        super("Scoreboard", 5, 200, 200, 300, HUDType.RENDER);
    }

    @Override
    public void render(float partialTicks) {
        this.scoreboard = mc.theWorld.getScoreboard();

    }

}

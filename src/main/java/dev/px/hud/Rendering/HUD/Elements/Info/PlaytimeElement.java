package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class PlaytimeElement extends RenderElement {

    public PlaytimeElement() {
        super("Playtime", 25, 100, HUDType.INFO);
        setTextElement(true);
        setHeight(70);
        setWidth(90);
    }

    @Override
    public void render2D(Render2DEvent event) {

        GL11.glPushMatrix();
        RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 4, new Color(42, 41, 41, 175));
        RoundedShader.drawRound(getX(), getY(), getWidth(), 18, 4, new Color(35, 35, 35, 175));
        RoundedShader.drawRound(getX(), getY() + 17, getWidth(), 2, 0, new Color(35, 35, 35, 175));
        Renderutil.drawBlurredShadow(getX(), getY(), getWidth(), getHeight(), 10, new Color(42, 41, 41, 125));
        GL11.glPopMatrix();

        renderText("Session", getX() + 2, getY() + 2, -1);
        renderText("Total Elapsed " + HUDMod.timeManager.getTotalDuration(HUDMod.timeManager.getTotalElapsed()), getX() + 2, getY() + 3 + getFontHeight());
        renderText("Playtime ", getX() + 2, getY() + 3 + (getFontHeight() * 2));
    }
}

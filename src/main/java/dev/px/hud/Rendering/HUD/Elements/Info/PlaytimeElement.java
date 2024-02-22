package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;

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

        RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 4, new Color(42, 41, 41, 175));
        RoundedShader.drawRound(getX(), getY(), getWidth(), 18, 4, new Color(35, 35, 35, 175));
        RoundedShader.drawRound(getX(), getY() + 17, getWidth(), 2, 0, new Color(35, 35, 35, 175));
        Renderutil.drawBlurredShadow(getX(), getY(), getWidth(), getHeight(), 10, new Color(42, 41, 41, 125));

        renderText("Session", getX() + ((getWidth() / 2)), getY() + 2, -1);


    }
}

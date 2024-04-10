package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;

import java.util.Objects;

public class PingElement extends RenderElement {

    public PingElement() {
        super("Ping Element", 5, 75, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render2D(Render2DEvent event) {
        int ping = 0;
        try {
            int responseTime = Objects.requireNonNull(mc.getNetHandler()).getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
            ping = responseTime;
        } catch (Exception responseTime) {}

        drawBackground();
        renderText("Ping: " + ping, getX(), getY(), fontColor.getValue().getRGB());
        setWidth(getFontWidth("Ping: " + ping));
    }
}

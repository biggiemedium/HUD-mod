package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeElement extends RenderElement {

    public TimeElement() {
        super("Time", 60, 60, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render2D(Render2DEvent event) {
        renderText(new SimpleDateFormat("kk:mm").format(new Date()), getX(), getY());
    }

}

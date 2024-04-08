package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Settings.Setting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeElement extends RenderElement {

    public TimeElement() {
        super("Time", 60, 60, HUDType.INFO);
        setTextElement(true);
    }
    Setting<Boolean> tfHour = create(new Setting<>("24 Hr", false));
    Setting<Boolean> date = create(new Setting<>("Date", false));

    @Override
    public void render2D(Render2DEvent event) {
        DateFormat dateFormat = tfHour.getValue() ? new SimpleDateFormat("kk:mm") : new SimpleDateFormat("hh:mm a");

        renderText(dateFormat.format(new Date()), getX(), getY());
        setWidth(getFontWidth(dateFormat.format(new Date())));
    }

}

package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;

public class WatermarkElement extends RenderElement {

    public WatermarkElement() {
        super("WaterMark", 1, 1, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render(float partialTicks) {
        renderText(HUDMod.NAME + " " + HUDMod.VERSION, getX(), getY(), fontColor.getValue().getRGB());
        setHeight(getFontHeight());
        setWidth(getFontWidth(HUDMod.NAME + " " + HUDMod.VERSION));
    }
}

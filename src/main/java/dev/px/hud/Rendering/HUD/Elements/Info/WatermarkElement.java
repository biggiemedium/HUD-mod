package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Settings.Setting;

public class WatermarkElement extends RenderElement {

    public WatermarkElement() {
        super("WaterMark", 1, 1, HUDType.INFO);
        setTextElement(true);
    }

    private Setting<Boolean> name = create(new Setting<>("Name", false));
    private Setting<Boolean> version = create(new Setting<>("Version", true));

    @Override
    public void render(float partialTicks) {
        renderText(display(), getX(), getY(), fontColor.getValue().getRGB());
        setHeight(getFontHeight());
        setWidth(getFontWidth(display()));
    }

    private String display() {
        String s = HUDMod.NAME;

        if(version.getValue()) {
            s += " | " + HUDMod.VERSION;
        }
        if(name.getValue()) {
            s += " | " + mc.thePlayer.getDisplayNameString();
        }
        return s;
    }

}

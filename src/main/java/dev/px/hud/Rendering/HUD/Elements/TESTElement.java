package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Elements.Combat.Armor;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.Settings.Setting;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, HUDType.INFO);
        setVisible(true);
    }

    private enum Mode {
        UP, SIDE
    }

    @Override
    public void render(float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow("Test Element", getX(), getY(), -1);
        Fontutil.drawTextShadow("Test", (float) getX(),  (float) getY() + (float) Fontutil.getHeight(), -1);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }
}

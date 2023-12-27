package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.Element;

public class TESTElement extends Element {

    public TESTElement() {
        super("Test Element", 20, 20, HUDType.INFO);
        setVisible(true);
    }

    @Override
    public void render(float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow("Test Element", getX(), getY(), -1);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }
}

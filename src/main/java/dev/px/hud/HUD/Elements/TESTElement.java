package dev.px.hud.HUD.Elements;

import dev.px.hud.HUD.Element;

public class TESTElement extends Element {

    public TESTElement() {
        super("Test Element", 20, 20, Category.MISC);
    }

    @Override
    public void render(float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow("Test Element", getX(), getY(), -1);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
    }
}

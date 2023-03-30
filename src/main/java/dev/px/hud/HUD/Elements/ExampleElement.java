package dev.px.hud.HUD.Elements;

import dev.px.hud.HUD.Element;

public class ExampleElement extends Element {

    public ExampleElement() {
        super("ExampleElement", 1, 1, Category.MISC);
    }

    @Override
    public void render(float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow("Example Element", getX(), getY(), -1);
        this.setWidth(mc.fontRendererObj.getStringWidth("Example Element"));
    }
}

package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;

public class TargetHUD extends RenderElement {

    public TargetHUD() {
        super("TargetHUDD", 200, 200, HUDType.COMBAT);
        setWidth(50);
        setHeight(50);
    }

    @Override
    public void render(float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow("Target HUD", getX(), getY(), -1);
    }
}

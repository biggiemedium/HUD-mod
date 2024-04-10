package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Rendering.HUD.RenderElement;
import net.minecraft.client.Minecraft;

public class FPSElement extends RenderElement {

    public FPSElement() {
        super("FPS", 2, 100, HUDType.INFO, true);
    }

    @Override
    public void render(float partialTicks) {
        drawBackground();
        renderText("FPS: " + Minecraft.getDebugFPS(), getX(), getY(), fontColor.getValue().getRGB());
        setWidth(getFontWidth("FPS: " + Minecraft.getDebugFPS()));
        setHeight(getFontHeight());
    }

}

package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Font.Fontutil;
import net.minecraft.network.play.client.C0APacketAnimation;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, HUDType.INFO);
        setTextElement(true);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }

    private enum Mode {
        UP, SIDE
    }

    @Override
    public void render(float partialTicks) {
        if(customFont.getValue()) {
            Fontutil.drawTextShadow("Test", (float) getX(),  (float) getY() + (float) Fontutil.getHeight(), -1);
        } else {
            mc.fontRendererObj.drawStringWithShadow("Test Element", getX(), getY(), -1);
        }

        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }
}

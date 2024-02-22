package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ItemStackElement extends RenderElement {

    public ItemStackElement() {
        super("Item stack", 50, 50, 25, 25, HUDType.COMBAT);
        setTextElement(false);
    }

    @Override
    public void render2D(Render2DEvent event) {

        if(mc.thePlayer.inventory.getCurrentItem() != null) {
            ItemStack stack = mc.thePlayer.inventory.getCurrentItem();

            RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 1, new Color(42, 41, 41, 125));
            Renderutil.drawBlurredShadow(getX(), getY(), getWidth(), getHeight(), 10, new Color(42, 41, 41, 125));

        }

    }

}

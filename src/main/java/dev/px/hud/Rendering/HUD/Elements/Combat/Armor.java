package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import scala.Int;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Armor extends RenderElement {

    public Armor() {
        super("Armor", 200, 200, HUDType.COMBAT);
    }

    Setting<Mode> mode = create(new Setting<Mode>("Mode", Mode.UP));
    Setting<Integer> scale = create(new Setting<Integer>("Scale", 1, 0, 5));

    @Override
    public void render(float partialTicks) {
        GlStateManager.enableTexture2D();

        int iteration = 0;

        for(int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            iteration++;
            ItemStack is = mc.thePlayer.inventory.armorInventory[i];
            if(i == 0) {
                continue;
            }

            int x = (int) (getX() - 90 + (9 - iteration) * 20 + 2);
            GlStateManager.enableDepth();
            Renderutil.itemRender.zLevel = 200.0F;
            Renderutil.itemRender.renderItemAndEffectIntoGUI(is, x, (int) getY());
            Renderutil.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, (int) getY(), "");
            Renderutil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    private void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();

        GlStateManager.disableAlpha();
        mc.getRenderItem().zLevel = -150.0F;

        GlStateManager.disableCull();

        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y);

        GlStateManager.enableCull();

        mc.getRenderItem().zLevel = 0;

        GlStateManager.disableBlend();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        GlStateManager.disableDepth();
        GlStateManager.disableLighting();

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();

        GlStateManager.scale(2.0F, 2.0F, 2.0F);

        GlStateManager.enableAlpha();

        GlStateManager.popMatrix();
    }

    private enum Mode {
        UP,
        SIDE
    }
}

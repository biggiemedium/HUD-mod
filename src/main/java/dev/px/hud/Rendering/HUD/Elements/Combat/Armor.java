package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class Armor extends RenderElement {

    public Armor() {
        super("Armor", 200, 200, HUDType.COMBAT);
    }

    Setting<Mode> mode = create(new Setting<Mode>("Mode", Mode.UP));
    Setting<Integer> scale = create(new Setting<Integer>("Scale", 1, 0, 5));

    @Override
    public void render2D(Render2DEvent event) {

        GlStateManager.enableTexture2D();

        int iteration = 0;

        for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            iteration++;
            if (i == 0) {
                continue;
            }
            ItemStack is = mc.thePlayer.inventory.armorItemInSlot(i);

            int x = (int) (getX() - 90 + (9 - iteration) * 20 + 2);
            GlStateManager.enableDepth();
            Renderutil.itemRender.zLevel = 200.0F;
            Renderutil.itemRender.renderItemAndEffectIntoGUI(is, x, (int) getY());
            Renderutil.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, (int) getY(), "");
            Renderutil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = (is.stackSize > 1) ? (is.stackSize + "") : "";
            mc.fontRendererObj.drawStringWithShadow(s, (x + 19 - 2 - mc.fontRendererObj.getStringWidth(s)), (getY() + 9), 16777215);
            int dmg = (int) calculatePercentage(is);
            mc.fontRendererObj.drawStringWithShadow(dmg + "", (x + 8 - mc.fontRendererObj.getStringWidth(dmg + "") / 2f), (getY() - 11), new Color(0, 255, 0).getRGB());
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

    public static float calculatePercentage(ItemStack stack) {
        float durability = stack.getMaxDamage() - stack.getItemDamage();
        return (durability / (float) stack.getMaxDamage()) * 100F;
    }

    private enum Mode {
        UP,
        SIDE
    }
}

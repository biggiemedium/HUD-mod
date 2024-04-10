package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Armor extends RenderElement {

    public Armor() {
        super("Armor", 200, 200, HUDType.COMBAT);
    }

    Setting<Mode> mode = create(new Setting<Mode>("Mode", Mode.UP));
    Setting<Integer> scale = create(new Setting<Integer>("Scale", 1, 0, 5));

    @Override
    public void render2D(Render2DEvent event) {
        for(int i = 1; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            ItemStack stack = mc.thePlayer.inventory.armorInventory[i];

            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            int xPos = mode.getValue() == Mode.SIDE ? getX() + (-16 * -1 + 48) : getX() + 2;
            int yPos = mode.getValue() == Mode.UP ? getY() + (-16 * -1 + 48) : getY() + 2;
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, xPos, yPos);
            //  mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, lastStack, getX() + 3, getY() + (getHeight() / 2) - 7, null);
            RenderHelper.disableStandardItemLighting();
            mc.getRenderItem().zLevel = 0.0F;
            GL11.glPopMatrix();
        }
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

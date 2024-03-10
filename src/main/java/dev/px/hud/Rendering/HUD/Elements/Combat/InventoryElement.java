package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class InventoryElement extends RenderElement {

    public InventoryElement() {
        super("Inventory", 2, 15, 16 * 9, 16 * 3, HUDType.COMBAT);
        setTextElement(false);
    }

    public Setting<Boolean> background = create(new Setting<>("Background", true));
    public Setting<Float> scale = create(new Setting<>("Scale", 1.0f, 0.0f, 2.0f));

    @Override
    public void render2D(Render2DEvent event) {

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.scale(scale.getValue(), scale.getValue(), scale.getValue());
        if (background.getValue())
            Renderutil.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x75101010); // background
        for (int i = 0; i < 27; i++)
        {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i + 9];

            int offsetX = (int) getX() + (i % 9) * 16;
            int offsetY = (int) getY() + (i / 9) * 16;
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}

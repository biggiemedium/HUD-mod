package dev.px.hud.HUD.Elements.Combat;

import dev.px.hud.HUD.Element;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class Armor extends Element {

    public Armor() {
        super("Armor", 200, 200, Category.COMBAT);
    }

    @Override
    public void render(float partialTicks) {
        GlStateManager.enableTexture2D();
        int x = 0;
        for(ItemStack i : mc.thePlayer.inventory.armorInventory) {
            x++;

            int x_2 = 0;
            int y_2 = 0;

            x_2 = (int)this.getX() - 90 + (9 - x) * 20 + 2 - 12;
            y_2 = (int)this.getY();

            //if(i.stackSize < 0) {
            //    continue;
            //}

            mc.getRenderItem().zLevel = (float) 200;
            mc.getRenderItem().renderItemAndEffectIntoGUI(i, (this.getX() - 70) + x , this.getY());
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, i, (this.getX() - 70) + x, this.getY(), "");
            mc.getRenderItem().zLevel = 0.0f;

            setWidth(getX() - 8);
            setHeight(17);

            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
}

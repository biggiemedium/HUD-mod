package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import scala.Int;

public class Armor extends Element {

    public Armor() {
        super("Armor", 200, 200, HUDType.COMBAT);
    }

    Setting<Boolean> vertical = create(new Setting<Boolean>("Vertical", true));
    Setting<Mode> mode = create(new Setting<Mode>("Mode", Mode.UP));
    Setting<Boolean> glint = create(new Setting<Boolean>("Glint", false, v -> vertical.getValue()));
    Setting<Integer> scale = create(new Setting<Integer>("Scale", 1, 0, 5));

    @Override
    public void render(float partialTicks) {
        GlStateManager.pushMatrix();
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
            GlStateManager.popMatrix();
        }
    }

    private enum Mode {
        UP,
        SIDE
    }
}

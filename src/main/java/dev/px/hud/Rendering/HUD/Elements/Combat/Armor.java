package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import scala.Int;

public class Armor extends RenderElement {

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
        int offset = 0;
        for (ItemStack itemStack : mc.thePlayer.inventory.armorInventory) {
            offset++;

            if (itemStack == null)
                continue;

            int x = ((9 - offset) * 14);

            float durabilityScaled = ((float) (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (float) itemStack.getMaxDamage()) * 100.0f;

            int color = 0x1FFF00;

            if (durabilityScaled > 30 && durabilityScaled < 70)
                color = 0xFFFF00;
            else if (durabilityScaled <= 30)
                color = 0xFF0000;

            mc.getRenderItem().zLevel = 200.0f;
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (getX() - 70) + x , getY());
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack, (getX() - 70) + x, getY(), "");
            mc.getRenderItem().zLevel = 0.0f;
            setWidth(x - 8);
            setHeight(17);
        }

        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
            GlStateManager.popMatrix();
    }

    private enum Mode {
        UP,
        SIDE
    }
}

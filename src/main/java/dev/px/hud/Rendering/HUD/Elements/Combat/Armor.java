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

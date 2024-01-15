package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import scala.Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Armor extends RenderElement {

    public Armor() {
        super("Armor", 200, 200, HUDType.COMBAT);
    }

    Setting<Mode> mode = create(new Setting<Mode>("Mode", Mode.UP));
    Setting<Boolean> glint = create(new Setting<Boolean>("Glint", false));
    Setting<Integer> scale = create(new Setting<Integer>("Scale", 1, 0, 5));

    @Override
    public void render(float partialTicks) {



    }

    private enum Mode {
        UP,
        SIDE
    }
}

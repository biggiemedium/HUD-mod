package dev.px.hud.Rendering.HUD.Elements.Info;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Chatutil;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class HitInfoElement extends RenderElement {

    public HitInfoElement() {
        super("Hit Info", 100, 100, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Boolean> ogTheme = create(new Setting<>("OG Theme", true, v -> !rainbowText.getValue()));

    @Override
    public void render(float partialTicks) {
        super.render(partialTicks);

        renderText(getEName(), getX(), getY(), fontColor.getValue().getRGB());

        setWidth(getFontWidth(getEName()));
        setHeight(getFontHeight());
    }

    public String getEName() {
        if(mc.objectMouseOver.entityHit != null) {
            Entity name = mc.objectMouseOver.entityHit;
            if(name instanceof EntityPlayer) {
                String s = ogTheme.getValue() ? name.getName() + " " + ChatFormatting.GOLD + Entityutil.getHealth((EntityPlayer) name) + ChatFormatting.RESET : name.getName() + " " + Entityutil.getHealth((EntityPlayer) name);
                return s;
            }
        }

        if(mc.currentScreen instanceof PanelGUIScreen) {
            return "Target Name 20.0";
        }

        return "";
    }

}

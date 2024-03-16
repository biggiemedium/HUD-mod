package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Mods.ChatModifications;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui {

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void overrideChatBackgroundColour(int left, int top, int right, int bottom, int color) {
        Element e = HUDMod.elementInitalizer.getElementByClass(ChatModifications.class);
        if(e != null) {
            if(e.isToggled() && HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).clearChat.getValue()) {
                Gui.drawRect(left, top, right, bottom, new Color(0, 0, 0, 0).getRGB());
            }
        }
    }



}

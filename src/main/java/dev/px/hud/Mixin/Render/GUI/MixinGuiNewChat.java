package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Mods.ChatModifications;
import dev.px.hud.Util.Event.Client.ChatReceiveEvent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui {

    @Inject(method = "printChatMessage", at = @At("TAIL"), cancellable = true)
    public void onChatReceive(IChatComponent chatComponent, CallbackInfo ci) {
        ChatReceiveEvent event = new ChatReceiveEvent(chatComponent);
        if(event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void overrideChatBackgroundColour(int left, int top, int right, int bottom, int color) {
        Element e = HUDMod.elementInitalizer.getElementByClass(ChatModifications.class);
        if(e != null) {
            if(e.isToggled() && HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).clearChat.getValue()) {
                Gui.drawRect(left, top, right, bottom, new Color(0, 0, 0, 0).getRGB());
            } else {
                Gui.drawRect(left, top, right, bottom, color);
            }
        } else {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }


}

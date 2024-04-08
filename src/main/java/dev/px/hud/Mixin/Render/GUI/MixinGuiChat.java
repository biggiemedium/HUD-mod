package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.ChatModifications;
import dev.px.hud.Util.API.Animation.SimpleAnimation;
import dev.px.hud.Util.API.Render.GlUtils;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.gui.GuiChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiChat.class)
public class MixinGuiChat {

    private SimpleAnimation animation = new SimpleAnimation(0.0F);


    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void drawScreenPre(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        if(HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).isToggled() && HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).bar.getValue()) {
            animation.setAnimation(30, 20);
            GlUtils.startTranslate(0, 29 - (int) animation.getValue());
           // Renderutil.drawOutlineRect(2, (float) this.height - (14 * animation.getValue()), (float) this.width - 2, (float) this.height - 2, 1, Integer.MIN_VALUE);
        }
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreenPost(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if(HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).isToggled() && HUDMod.elementInitalizer.getElementByClass(ChatModifications.class).bar.getValue()) {
            GlUtils.stopTranslate();
        }
    }

}

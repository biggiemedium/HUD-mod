package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.RenderImageEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGUIIngame {

    @Inject(method = "renderTooltip", at = @At("RETURN"))
    public void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        RenderImageEvent event = new RenderImageEvent(sr, partialTicks);
        if(event.isCanceled()) {
            ci.cancel();
        }
    }
}

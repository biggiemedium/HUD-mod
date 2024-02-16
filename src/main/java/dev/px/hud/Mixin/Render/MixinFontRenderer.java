package dev.px.hud.Mixin.Render;

import dev.px.hud.Rendering.MCGUI.HUDMenuGUI;
import dev.px.hud.Util.API.Font.Fontutil;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    public void drawString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> info) {
        if(HUDMenuGUI.getInstance.isTextOverrided()) {
            info.setReturnValue(Fontutil.drawStringWithShadowInt(text, x, y, color));
        }
    }

}

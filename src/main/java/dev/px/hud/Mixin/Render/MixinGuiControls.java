package dev.px.hud.Mixin.Render;

import net.minecraft.client.gui.GuiControls;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiControls.class)
public class MixinGuiControls {

    @Inject(method = "initGui", at = @At("TAIL"), cancellable = true)
    public void render(CallbackInfo info) {

    }

}

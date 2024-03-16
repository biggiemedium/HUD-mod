package dev.px.hud.Mixin.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.BlockHighlight;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobals {

    @Inject(method = "drawSelectionBox", at = @At("HEAD"), cancellable = true)
    public void renderCrossheir(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks, CallbackInfo info) {
        if(HUDMod.elementInitalizer.getElementByClass(BlockHighlight.class) != null) {
            if(HUDMod.elementInitalizer.isElementToggled(BlockHighlight.class)) {
                info.cancel();
            }
        }
    }

}

package dev.px.hud.Mixin.Render.Item;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.ItemPhysics;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {


    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if(HUDMod.elementInitalizer.getElementByClass(ItemPhysics.class) != null) {
            if(HUDMod.elementInitalizer.isElementToggled(ItemPhysics.class)) {
                HUDMod.elementInitalizer.getElementByClass(ItemPhysics.class).itemPhysic(entity, x, y, z);
                ci.cancel();
            }
        }

    }

}

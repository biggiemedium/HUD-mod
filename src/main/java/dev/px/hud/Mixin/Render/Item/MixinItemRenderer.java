package dev.px.hud.Mixin.Render.Item;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.OldAnimations;
import dev.px.hud.Util.Event.Render.RenderItemHeldEvent;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer implements Wrapper {
/*
    @Inject(method = "transformFirstPersonItem", at = @At("HEAD"), cancellable = true)
    public void onTransformPre(float equipProgress, float swingProgress, CallbackInfo ci) {

    }

    @Inject(method = "doBowTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void preBowScale(CallbackInfo ci) {
        if (HUDMod.elementInitalizer.isElementToggled(OldAnimations.class)) {
            if(HUDMod.elementInitalizer.getElementByClass(OldAnimations.class).bow.getValue()) {
                GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.5F, 0.0F);
            }
        }
    }

    @Inject(method = "doBowTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V", shift = At.Shift.AFTER))
    private void postBowScale(CallbackInfo ci) {
        if (HUDMod.elementInitalizer.isElementToggled(OldAnimations.class)) {
            if (HUDMod.elementInitalizer.getElementByClass(OldAnimations.class).bow.getValue()) {
                GlStateManager.translate(0.0F, -0.5F, 0.0F);
                GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }


 */
}

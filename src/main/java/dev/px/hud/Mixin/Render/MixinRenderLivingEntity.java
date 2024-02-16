package dev.px.hud.Mixin.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.NameTags;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRenderLivingEntity<T extends Entity> extends Render<T> {

    protected MixinRenderLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    /*

    @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
    private void handleBetterF1AndShowOwnNametag(T entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!HUDMod.elementInitalizer.isElementToggled(NameTags.class));
    }

     */

    /*
    @Redirect(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private void patcher$useShadowedNametagRendering(T entity, int x, int y, int color) {


    }

     */

}

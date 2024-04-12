package dev.px.hud.Mixin.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.NameTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRenderLivingEntity <T extends EntityLivingBase> extends Render<T> {

    protected MixinRenderLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
    private void handleBetterF1AndShowOwnNametag(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (!Minecraft.isGuiEnabled()) {
            cir.setReturnValue(false);
        }
        if(HUDMod.elementInitalizer.isElementToggled(NameTags.class)) {
            cir.setReturnValue(false);
        }
        else if (entity == renderManager.livingPlayer && !entity.isInvisible()) { cir.setReturnValue(true); }
    }

    /*
    @Redirect(method = "canRenderName", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    public Entity canRenderName(RenderManager renderManager) {
        if(HUDMod.elementInitalizer.getElementByClass(NameTags.class).isToggled()) {
            return null;
        }
        return renderManager.livingPlayer;
    }

     */

}
package dev.px.hud.Mixin.Render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(RenderPlayer.class)
public class MixinRendererLivingEntity {

    @Inject(method = "renderName", at = @At("HEAD"), cancellable = true)
    public <T> void onNameRender(AbstractClientPlayer entity, double x, double y, double z) {

    }

}

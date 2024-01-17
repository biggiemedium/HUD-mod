package dev.px.hud.Mixin.Render;

import dev.px.hud.Util.Event.RenderNametagEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {

    @Inject(method = "renderOffsetLivingLabel", at = @At("HEAD"), cancellable = true)
    public <T> void onNameRender(AbstractClientPlayer entity, double x, double y, double z, String str, float p_177069_9_, double distanceSq, CallbackInfo in) {
        RenderNametagEvent packet = new RenderNametagEvent(entity, x, y, z);
        MinecraftForge.EVENT_BUS.post(packet);
        if(packet.isCanceled()) {
            in.cancel();
        }
    }
}

package dev.px.hud.Mixin.Render;

import dev.px.hud.Util.Event.Render.RenderChunkEvent;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkRenderContainer.class)
public class MixinRenderChunkContainer {

    @Inject(method = "preRenderChunk", at = @At(value = "RETURN", target = "Lnet/minecraft/client/renderer/chunk/RenderChunk;getPosition()Lnet/minecraft/util/math/BlockPos/MutableBlockPos;"))
    private void preRenderChunk(RenderChunk renderChunk, CallbackInfo callbackInfo) {
        MinecraftForge.EVENT_BUS.post(new RenderChunkEvent.RenderChunkContainerEvent(renderChunk));
    }

}

package dev.px.hud.Mixin.Render;

import dev.px.hud.Util.Event.Render.RenderChunkEvent;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderChunk.class)
public class MixinRenderChunk {

    @Inject(method = "setPosition", at = @At("RETURN"))
    public void setPosition(BlockPos pos, CallbackInfo callbackInfo) {
        MinecraftForge.EVENT_BUS.post(new RenderChunkEvent((RenderChunk) (Object) this, pos));
    }
}

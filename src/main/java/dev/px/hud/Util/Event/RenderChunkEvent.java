package dev.px.hud.Util.Event;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderChunkEvent extends Event {

    private RenderChunk chunk;
    private BlockPos pos;

    public RenderChunkEvent(RenderChunk chunk, BlockPos pos) {
        this.chunk = chunk;
        this.pos = pos;
    }

    public RenderChunk getChunk() {
        return chunk;
    }

    public void setChunk(RenderChunk chunk) {
        this.chunk = chunk;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Cancelable
    public static class RenderChunkContainerEvent extends Event {
        private RenderChunk renderChunk;

        public RenderChunkContainerEvent(RenderChunk chunk) {
            this.renderChunk = chunk;
        }

        public RenderChunk getRenderChunk() {
            return renderChunk;
        }

        public void setRenderChunk(RenderChunk renderChunk) {
            this.renderChunk = renderChunk;
        }
    }
}

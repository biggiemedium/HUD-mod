package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render.RenderChunkEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChunkAnimator extends ToggleableElement {

    public ChunkAnimator() {
        super("Chunk Animator", "Animates chunks at edge of render distance", HUDType.MOD);
    }

    Setting<Integer> animation = create(new Setting<>("Animation time", 1000, 250, 10000));
    Setting<Boolean> easing = create(new Setting<>("Easing", true));

    private WeakHashMap<RenderChunk, AtomicLong> lifespans = new WeakHashMap<>();

    @SubscribeEvent
    public void onChunkRender(RenderChunkEvent event) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            if (!lifespans.containsKey(event.getChunk())) {
                lifespans.put(event.getChunk(), new AtomicLong(-1L));
            }
        }
    }

    @SubscribeEvent
    public void onContainerRender(RenderChunkEvent.RenderChunkContainerEvent event) {
        if (lifespans.containsKey(event.getRenderChunk())) {
            AtomicLong timeAlive = lifespans.get(event.getRenderChunk());
            long timeClone = timeAlive.get();
            if (timeClone == -1L) {
                timeClone = System.currentTimeMillis();
                timeAlive.set(timeClone);
            }

            long timeDifference = System.currentTimeMillis() - timeClone;
            if (timeDifference <= animation.getValue()) {
                double chunkY = event.getRenderChunk().getPosition().getY();
                double offsetY = chunkY / animation.getValue() * timeDifference;
                if (easing.getValue()) {
                    offsetY = chunkY * easeOutCubic(timeDifference / animation.getValue().doubleValue());
                }
                GlStateManager.translate(0.0, -chunkY + offsetY, 0.0);
            }
        }
    }

    private double easeOutCubic(double t) {
        return (--t) * t * t + 1;
    }
}

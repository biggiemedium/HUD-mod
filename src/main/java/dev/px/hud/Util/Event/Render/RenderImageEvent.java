package dev.px.hud.Util.Event.Render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderImageEvent extends Event {

    private ScaledResolution scaledResolution;
    private float partialTicks;

    public RenderImageEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

}

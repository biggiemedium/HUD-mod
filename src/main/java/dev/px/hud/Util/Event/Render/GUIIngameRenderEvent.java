package dev.px.hud.Util.Event.Render;

import net.minecraftforge.fml.common.eventhandler.Event;

public class GUIIngameRenderEvent extends Event {

    private float partialTicks;

    public GUIIngameRenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

}

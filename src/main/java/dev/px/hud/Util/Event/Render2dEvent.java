package dev.px.hud.Util.Event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class Render2dEvent extends Event {

    private float partialTicks;

    public Render2dEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

}

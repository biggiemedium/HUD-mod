package dev.px.hud.Util.Event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class Render3dEvent extends Event {

    private float partialTicks;

    public Render3dEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

}

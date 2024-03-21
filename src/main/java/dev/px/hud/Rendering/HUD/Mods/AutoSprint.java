package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Playerutil;
import dev.px.hud.Util.API.Util;

public class AutoSprint extends ToggleableElement {

    public AutoSprint() {
        super("Auto Sprint", "Automatically runs", HUDType.MOD);
    }

    @Override
    public void enable() {
        if(Util.isNull()) return;
    }

    @Override
    public void disable() {
        if(Util.isNull()) return;
        mc.thePlayer.setSprinting(false);
    }

    public void onUpdate() {
        if(Util.isNull()) return;
        // These return statements are so that I dont get banned while playing
        if(mc.thePlayer.moveForward <= 0 && mc.thePlayer.moveStrafing > 0) return; // if only moving to side
        if(mc.thePlayer.moveForward < 0) return; // backwards
        mc.thePlayer.setSprinting(Playerutil.isMoving());
    }
}

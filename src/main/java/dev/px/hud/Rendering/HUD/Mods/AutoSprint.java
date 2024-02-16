package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Playerutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.ClientUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        mc.thePlayer.setSprinting(Playerutil.isMoving());
    }
}

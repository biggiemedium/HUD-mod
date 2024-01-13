package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.ClientUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoSprint extends ToggleableElement {

    public AutoSprint() {
        super("Auto Sprint", "Automatically runs", HUDType.COMBAT);
    }

}

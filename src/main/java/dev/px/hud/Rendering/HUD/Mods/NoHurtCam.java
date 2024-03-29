package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render.HurtCamEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoHurtCam extends ToggleableElement {

    public NoHurtCam() {
        super("NoHurtCam", "", HUDType.RENDER);
    }

    @Override
    public void onUpdate() {

    }

    @SubscribeEvent
    public void onHurtCam(HurtCamEvent event) {
        event.setCanceled(true);
    }

}

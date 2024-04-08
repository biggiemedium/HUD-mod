package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;

public class FullBright extends ToggleableElement {

    public FullBright() {
        super("Full Bright", "Lets you see in the dark", HUDType.RENDER);
    }

    @Override
    public void enable() {
         mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onUpdate() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void disable() {
        mc.gameSettings.gammaSetting = 0;
    }
}

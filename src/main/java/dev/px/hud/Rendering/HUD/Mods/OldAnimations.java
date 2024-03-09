package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class OldAnimations extends ToggleableElement {

    public OldAnimations() {
        super("Old Animations", HUDType.MOD);
        INSTANCE = this;
    }

    public Setting<Boolean> sword = create(new Setting<>("Sword", true));

    public static OldAnimations INSTANCE;
}

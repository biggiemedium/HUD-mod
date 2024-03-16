package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class OldAnimations extends ToggleableElement {

    public OldAnimations() {
        super("Old Animations", HUDType.MOD);
        INSTANCE = this;
    }

    public Setting<Boolean> sword = create(new Setting<>("Sword", true));
    public Setting<Boolean> bow = create(new Setting<>("Bow", true));
    public Setting<Boolean> eat = create(new Setting<>("Eat", true));
    public Setting<Boolean> rod = create(new Setting<>("Rod", true));

    public static OldAnimations INSTANCE;
}

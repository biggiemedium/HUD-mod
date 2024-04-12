package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.Elements.TestToggleElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class HotbarModifications extends ToggleableElement {

    public HotbarModifications() {
        super("Hotbar Mods", HUDType.MOD);
    }

    public Setting<Boolean> animation = create(new Setting<>("Animation", true));
    public Setting<Integer> speed = create(new Setting<>("Speed", 17, 10, 30));
    public Setting<HotbarMode> mode = create(new Setting<>("Mode", HotbarMode.Round));

    public enum HotbarMode {
        Vanilla,
        Clear,
        Skid,
        Round
    }
}

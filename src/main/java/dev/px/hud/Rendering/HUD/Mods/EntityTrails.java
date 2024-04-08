package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class EntityTrails extends ToggleableElement {

    public EntityTrails() {
        super("Entity Trails", HUDType.RENDER);
    }

    Setting<Boolean> arrows = create(new Setting<>("Arrows", true));
    Setting<Boolean> pearls = create(new Setting<>("Pearls", true));

}

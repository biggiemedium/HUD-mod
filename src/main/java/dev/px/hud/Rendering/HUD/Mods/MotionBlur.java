package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.entity.Render;

public class MotionBlur extends ToggleableElement {

    public MotionBlur() {
        super("Motion Blur", "Motion Blur", HUDType.RENDER);
    }

    public Setting<Float> amount = create(new Setting<>("Amount", 0.5f, 0.1f, 0.85f));

}

package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class ChatModifications extends ToggleableElement {

    public ChatModifications() {
        super("Chat Modifications", HUDType.MOD);
    }

    public Setting<Boolean> clearChat = create(new Setting<>("Clear Chat", true));
    public Setting<Boolean> fade = create(new Setting<>("Fade", true));
    public Setting<Integer> speed = create(new Setting<>("Fade speed", 4, 1, 10, v -> fade.getValue()));
    // public Setting<Boolean> infChat = create(new Setting<>("Infinite length", true));

}

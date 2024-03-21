package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class ChatModifications extends ToggleableElement {

    public ChatModifications() {
        super("Chat Modifications", HUDType.MOD);
    }

    public Setting<Boolean> clearChat = create(new Setting<>("Clear Chat", true));
   // public Setting<Boolean> infChat = create(new Setting<>("Infinite length", true));

}

package dev.px.hud.Util.API.Input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class BindRegistry {

    public static KeyBinding guiKey;

    public static void register() {
        guiKey = new KeyBinding("HUD Mod GUI Key", Keyboard.KEY_RSHIFT, "Hud Mod");
        ClientRegistry.registerKeyBinding(guiKey);
    }

}

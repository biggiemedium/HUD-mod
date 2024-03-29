package dev.px.hud.Util.API;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class BindRegistry {

    public static KeyBinding guiKey;
    public static KeyBinding newGui;

    public static void register() {
        guiKey = new KeyBinding("HUD Mod GUI Key", Keyboard.KEY_RSHIFT, "Hud Mod");
        ClientRegistry.registerKeyBinding(guiKey);
        newGui = new KeyBinding("Updated GUI", Keyboard.KEY_NONE, "Hud Mod");
        ClientRegistry.registerKeyBinding(newGui);
    }

}

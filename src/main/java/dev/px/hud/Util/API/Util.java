package dev.px.hud.Util.API;

import dev.px.hud.Util.Wrapper;
import net.minecraft.util.ChatComponentText;

public class Util implements Wrapper {

    public static void sendClientSideMessage(String text) {
        if(mc.thePlayer != null && mc.theWorld != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(text));
        }
    }
}

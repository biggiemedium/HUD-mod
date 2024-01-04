package dev.px.hud.Util.API;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.util.ChatComponentText;

public class Util implements Wrapper {

    private static ChatFormatting RESET = ChatFormatting.RESET;
    private static ChatFormatting GRAY = ChatFormatting.GRAY;
    private static ChatFormatting AQUA = ChatFormatting.AQUA;

    public static void sendClientSideMessage(String text) {
        if(mc.thePlayer != null && mc.theWorld != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(text));
        }
    }

    public static void sendClientSideMessage(String text, boolean watermark) {
        if(mc.thePlayer != null && mc.theWorld != null) {
            String s = watermark ? GRAY + "[" + RESET + AQUA + "HUD Mod" + RESET + GRAY + "] " + RESET + text : text;
            mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(s));
        }
    }

    public static boolean isNull() {
        return mc.thePlayer == null || mc.theWorld == null;
    }
}

package dev.px.hud.Util.API;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.ChatComponentText;

public class Chatutil extends Util {

    private static ChatFormatting RESET = ChatFormatting.RESET;
    private static ChatFormatting GRAY = ChatFormatting.GRAY;
    private static ChatFormatting AQUA = ChatFormatting.AQUA;

    /*
    Method doesnt work
    Use Util.sendClientSideMessage
    Will fix later
     */
    public static void sendClientMessage(String content, boolean watermark) {
        if(player == null || world == null || mc.ingameGUI == null) return;
        String text = watermark ? GRAY + "[" + RESET + AQUA + "HUD Mod" + RESET + GRAY + "]" + RESET + content : content;
        mc.thePlayer.addChatMessage(new ChatComponentText(text));
    }

}

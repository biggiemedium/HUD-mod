package dev.px.hud.Util.API;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Chatutil {

    private static ChatFormatting RESET = ChatFormatting.RESET;
    private static ChatFormatting GRAY = ChatFormatting.GRAY;
    private static ChatFormatting AQUA = ChatFormatting.AQUA;

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void sendClientMessage(String content, boolean watermark) {
        if(mc.thePlayer == null || mc.theWorld == null || mc.ingameGUI == null) return;
        String text = watermark ? GRAY + "[" + RESET + AQUA + "HUD Mod" + RESET + GRAY + "]" + RESET + content : content;
        mc.thePlayer.addChatMessage(new ChatComponentText(text));
    }

}

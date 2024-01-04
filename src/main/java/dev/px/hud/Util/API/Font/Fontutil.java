package dev.px.hud.Util.API.Font;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.Config.Configs.FontFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Fontutil {

    private static FontRenderer font = new FontRenderer(loadFont(40)); //new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 40));

    public Fontutil() {

    }

    public static Font loadFont(int size) {
        try {

                // creates and derives the font
                Font clientFont = Font.createFont(Font.TRUETYPE_FONT, new File(""));
                clientFont = clientFont.deriveFont(Font.PLAIN, size);
                return clientFont;


            // default
            //return new Font(Font.SANS_SERIF, Font.PLAIN, size);

        } catch (Exception exception) {
            return new Font(Font.SANS_SERIF, Font.PLAIN, size);
        }
    }

    public static void drawText(String text, int x, int y, int color) {
        font.drawString(text, x, y, color, false, true);
    }

    public static void drawTextShadow(String text, int x, int y, int color) {
        font.drawStringWithShadow(text, x, y, color, true);
    }

    public static void drawTextShadow(String text, float x, float y, int color) {
        font.drawStringWithShadow(text, x, y, color, true);
    }

    public static int drawStringWithShadowInt(String text, float x, float y, int color) {
        return HUDMod.fontManager.getFontRenderer().drawStringWithShadow(text, x, y, color, true);
    }

    public static float getWidth(String text) {
        return font.getStringWidth(text);
    }

    public static double getHeight() {
        return font.getHeight();
    }
}

package dev.px.hud.Util.API.Font;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Fontutil {

    private static FontRenderer font = new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 40));;

    public static void drawText(String text, int x, int y, int color) {
        font.drawString(text, x, y, color, false, true);
    }

    public static void drawTextShadow(String text, int x, int y, int color) {
        font.drawStringWithShadow(text, x, y, color, false);
    }

    public static double getWidth(String text) {
        return font.getStringWidth(text);
    }

    public static double getHeight() {
        return font.getHeight();
    }
}

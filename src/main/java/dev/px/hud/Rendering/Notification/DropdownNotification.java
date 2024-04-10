package dev.px.hud.Rendering.Notification;

import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class DropdownNotification {
    private String messsage;
    private long start;

    private long fadedIn;
    private long fadeOut;
    private long end;
    private int length;
    private final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    public DropdownNotification(String messsage, int length) {
        this.messsage = messsage;
        this.length = length;

        fadedIn = 200 * length;
        fadeOut = fadedIn + 500 * length;
        end = fadeOut + fadedIn;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    private long getTime() {
        return System.currentTimeMillis() - start;
    }

    public void render() {
        double offset;
        int width = (int) Fontutil.getWidth(messsage) + 20;
        int height = (int) Fontutil.getHeight() + 10;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * 10;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * 10);
        } else {
            offset = 10;
        }

        int x = (sr.getScaledWidth() / 2) - (width / 2);
        int y = (int) offset;
        RoundedShader.drawRound(x, y, width, height, 7, new Color(0, 0, 0, 120));
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        Fontutil.drawText(messsage, x + 10, (int) y + 6, Color.WHITE.getRGB());
    }

}

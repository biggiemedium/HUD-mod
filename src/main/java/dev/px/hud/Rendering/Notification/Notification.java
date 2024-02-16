package dev.px.hud.Rendering.Notification;

import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
Taken from thunderhack
 */
public class Notification implements Wrapper {

    private String name, message;
    private NotificationType type;
    private Timer timer;

    private Animation animation = new Animation(300, false, Easing.LINEAR);
    private float y, width, height;
    private float xAnimation;
    private int removeTime;
    private ScaledResolution sr;

    public Notification(String name, String message, NotificationType type, int time) {

        this.name = name;
        this.message = message;
        this.type = type;
        this.removeTime = time;

        this.timer = new Timer();
        timer.reset();

        this.sr = new ScaledResolution(mc);

        this.height = 28;
        this.y = sr.getScaledHeight() - height;
        this.width = Fontutil.getWidth(message) + 40;
        this.xAnimation = width;
    }

    public void render(float getY) {
        GL11.glPushMatrix();
        Color scolor = new Color(0x181A18);
        Color icolor = new Color(scolor.getRed(), scolor.getGreen(), scolor.getBlue(), (int) Mathutil.clamp(255 * (1 - animation.getAnimationFactor()), 0, 255));
        Color icolor2 = new Color(255, 255, 255, (int) Mathutil.clamp((1 - animation.getAnimationFactor()), 0, 255));
        Color c = new Color(39, 179, 206, (int) Mathutil.clamp(255 * (1 - animation.getAnimationFactor()), 0, 255));

        animation.setState(timerFinished());
        xAnimation = (float) (width * animation.getAnimationFactor());
        y = animate(y, getY);

        int x1 = (int) ((sr.getScaledWidth() - 6) - width + xAnimation);
        int y1 = (int) y;

        Renderutil.drawBlurredShadow(x1, y1, width, height, 12, icolor);
        RoundedShader.drawRound(x1, y1, width, height, 6f, icolor);
     //   Renderutil.drawRoundedRect(x1, y1 + (height - 2), width, 2, 1, c);

        Fontutil.drawText(name, (x1 + 6), y1 + 4, -1);
        Fontutil.drawText(message, x1 + 6, (int) (y1 + 4 + (height - Fontutil.getHeight()) / 2f), icolor2.getRGB());
        GL11.glPopMatrix();
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isRemoveable() {
        return (timerFinished()) && xAnimation >= width - 5;
    }

    public boolean timerFinished() {
        return timer.passed(removeTime);
    }

    public float animate(float value, float target) {
        return value + (target - value) / 8f;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getxAnimation() {
        return xAnimation;
    }

    public enum NotificationType {
        INFO,
        WARNING,
        ERROR,
    }

}

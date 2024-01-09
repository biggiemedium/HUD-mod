package dev.px.hud.Rendering.Notification;

import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Math.Timer;
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
        this.width = Fontutil.getWidth(name) + 34;
        this.xAnimation = width;
    }

    public void render() {
        Color scolor = new Color(0xFF171717);
        Color icolor = new Color(scolor.getRed(), scolor.getGreen(), scolor.getBlue(), (int) Mathutil.clamp(255 * (1 - animation.getAnimationFactor()), 0, 255));
        Color icolor2 = new Color(255, 255, 255, (int) Mathutil.clamp((1 - animation.getAnimationFactor()), 0, 255));

        xAnimation = (float) (width * animation.getAnimationFactor());

    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isRemoveable() {
        return timer.passed(this.removeTime) && xAnimation >= width - 5;
    }

    public enum NotificationType {
        INFO,
        WARNING,
        ERROR,
    }

}

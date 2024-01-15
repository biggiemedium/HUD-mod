package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpeedElement extends RenderElement {

    public SpeedElement() {
        super("Speed", 2, 75, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Mode> mode = create(new Setting<>("Mode", Mode.KMH));

    @Override
    public void render(float partialTicks) {

        String s = mode.getValue() == Mode.KMH ? "KMH: " + getSpeedInKM() : "B/PS: " + Mathutil.round(getSpeedInBPS(), 2);
        renderText(s, getX(), getY(), fontColor.getValue().getRGB());
        setWidth(getFontWidth(s));
        setHeight(getFontHeight());
    }

    private float getSpeedInBPS() {
        final double x = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        final double z = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        final float t = ((IMixinMinecraft) mc).timer().timerSpeed / 1000.0f;

        return MathHelper.sqrt_double(x * x + z * z) / t;
    }

    private float getSpeedInKM() {
        double deltaX = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double deltaZ = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;

        float distance = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

        double floor = Math.floor(( distance/1000.0f ) / ( 0.05f/3600.0f ));

        String formatter = String.valueOf(floor);

        if (!formatter.contains("."))
            formatter += ".0";

        return Float.valueOf(formatter);
    }

    private enum Mode {
        KMH,
        BPS
    }

}

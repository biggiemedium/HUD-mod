package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class SpeedElement extends RenderElement {

    public SpeedElement() {
        super("Speed", 2, 75, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Boolean> graph = create(new Setting<>("Graph", true));
    Setting<Mode> mode = create(new Setting<>("Mode", Mode.KMH, v -> !graph.getValue()));

    private final ArrayList<Float> speeds = new ArrayList<>();
    private double lastVertices;
    private float currentSpeed;
    private float average = 0;

    @Override
    public void render(float partialTicks) {

        if (speeds.size() > 100 - 2) {
            speeds.remove(0);
        }

        speeds.add((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) * ((IMixinMinecraft) mc).timer().timerSpeed);

        currentSpeed = -1;
        for (float f : speeds) {
            if (f > currentSpeed) {
                currentSpeed = f;
            }
        }

        if (lastVertices != 100) {
            synchronized (speeds) {
                speeds.clear();
                currentSpeed = 0;
            }
        }

        lastVertices = 100;
        average = calculateAverage(speeds);
    }

    private float calculateAverage(ArrayList<Float> marks) {
        Float sum = 0f;
        if(!marks.isEmpty()) {
            for (Float mark : marks) {
                sum += mark;
            }
            return sum.floatValue() / marks.size();
        }
        return sum;
    }

    @Override
    public void render2D(Render2DEvent event) {
        if(!graph.getValue()) {
            String s = mode.getValue() == Mode.KMH ? "KMH: " + getSpeedInKM() : "B/PS: " + Mathutil.round(getSpeedInBPS(), 2);
            renderText(s, getX(), getY(), fontColor.getValue().getRGB());
            setWidth(getFontWidth(s));
            setHeight(getFontHeight());
        } else {

            setWidth(110);
            setHeight(45);
            RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 2, new Color(26, 26, 26, 180));

            renderText("Speed", getX() + 1, getY() + 2, fontColor.getValue().getRGB());
            renderText("Average: " + Mathutil.round(getSpeedInKM(), 1), getX() + (getWidth() - getFontWidth("Average: " + Mathutil.round(getSpeedInKM(), 1))), getY() + 2, fontColor.getValue().getRGB());

            GL11.glPushMatrix();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glBegin(GL11.GL_LINES);

            if (speeds.size() > 3) {
                float width = getX();

                for (int i = 0; i < speeds.size() - 1; i++) {
                    Renderutil.color(new Color(255, 255, 255, 200));
                    float y = (float) (speeds.get(i) * 10 * 3);
                    float y2 = (float) (speeds.get(i + 1) * 10 * 3);
                    float length = (float) (110 / (speeds.size() - 1));
                    if(length >= 110) {
                        length = 110;
                    }
                    if(y >= 35 || y2 >= 35) {
                        y = 32;
                    }

                    GL11.glVertex2f(width + (i * length), (float) (getY() + getHeight()) - Math.min(y, 50));
                    GL11.glVertex2f(width + ((i + 1) * length), (float) (getY() + getHeight()) - Math.min(y2, 50));
                }
            }
            GL11.glEnd();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
            Renderutil.color(Color.WHITE);
            GlStateManager.resetColor();
            GL11.glPopMatrix();
        }
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

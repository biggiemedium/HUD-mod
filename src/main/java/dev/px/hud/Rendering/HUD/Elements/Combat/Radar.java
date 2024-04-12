package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.HUDMod;
import dev.px.hud.Manager.SocialManager;
import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Radar extends RenderElement {

    public Radar() {
        super("Radar", new ScaledResolution(Wrapper.mc).getScaledWidth() - 85, 85, 80, 80, HUDType.COMBAT);
        setTextElement(false);
    }

    Setting<Color> backgroundColor = create(new Setting<>("Radar Color", new Color(0x3D3939)));
    Setting<Color> enemyColor = create(new Setting<>("Enemy Color", new Color(255, 50, 50, 255)));
    Setting<Color> friendColor = create(new Setting<>("Friend Color", new Color(50, 50, 255, 255)));

    private CopyOnWriteArrayList<EntityPlayer> players = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Entity> entityList = new CopyOnWriteArrayList<>();

    @Override
    public void render2D(Render2DEvent event) {
            this.setWidth(80);
            this.setHeight(80);

            players.clear();
            players.addAll(mc.theWorld.playerEntities);
            double psx = getX();
            double psy = getY();
            int sizeRect = 80;
            float xOffset = (float) psx;
            float yOffset = (float) psy;
            double playerPosX = mc.thePlayer.posX;
            double playerPosZ = mc.thePlayer.posZ;


            Renderutil.drawBlurredShadow(xOffset, getY(), sizeRect, sizeRect, 17, new Color(61, 57, 57, 150));
        GL11.glPushMatrix();
            RoundedShader.drawRound(xOffset, getY(), sizeRect, sizeRect, 7f, backgroundColor.getValue());
        GL11.glPopMatrix();

            Renderutil.drawRect(xOffset + (sizeRect / 2F - 0.5), yOffset + 3.5, xOffset + (sizeRect / 2F + 0.2), (yOffset + sizeRect) - 3.5, getColor(155, 100));

            Renderutil.drawRect(xOffset + 3.5, yOffset + (sizeRect / 2F - 0.2), (xOffset + sizeRect) - 3.5, yOffset + (sizeRect / 2F + 0.5), getColor(155, 100));


            for (EntityPlayer entityPlayer : players) {
                if (entityPlayer == mc.thePlayer)
                    continue;

                float partialTicks = event.getPartialTicks();
                float posX = (float) (entityPlayer.posX + (entityPlayer.posX - entityPlayer.lastTickPosX) * partialTicks - playerPosX) * 2;
                float posZ = (float) (entityPlayer.posZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * partialTicks - playerPosZ) * 2;
                float cos = (float) Math.cos(mc.thePlayer.rotationYaw * 0.017453292);
                float sin = (float) Math.sin(mc.thePlayer.rotationYaw * 0.017453292);
                float rotY = -(posZ * cos - posX * sin);
                float rotX = -(posX * cos + posZ * sin);
                if (rotY > sizeRect / 2F - 6) {
                    rotY = sizeRect / 2F - 6;
                } else if (rotY < -(sizeRect / 2F - 8)) {
                    rotY = -(sizeRect / 2F - 8);
                }
                if (rotX > sizeRect / 2F - 5) {
                    rotX = sizeRect / 2F - 5;
                } else if (rotX < -(sizeRect / 2F - 5)) {
                    rotX = -(sizeRect / 2F - 5);
                }

                GL11.glPushMatrix();
                RoundedShader.drawRound((xOffset + sizeRect / 2F + rotX) - 2, (yOffset + sizeRect / 2F + rotY) - 2, 4, 4, 4f, HUDMod.socialManager.getState(entityPlayer.getName()) == SocialManager.SocialState.FRIEND ? friendColor.getValue() : enemyColor.getValue());
                GL11.glPopMatrix();
            }
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static double interpolate(double current, double old) {
        return old + (current - old) * ((IMixinMinecraft) Wrapper.mc).timer().renderPartialTicks;
    }
}

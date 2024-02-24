package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Objects;

public class NameTags extends ToggleableElement {

    public NameTags() {
        super("Name tags", "", HUDType.RENDER);
    }

    Setting<Integer> distance = create(new Setting<>("Distance", 35, 1, 75));
    Setting<Float> scale = create(new Setting<>("Scale", 0.3f, 0.1f, 8f));

    Setting<Boolean> armor = create(new Setting<>("Armor", true));
    Setting<Boolean> durability = create(new Setting<>("Durability", false, v-> armor.getValue()));
    Setting<Boolean> health = create(new Setting<>("Health", true));
    Setting<Boolean> ping = create(new Setting<>("Ping", true));
    Setting<Boolean> item = create(new Setting<>("Item Held", true));

    @Override
    public void enable() {

    }


    @Override
    public void onRender(Render3dEvent event) {

        if(mc.getRenderManager() == null || mc.getRenderManager().options == null) return;
        for(EntityPlayer e : mc.theWorld.playerEntities) {
            if(e == null) { continue; }
            if(mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) { continue; }
            double pX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosX();
            double pY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosY();
            double pZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ();
            Vec3 pos = new Vec3(pX, pY, pZ);


        }

    }

    @SubscribeEvent
    public void onNameTagRender(RenderLivingEvent.Specials.Pre<EntityPlayer> event) {
        event.setCanceled(true);
    }

    private void renderNameTag(EntityPlayer player, double x, double y, double z, float ticks) {
        Entity camera = mc.getRenderViewEntity();
        assert (camera != null);
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = Mathutil.interpolate(camera.prevPosX, camera.posX, ticks);
        camera.posY = Mathutil.interpolate(camera.prevPosY, camera.posY, ticks);
        camera.posZ = Mathutil.interpolate(camera.prevPosZ, camera.posZ, ticks);

        double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
        double scale = (0.0018 + (double) this.scale.getValue() * (distance * (double) 0.4f)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }

        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) distance + (player.isSneaking() ? 0.0 : 0.08f) + 1.4f, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.enableBlend(); // Code may be fucky starting here
        String nameTag = getDisplayTag(player);
        float width = mc.fontRendererObj.getStringWidth(nameTag) / 2;
        float height = mc.fontRendererObj.FONT_HEIGHT;

        GlStateManager.enableBlend();
        Renderutil.drawRect(-width - 1, -(height + 1), width + 2, 2, 0x5F0A0A0A);
        GlStateManager.disableBlend();
        mc.fontRendererObj.drawStringWithShadow(nameTag, -width+1, -height+3, getColorByHealth(player.getMaxHealth(), player.getHealth()));

        GlStateManager.pushMatrix();

    }

    // Ionar2 Code
    private int getColorByHealth(float maxHealth, float health) {
        Color green = new Color(72, 255, 94);
        Color yellow = new Color(255, 250, 57);
        Color red = new Color(255, 35, 40);

        float middleHealth = maxHealth / 2;

        if (health <= middleHealth)
        {
            return blend(yellow, red, (health / middleHealth)).getRGB();
        } else if (health <= (middleHealth * 2))
        {
            return blend(green, yellow, ((health - middleHealth) / middleHealth)).getRGB();
        }
        return green.getRGB();
    }

    public static Color blend(Color color1, Color color2, float ratio) {
        if (ratio < 0)
            return color2;
        if (ratio > 1)
            return color1;
        float ratio2 = (float) 1.0 - ratio;
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color((rgb1[0] * ratio) + (rgb2[0] * ratio2), (rgb1[1] * ratio) + (rgb2[1] * ratio2), (rgb1[2] * ratio) + (rgb2[2] * ratio2));
    }

    private String getDisplayTag(EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (name.contains(mc.getSession().getUsername())) {
            name = "You";
        }
        if (!this.health.getValue()) {
            return name;
        }
        float health = Entityutil.getHealth(player);
        String color = health > 18.0f ? "\u00a7a" : (health > 16.0f ? "\u00a72" : (health > 12.0f ? "\u00a7e" : (health > 8.0f ? "\u00a76" : (health > 5.0f ? "\u00a7c" : "\u00a74"))));
        String pingStr = "";

        if (this.ping.getValue()) {
            try {
                int responseTime = Objects.requireNonNull(mc.getNetHandler()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = pingStr + responseTime + "ms ";
            } catch (Exception responseTime) {}
        }

        name = Math.floor(health) == (double) health ? name + color + " " + (health > 0.0f ? Integer.valueOf((int) Math.floor(health)) : "dead") : name + color + " " + (health > 0.0f ? Integer.valueOf((int) health) : "dead");
        return pingStr + name;
    }
}

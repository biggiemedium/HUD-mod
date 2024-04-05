package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Render.ESPutil;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector4f;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdditionalInfo extends ToggleableElement {

    public AdditionalInfo() {
        super("Side Nametag", "", HUDType.RENDER);
    }

    private Map<EntityPlayer, Timer> targetMap = new ConcurrentHashMap<>();
    Setting<Float> scale = create(new Setting<>("Scale", 0.3f, 0.1f, 8f));


    @Override
    public void enable() {
        this.targetMap = new ConcurrentHashMap<>();
    }

    @Override
    public void disable() {
        this.targetMap.clear();
        this.targetMap = null; // Check if this causes error
    }

    @Override
    public void onRender(Render3dEvent event) {

        if(mc.getRenderManager() == null || mc.getRenderManager().options == null) return;
        for(EntityPlayer e : mc.theWorld.playerEntities) {
            if(e == null) { continue; }
            if(e == mc.thePlayer) { continue; }
            if(!e.isEntityAlive()) { continue; }
            //        if(mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) { continue; }
            double pX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosX();
            double pY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosY();
            double pZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ();
            Vec3 pos = new Vec3(pX, pY, pZ);

            renderNameTag(e, pX, pY, pZ, event.getPartialTicks());
        }

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

        double tempY = y;
        tempY += player.isSneaking() ? 0.5 : 0.7;
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
        GlStateManager.translate((float) x, (float) tempY + 1.6f, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        String nameTag = "getDisplayTag(player)";

        float width = mc.fontRendererObj.getStringWidth(nameTag);
        float height = mc.fontRendererObj.FONT_HEIGHT / 2;

        GlStateManager.enableBlend();
        Renderutil.drawRect(-width - 1, -(height + 1), width + 2, 2, 0x5F0A0A0A);
        GlStateManager.disableBlend();
    //    mc.fontRendererObj.drawStringWithShadow(nameTag, -width+1, -height+3, getColorByHealth(player.getMaxHealth(), player.getHealth()));

        GlStateManager.pushMatrix();

        // Item stuff here

        GlStateManager.popMatrix();

        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();

        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
    }

}

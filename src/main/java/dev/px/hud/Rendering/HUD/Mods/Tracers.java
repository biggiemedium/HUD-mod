package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.HUDMod;
import dev.px.hud.Mixin.Render.Item.IEntityRenderer;
import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL32;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Tracers extends ToggleableElement {

    public Tracers() {
        super("Tracers", HUDType.RENDER);
    }

    Setting<Color> color = create(new Setting<>("Color", new Color(255, 255, 255)));
    Setting<Integer> distance = create(new Setting<>("Distance", 35, 1, 75));


    @Override
    public void onRender(Render3dEvent event) {
        for(Entity e : mc.theWorld.playerEntities) {
            if (e == null || e == mc.thePlayer) {
                continue;
            }
            if(HUDMod.elementInitalizer.isElementToggled(ESPMod.class)) {
                if(HUDMod.elementInitalizer.getElementByClass(ESPMod.class).mode.getValue() == ESPMod.Mode.R6) {
                    continue;
                }
            }
            if (!(e instanceof EntityLivingBase)) {
                continue;
            }
            if (mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) {
                continue;
            }
            Vec3 vec = Entityutil.getInterpolatedPos(e, event.getPartialTicks())
                    .subtract(new Vec3(
                            ((MixinRenderManager) mc.getRenderManager()).getRenderPosX(),
                            ((MixinRenderManager) mc.getRenderManager()).getRenderPosY(),
                            ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ())
                    );
            if(vec != null) {
                boolean bobbing = mc.gameSettings.viewBobbing;
                mc.gameSettings.viewBobbing = false;
                mc.entityRenderer.setupOverlayRendering();
                ((IEntityRenderer) mc.entityRenderer).invokeSetupCameraTransform(event.getPartialTicks(), 0);
                Vec3 forward = new Vec3(0, 0, 1).rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationPitch)).rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw));

                Tracers.drawLine3D((float) forward.xCoord, (float) forward.yCoord + mc.thePlayer.eyeHeight, (float) forward.zCoord, (float) vec.xCoord, (float) vec.yCoord, (float) vec.zCoord, 0.5f, this.color.getValue().getRGB());
                mc.gameSettings.viewBobbing = bobbing;
                ((IEntityRenderer) mc.entityRenderer).invokeSetupCameraTransform(event.getPartialTicks(), 0);
            }
        }


    }

    public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, float thickness, int hex) {
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        float alpha = (hex >> 24 & 0xFF) / 255.0F;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(GL_SMOOTH);
        glLineWidth(thickness);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GlStateManager.disableDepth();
        glEnable(GL32.GL_DEPTH_CLAMP);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getWorldRenderer();
        bufferbuilder.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) x, (double) y, (double) z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double) x1, (double) y1, (double) z1).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL_FLAT);
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableDepth();
        glDisable(GL32.GL_DEPTH_CLAMP);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

}

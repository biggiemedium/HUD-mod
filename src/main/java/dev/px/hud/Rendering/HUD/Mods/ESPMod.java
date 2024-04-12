package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.HUDMod;
import dev.px.hud.Manager.SocialManager;
import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Mixin.Render.Item.IEntityRenderer;
import dev.px.hud.Mixin.Render.IMixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Render.Color.Colorutil;
import dev.px.hud.Util.API.Render.ESPutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ESPMod extends ToggleableElement {

    public ESPMod() {
        super("ESP", "ESP", HUDType.RENDER);
    }

    Setting<Boolean> showESP = create(new Setting<>("ESP", true));
    public Setting<Mode> mode = create(new Setting<>("Mode", Mode.Box));
    Setting<Integer> distance = create(new Setting<>("Distance", 35, 1, 75));

    /* Cirlce stuff */
    Setting<Boolean> circle = create(new Setting<>("Cirlce", true));
    Setting<Boolean> playersOnly = create(new Setting<>("Player Only", true));

    private Map<Entity, Timer> targetMap = new ConcurrentHashMap<>();
    private Map<Entity, org.lwjgl.util.vector.Vector4f> entityPosition = new ConcurrentHashMap<>();

    public enum Mode {
        Box,
        R6
    }

    @Override
    public void onUpdate() {
        if (Util.isNull() || mc.thePlayer.ticksExisted < 20) {
            return;
        }

        entityPosition.clear();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (ESPutil.isInView(entity)) {
                if (playersOnly.getValue() && entity instanceof EntityPlayer) {
                    entityPosition.put(entity, ESPutil.getEntityPositionsOn2D(entity));
                } else if (!playersOnly.getValue()) {
                    if (Entityutil.isPassive(entity) || entity instanceof EntityMob) {
                        entityPosition.put(entity, ESPutil.getEntityPositionsOn2D(entity));
                    }
                }
            }
        }

        targetMap.forEach((e, t) -> {
            if(t.passed(7 * 1000)) {
                targetMap.remove(e, t);
            }
        });

        if(mc.objectMouseOver != null) {
            Entity e = mc.objectMouseOver.entityHit;
            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                if (e instanceof EntityLivingBase) {
                    if (e != null) {
                        if (circle.getValue()) {
                            if (playersOnly.getValue() && e instanceof EntityPlayer) {
                                if (!targetMap.containsKey(e)) {
                                    Timer t = new Timer();
                                    t.reset();
                                    targetMap.put(e, t);
                                } else {
                                    targetMap.forEach((entity, t) -> {
                                        t.reset();
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRender(Render3dEvent event) {

        /* Cirlces */
       for(Entity e : targetMap.keySet()) {
           if(e == null) {
               continue;
           }
           if(e == mc.thePlayer) {
               continue;
           }
           if(playersOnly.getValue() && !(e instanceof EntityPlayer)) {
               continue;
           }

           Renderutil.drawCircle(e, 1, HUDMod.colorManager.getMainColor().getRGB(), true);
       }

       if(!showESP.getValue()) return; // im lazy

        /* Box 2d */
        if(mode.getValue() == Mode.Box || mode.getValue() == Mode.R6) {
                for(EntityPlayer e : mc.theWorld.playerEntities) {
                    if(e == null || e == mc.thePlayer) {
                        continue;
                    }
                    if(!(e instanceof EntityLivingBase)) {
                        continue;
                    }
                    if(HUDMod.preferenceManager.ESPCluster.getValue() && mc.theWorld.playerEntities.size() > 40 && mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > 20) {
                        continue;
                    }
                    if(mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) {
                        continue;
                    }
                    if(HUDMod.preferenceManager.NCPCluster.getValue()
                            && Entityutil.isHypixelNPC(e) || Entityutil.isPlayerFake(e))
                    { continue; }

                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * ((IMixinMinecraft) mc).timer().renderPartialTicks - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosX();
                    double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * ((IMixinMinecraft) mc).timer().renderPartialTicks - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosY());
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ((IMixinMinecraft) mc).timer().renderPartialTicks - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosZ();
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y - 0.2, z);
                    GlStateManager.disableDepth();

                    GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);

                    final float width = 1.1f;
                    final float height = 2.2f;

                    float lineWidth = mode.getValue() == Mode.Box ? 0.07f : 0.04f;

                    Color pColor = HUDMod.socialManager.getState(e.getName()) == SocialManager.SocialState.FRIEND && HUDMod.preferenceManager.FRIENDS.getValue() ? new Color(18, 150, 238) : new Color(255, 30, 30, 255);
                    Color pColor2 = HUDMod.socialManager.getState(e.getName()) == SocialManager.SocialState.FRIEND && HUDMod.preferenceManager.FRIENDS.getValue() ? new Color(18, 150, 238) : Colorutil.interpolateColorC(HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), 15);
                    if(mode.getValue() == Mode.R6) {
                        draw2DBox(width, height, lineWidth, 0, pColor);// new Color(255, 30, 30, 255)
                    } else if(mode.getValue() == Mode.Box) {
                        draw2DBox(width, height, lineWidth, 0.04f, new Color(0, 0, 0, 165));
                        if (((EntityLivingBase) e).hurtTime > 0)
                            draw2DBox(width, height, lineWidth, 0, new Color(255, 30, 30, 255)); // new Color(255, 30, 30, 255)
                        else
                            draw2DBox(width, height, lineWidth, 0, pColor2); // Colorutil.interpolateColorC(HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), 15)
                    }
                    GlStateManager.enableDepth();
                    GL11.glPopMatrix();
                }
        }

        if(mode.getValue() == Mode.R6) {
            for(Entity e : mc.theWorld.playerEntities) {
                if (e == null || e == mc.thePlayer) {
                    continue;
                }
                if (!(e instanceof EntityLivingBase)) {
                    continue;
                }
                if (mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) {
                    continue;
                }
                Vec3 vec = Entityutil.getInterpolatedPos(e, event.getPartialTicks())
                        .subtract(new Vec3(
                                        ((IMixinRenderManager) mc.getRenderManager()).getRenderPosX(),
                                        ((IMixinRenderManager) mc.getRenderManager()).getRenderPosY(),
                                        ((IMixinRenderManager) mc.getRenderManager()).getRenderPosZ())
                        );
                if(vec != null) {
                    boolean bobbing = mc.gameSettings.viewBobbing;
                    mc.gameSettings.viewBobbing = false;
                    mc.entityRenderer.setupOverlayRendering();
                    ((IEntityRenderer) mc.entityRenderer).invokeSetupCameraTransform(event.getPartialTicks(), 0);
                    Vec3 forward = new Vec3(0, 0, 1).rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationPitch)).rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw));
                    Tracers.drawLine3D((float) forward.xCoord, (float) forward.yCoord + mc.thePlayer.eyeHeight, (float) forward.zCoord, (float) vec.xCoord, (float) vec.yCoord, (float) vec.zCoord, 0.5f, new Color(255, 30, 30, 255).getRGB());
                    mc.gameSettings.viewBobbing = bobbing;
                    ((IEntityRenderer) mc.entityRenderer).invokeSetupCameraTransform(event.getPartialTicks(), 0);
                }

            }
        }
    }

    private void draw2DBox(final float width, final float height, final float lineWidth, final float offset, final Color c) {
        Renderutil.rect(-width / 2 - offset, -offset, width / 4, lineWidth, c);
        Renderutil.rect(width / 2 - offset, -offset, -width / 4, lineWidth, c);
        Renderutil.rect(width / 2 - offset, height - offset, -width / 4, lineWidth, c);
        Renderutil.rect(-width / 2 - offset, height - offset, width / 4, lineWidth, c);

        Renderutil.rect(-width / 2 - offset, height - offset, lineWidth, -height / 4, c);
        Renderutil.rect(width / 2 - lineWidth - offset, height - offset, lineWidth, -height / 4, c);
        Renderutil.rect(width / 2 - lineWidth - offset, -offset, lineWidth, height / 4, c);
        Renderutil.rect(-width / 2 - offset, -offset, lineWidth, height / 4, c);
    }

    public boolean containsName(final List<Entity> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }

    private boolean entityCheck(Entity e) {

        if(e instanceof EntityPlayer && playersOnly.getValue()) {
            return true;
        }

        return false;
    }

    public Setting<Mode> getMode() {
        return mode;
    }

    public class ESPTarget {
        private Entity entity;
        private Timer timer;

        public ESPTarget(Entity entity, Timer timer) {
            this.entity = entity;
            this.timer = timer;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }

        public Timer getTimer() {
            return timer;
        }

        public void setTimer(Timer timer) {
            this.timer = timer;
        }
    }
}

package dev.px.hud.Rendering.HUD.Mods;

import com.sun.javafx.geom.Vec4f;
import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Render.ESPutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector4f;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ESPMod extends ToggleableElement {

    public ESPMod() {
        super("ESP", "ESP", HUDType.RENDER);
    }

    Setting<Boolean> circle = create(new Setting<>("Cirlce", true));
    Setting<Boolean> targetOnly = create(new Setting<>("Target only", true, v -> circle.getValue()));

    Setting<Boolean> players = create(new Setting<>("Player", true, v -> !targetOnly.getValue()));
    Setting<Boolean> passives = create(new Setting<>("Passives", false, v -> !targetOnly.getValue()));
    Setting<Boolean> hostile = create(new Setting<>("Hostiles", false, v -> !targetOnly.getValue()));
    Setting<Boolean> items = create(new Setting<>("Items", false, v -> !targetOnly.getValue()));




    private List<ESPTarget> highlightedPlayers = new ArrayList<>();


    @Override
    public void onUpdate() {
        if(Util.isNull()) return;

        Entity event = mc.objectMouseOver.entityHit;

        if(mc.gameSettings.keyBindAttack.isKeyDown()) {
        if(event instanceof EntityLivingBase) {
            if (event != mc.thePlayer) {
                if (event.isEntityAlive()) {
                    if (entityCheck(event)) {
                        if (circle.getValue()) {
                            if (targetOnly.getValue()) {
                                ESPTarget t = new ESPTarget(event, new Timer());
                                t.getTimer().reset();
                                if(!highlightedPlayers.contains(event)) {
                                    this.highlightedPlayers.add(t);
                                } else {
                                    t.getTimer().reset();
                                }
                            }
                        }
                    }
                }
            }
        }
        }


        this.highlightedPlayers.removeIf(e ->
                e.getEntity() == null
                        || e.getTimer().passed(6 * 1000)
                        || e.getEntity().isDead
                        || mc.thePlayer.getDistanceSqToEntity(e.getEntity()) > 100);
    }

    @Override
    public void onRender(Render3dEvent event) {

        for(Entity e : mc.theWorld.playerEntities) {
            if(e != null) {
                if(e != mc.thePlayer) {
                    if(e.isEntityAlive()) {
                        drawHealthBarTenacity(e, false, false);
                    }
                }
            }
        }

        if(circle.getValue() && !targetOnly.getValue()) {
            for (Entity e : mc.theWorld.loadedEntityList) {
                if (e != null) {
                    if (e != mc.thePlayer) {
                        if (e.isEntityAlive()) {
                            if(entityCheck(e)) {
                                Renderutil.drawCircle(e, 1, HUDMod.colorManager.getMainColor().getRGB(), true);
                            }
                        }
                    }
                }
            }
        } else if(circle.getValue() && targetOnly.getValue()) {
            this.highlightedPlayers.forEach(e -> {
                Renderutil.drawCircle(e.getEntity(), 1, HUDMod.colorManager.getMainColor().getRGB(), true);
            });
        }

    }

    private void drawHealthBarTenacity(Entity entity, boolean text3d, boolean mcText) {
        if(entity instanceof EntityLivingBase) {
            Vector4f pos = ESPutil.getEntityPositionsOn2D(entity);
            float x = pos.getX(),
                    y = pos.getY(),
                    right = pos.getZ(),
                    bottom = pos.getW();

            EntityLivingBase renderingEntity = (EntityLivingBase) entity;
            float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
            Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);

            float height = (bottom - y) + 1;
            Renderutil.drawRect(right + 2.5f, y - .5f, 2, height + 1, new Color(0, 0, 0, 180).getRGB());

            Renderutil.drawRect(right + 3, y + (height - (height * healthValue)), 1, height * healthValue, healthColor.getRGB());


            if (text3d) {
                healthValue *= 100;
                String health = String.valueOf(Mathutil.round(healthValue, 1)).substring(0, healthValue == 100 ? 3 : 2);
                String text = health + "%";
                double fontScale = .5;
                float textX = right + 8;
                float fontHeight = mcText ? (float) (mc.fontRendererObj.FONT_HEIGHT * fontScale) : (float) (Fontutil.getHeight() * fontScale);
                float newHeight = height - fontHeight;
                float textY = y + (newHeight - (newHeight * (healthValue / 100)));

                GL11.glPushMatrix();
                GL11.glTranslated(textX - 5, textY, 1);
                GL11.glScaled(fontScale, fontScale, 1);
                GL11.glTranslated(-(textX - 5), -textY, 1);
                if (mcText) {
                    mc.fontRendererObj.drawStringWithShadow(text, textX, textY, -1);
                } else {
                    Fontutil.drawTextShadow(text, textX, textY, -1);
                }
                GL11.glPopMatrix();
            }
        }
    }



    private boolean entityCheck(Entity e) {

        if(e instanceof EntityPlayer && players.getValue()) {
            return true;
        }
        if(e instanceof EntityMob && hostile.getValue()) {
            return true;
        }
        if(Entityutil.isPassive(e) && passives.getValue()) {
            return true;
        }
        if(e instanceof EntityItem && items.getValue()) {
            return true;
        }

        return false;
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

package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.Event.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
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


    private Map<Entity, Timer> attackTarget = new ConcurrentHashMap<>();

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender(Render3dEvent event) {

        if(circle.getValue() && !targetOnly.getValue()) {
            for (Entity e : mc.theWorld.loadedEntityList) {
                if (e instanceof EntityLivingBase) {
                    Renderutil.drawCircle(e, 1, new Color(56, 56, 56, 150).getRGB(), true);
                }
            }
        } else if (circle.getValue() && targetOnly.getValue()) {
            for (Entity e : attackTarget.keySet()) {
                if (e instanceof EntityLivingBase) {
                    Renderutil.drawCircle(e, 1, new Color(56, 56, 56, 150).getRGB(), true);
                }
            }
        }

    }

    @SubscribeEvent
    public void onEntityAttack(AttackEntityEvent event) {
        if(event.target instanceof EntityLivingBase) {
            if (event.target != mc.thePlayer) {
                if(event.target.isEntityAlive()) {
                    if(circle.getValue() && targetOnly.getValue()) {
                        this.attackTarget.put(event.target, new Timer());
                    }
                }
            }
        }

    }
}

package dev.px.hud.Util.API.Entity;

import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;

public class Entityutil implements Wrapper {

    private static final Frustum frustum = new Frustum();

    public static boolean isInView(Entity ent) {
        frustum.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
        return frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }

    public static Entity getTarget(int distance, boolean playersOnly) {
        for(Entity e : mc.theWorld.loadedEntityList) {
            if(e == null) {
                continue;
            }
            if(!(e instanceof EntityLivingBase)) {
                continue;
            }
            if(!e.isEntityAlive()) {
                continue;
            }
            if(e == mc.thePlayer) {
                continue;
            }
            if(playersOnly && !(e instanceof EntityPlayer)) {
                continue;
            }
            if(mc.thePlayer.getDistanceToEntity(e) > distance) {
                continue;
            }
            return e;
        }
        return null;
    }

    public static float getHealth(EntityPlayer entity) {
        return Mathutil.round(entity.getHealth() + entity.getAbsorptionAmount(), 0);
    }

    public static boolean isPassive(Entity entity) {

        if(entity instanceof EntityWolf && ((EntityWolf) entity).isAngry()) {
            return false;
        }

        if(entity instanceof EntityAnimal || entity instanceof EntityTameable || entity instanceof EntitySquid || entity instanceof EntityAgeable) {
            return true;
        }

        return false;
    }

    public static Vec3 getInterpolatedPos(Entity entity, float ticks) {
        Vec3 d = new Vec3(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
        d.add(getInterpolatedAmount(entity, ticks));
        return d;
    }

    public static Vec3 getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    public static Vec3 getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
}

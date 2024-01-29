package dev.px.hud.Util.API.Entity;

import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

public class Entityutil implements Wrapper {


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
}

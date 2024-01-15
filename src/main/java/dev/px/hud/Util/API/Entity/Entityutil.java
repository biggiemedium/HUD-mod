package dev.px.hud.Util.API.Entity;

import dev.px.hud.Util.API.Math.Mathutil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Entityutil {

    public static float getHealth(EntityPlayer entity) {
        return Mathutil.round(entity.getHealth() + entity.getAbsorptionAmount(), 0);
    }

}

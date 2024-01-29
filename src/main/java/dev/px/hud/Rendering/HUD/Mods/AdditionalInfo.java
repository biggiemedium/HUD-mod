package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Render.ESPutil;
import dev.px.hud.Util.Event.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vector3d;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AdditionalInfo extends ToggleableElement {

    public AdditionalInfo() {
        super("Side Nametag", "", HUDType.RENDER);
    }

    @Override
    public void enable() {

    }

    @Override
    public void onRender(Render3dEvent event) {

        if (mc.renderEngine != null && mc.getRenderManager().options != null) {
            double pX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosX();
            double pY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosY();
            double pZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ();


            for (EntityPlayer e : mc.theWorld.playerEntities) {
                if (mc.thePlayer.getDistanceToEntity(e) <= 15) {
                    if (e != null) {
                        if (e != mc.thePlayer) {
                            if (e instanceof EntityPlayer) {
                                if (!e.isDead) {


                                    Vector4f pos = ESPutil.getEntityPositionsOn2D(e);
                                    float
                                            x = pos.getX(),
                                            y = pos.getY(),
                                            right = pos.getZ(),
                                            bottom = pos.getW();



                                }
                            }
                        }
                    }
                }
            }


        }
    }

}

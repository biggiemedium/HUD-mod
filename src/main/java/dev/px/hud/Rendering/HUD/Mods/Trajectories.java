package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.lwjgl.util.glu.Cylinder;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class Trajectories extends ToggleableElement {

    public Trajectories() {
        super("Trajectories", HUDType.RENDER);
    }

    public Setting<Color> color = create(new Setting<>("Color", new Color(255, 255, 255)));
    public Setting<Color> colorc = create(new Setting<>("Charge Color", new Color(204, 127, 0)));
    public Setting<Float> scale = create(new Setting<>("Scale", 1.0f, 0.1f, 2.0f));
    @Override
    public void onRender(Render3dEvent event) {

        if (mc.thePlayer == null || mc.theWorld == null || mc.gameSettings == null) return;
        if(mc.thePlayer.getHeldItem() == null) return;
        final double renderPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.getPartialTicks();
        final double renderPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.getPartialTicks();
        final double renderPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.getPartialTicks();

       // if(mc.thePlayer.getHeldItem() == null) return;
        if (mc.gameSettings.thirdPersonView == 0 && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow || mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod || mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl || mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg || mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball || mc.thePlayer.getHeldItem().getItem() instanceof ItemExpBottle)) {

            GL11.glPushMatrix();
            Item item = mc.thePlayer.getHeldItem().getItem();
            double posX = renderPosX - MathHelper.cos(mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            double posY = renderPosY + mc.thePlayer.getEyeHeight() - 0.1000000014901161;
            double posZ = renderPosZ - MathHelper.sin(mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            double motionX = -MathHelper.sin(mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);
            double motionY = -MathHelper.sin(mc.thePlayer.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);
            double motionZ = MathHelper.cos(mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0f * 3.1415927f) * ((item instanceof ItemBow) ? 1.0 : 0.4);
            final int var6 = 72000 - mc.thePlayer.getItemInUseCount();
            float power = var6 / 20.0f;
            power = (power * power + power * 2.0f) / 3.0f;
            if (power > 1.0f) {
                power = 1.0f;
            }
            final float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            motionX /= distance;
            motionY /= distance;
            motionZ /= distance;
            final float pow = (item instanceof ItemBow) ? (power * 2.0f) : ((item instanceof ItemFishingRod) ? 1.25f : ((mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle) ? 0.9f : 1.0f));
            motionX *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle) ? 0.75f : 1.5f));
            motionY *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle) ? 0.75f : 1.5f));
            motionZ *= pow * ((item instanceof ItemFishingRod) ? 0.75f : ((mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle) ? 0.75f : 1.5f));
            this.enableGL3D(2.0f);
            if (power > 0.6f) {
                GlStateManager.color(this.color.getValue().getRed() / 255.0f, this.color.getValue().getGreen() / 255.0f, this.color.getValue().getBlue() / 255.0f, 1.0f);
            } else {
                GlStateManager.color(this.colorc.getValue().getRed() / 255.0f, this.colorc.getValue().getGreen() / 255.0f, this.colorc.getValue().getBlue() / 255.0f, 1.0f);
            }
            GL11.glEnable(2848);
            final float size = (float) ((item instanceof ItemBow) ? 0.3 : 0.25);
            boolean hasLanded = false;
            Entity landingOnEntity = null;
            MovingObjectPosition landingPosition = null;
            while (!hasLanded && posY > 0.0) {
                final Vec3 present = new Vec3(posX, posY, posZ);
                final Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                final MovingObjectPosition possibleLandingStrip = mc.theWorld.rayTraceBlocks(present, future, false, true, false);
                if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                    landingPosition = possibleLandingStrip;
                    hasLanded = true;
                }
                final AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
                final List entities = this.getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
                for (final Object entity : entities) {
                    final Entity boundingBox = (Entity) entity;
                    if (boundingBox.canBeCollidedWith() && boundingBox != mc.thePlayer) {
                        final float var8 = 0.3f;
                        final AxisAlignedBB var9 = boundingBox.getEntityBoundingBox().expand((double) var8, (double) var8, (double) var8);
                        final MovingObjectPosition possibleEntityLanding = var9.calculateIntercept(present, future);
                        if (possibleEntityLanding == null) {
                            continue;
                        }
                        hasLanded = true;
                        landingOnEntity = boundingBox;
                        landingPosition = possibleEntityLanding;
                    }
                }
                if (landingOnEntity != null) {
                    GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
                }
                posX += motionX;
                posY += motionY;
                posZ += motionZ;
                final float motionAdjustment = 0.99f;
                motionX *= motionAdjustment;
                motionY *= motionAdjustment;
                motionZ *= motionAdjustment;
                motionY -= ((item instanceof ItemBow) ? 0.05 : 0.03);
                this.drawLine3D(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            }
            if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
                final int side = landingPosition.sideHit.getIndex();
                if (side == 2) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                } else if (side == 3) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                } else if (side == 4) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                } else if (side == 5) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                final Cylinder c = new Cylinder();
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.scale((float) this.scale.getValue(), (float) this.scale.getValue(), (float) this.scale.getValue());
                c.setDrawStyle(100011);
                if (landingOnEntity != null) {
                    GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glLineWidth(2.5f);
                    c.draw(0.6f, 0.3f, 0.0f, 4, 1);
                    GL11.glLineWidth(0.1f);

                    GlStateManager.color(this.color.getValue().getRed() / 255.0f, this.color.getValue().getGreen() / 255.0f, this.color.getValue().getBlue() / 255.0f, 1.0f);

                }
                c.draw(0.6f, 0.3f, 0.0f, 4, 1);
            }
            this.disableGL3D();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();

        }
    }

    public void enableGL3D(final float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        mc.entityRenderer.disableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }

    public void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public void drawLine3D(final double var1, final double var2, final double var3) {
        GL11.glVertex3d(var1, var2, var3);
    }

    protected List<Entity> getEntitiesWithinAABB(AxisAlignedBB bb) {
        final ArrayList<Entity> list = new ArrayList<>();
        final int chunkMinX = MathHelper.floor_double((bb.minX - 2.0) / 16.0);
        final int chunkMaxX = MathHelper.floor_double((bb.maxX + 2.0) / 16.0);
        final int chunkMinZ = MathHelper.floor_double((bb.minZ - 2.0) / 16.0);
        final int chunkMaxZ = MathHelper.floor_double((bb.maxZ + 2.0) / 16.0);
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (mc.theWorld.getChunkProvider().provideChunk(x, z) != null) {
                    mc.theWorld.getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity(mc.thePlayer, bb, list, EntitySelectors.NOT_SPECTATING);
                }
            }
        }
        return list;
    }
}


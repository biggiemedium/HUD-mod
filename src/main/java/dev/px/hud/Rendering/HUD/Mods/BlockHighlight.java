package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Game.IMixinMinecraft;
import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

public class BlockHighlight extends ToggleableElement {

    public BlockHighlight() {
        super("Block Highlight", "", HUDType.MOD);
    }

    Setting<Float> lineWidth = create(new Setting<>("Line Width", 1.0f, 1.0f, 5.0f));
  //  Setting<Mode> mode = create(new Setting<>("Mode", Mode.Outline));
    Setting<Color> highlightColor = create(new Setting<>("Color", new Color(255, 255, 255)));
  //  Setting<Color> insideColor = create(new Setting<>("Inside Color", new Color(255, 255, 255), v -> mode.getValue() == Mode.Full));

    @Override
    public void disable() {
    }

    private enum Mode {
        Outline,
        Full
    }

    @Override
    public void onRender(Render3dEvent event) {

        MovingObjectPosition mop = mc.objectMouseOver;
        if(mop != null) {
            if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos pos = mop.getBlockPos();
                AxisAlignedBB bb = mc.theWorld.getBlockState(pos).getBlock().getSelectedBoundingBox(mc.theWorld, pos);
               // if(mode.getValue() == Mode.Outline) {
                    if (pos != null) {
                        drawBlockOutline(pos, highlightColor.getValue(), lineWidth.getValue(), false, 0);
                    }
             //   }

            }
        }
    }


    public Vec3 interpolateEntity(Entity entity, float time) {
        return new Vec3(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
    }



    public void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air, int mode) {
        IBlockState iblockstate = mc.theWorld.getBlockState(pos);
        if ((air || iblockstate.getBlock() != Blocks.air) && mc.theWorld.getWorldBorder().contains(pos)) {
            assert (mc.getRenderViewEntity() != null);
            Vec3 interp = interpolateEntity(mc.getRenderViewEntity(), ((IMixinMinecraft) mc).timer().renderPartialTicks);
            drawBlockOutline(iblockstate.getBlock().getSelectedBoundingBox(mc.theWorld, pos).offset(-interp.xCoord, -interp.yCoord, -interp.zCoord), color, linewidth);
        } // .expand(0.002f, 0.002f, 0.002f)
    }

    public void drawBlockOutline(AxisAlignedBB bb, Color color, float linewidth) {
        float red = (float) color.getRed() / 255.0f;
        float green = (float) color.getGreen() / 255.0f;
        float blue = (float) color.getBlue() / 255.0f;
        float alpha = (float) color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)linewidth);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getWorldRenderer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


}

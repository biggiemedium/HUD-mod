package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

public class BlockHighlight extends ToggleableElement {

    public BlockHighlight() {
        super("Block Highlight", "", HUDType.MOD);
    }

    Setting<Float> lineWidth = create(new Setting<>("Line Width", 1.0f, 5.0f, 0.1f));
    Setting<Mode> mode = create(new Setting<>("Mode", Mode.Outline));
    Setting<Color> highlightColor = create(new Setting<>("Color", new Color(255, 255, 255)));
    Setting<Color> insideColor = create(new Setting<>("Inside Color", new Color(255, 255, 255), v -> mode.getValue() == Mode.Full));

    protected AxisAlignedBB currentBB;
    protected AxisAlignedBB slideBB;

    @Override
    public void disable() {
        this.currentBB = null;
        this.slideBB = null;
    }

    private enum Mode {
        Outline,
        Full
    }

    @Override
    public void onRender(Render3dEvent event) {

        if(player == null || world == null || mc.objectMouseOver == null) return;

        if(mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            GL11.glPushMatrix();
            glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            glDisable(GL11.GL_TEXTURE_2D);
            glEnable(GL11.GL_LINE_SMOOTH);
            glDisable(GL11.GL_DEPTH_TEST);
            glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            Renderutil.color(new Color(152, 217, 0));
            double x = pos.getX() - ((MixinRenderManager) mc).getRenderPosX();
            double y = pos.getY() - ((MixinRenderManager) mc).getRenderPosY();
            double z = pos.getZ() - ((MixinRenderManager) mc).getRenderPosZ();
            double height = mc.theWorld.getBlockState(pos).getBlock().getBlockBoundsMaxY() - mc.theWorld.getBlockState(pos).getBlock().getBlockBoundsMinY();
            GL11.glLineWidth(1);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y,z);
            GL11.glVertex3d(x,y + height,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y,z);
            GL11.glVertex3d(x + 1,y + height,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y,z + 1);
            GL11.glVertex3d(x + 1,y + height,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y,z + 1);
            GL11.glVertex3d(x,y + height,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y,z);
            GL11.glVertex3d(x + 1,y,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y + height,z);
            GL11.glVertex3d(x + 1,y + height,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y,z);
            GL11.glVertex3d(x,y,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y + height,z);
            GL11.glVertex3d(x,y + height,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y,z + 1);
            GL11.glVertex3d(x + 1,y,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y + height,z + 1);
            GL11.glVertex3d(x + 1,y + height,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y,z + 1);
            GL11.glVertex3d(x + 1,y,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x + 1,y + height,z + 1);
            GL11.glVertex3d(x + 1,y + height,z);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y,z + 1);
            GL11.glVertex3d(x + 1,y,z + 1);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x,y + height,z + 1);
            GL11.glVertex3d(x + 1,y + height,z + 1);
            GL11.glEnd();

//            GL11.glBegin(GL11.GL_LINE_STRIP);
//            GL11.glVertex3d(x,y,z);
//            GL11.glVertex3d(x,y + 1,z);
//            GL11.glVertex3d(x + 1,y + 1,z);
//            GL11.glVertex3d(x + 1,y,z);
//            GL11.glVertex3d(x,y,z);
//            GL11.glEnd();
//            GL11.glBegin(GL11.GL_LINE_STRIP);
//            GL11.glVertex3d(x + 1,y,z);
//            GL11.glVertex3d(x + 1,y,z + 1);
//            GL11.glVertex3d(x + 1,y + 1,z + 1);
//            GL11.glVertex3d(x + 1,y + 1,z);
//            GL11.glEnd();
//            GL11.glBegin(GL11.GL_LINE_STRIP);
//            GL11.glVertex3d(x + 1,y,z + 1);
//            GL11.glVertex3d(x, y,z + 1);
//            GL11.glVertex3d(x,y + 1,z + 1);
//            GL11.glVertex3d(x + 1,y + 1,z + 1);
//            GL11.glEnd();
//            GL11.glBegin(GL11.GL_LINE_STRIP);
//            GL11.glVertex3d(x,y,z);
//            GL11.glv

            GL11.glDepthMask(true);
            glEnable(GL11.GL_DEPTH_TEST);
            glDisable(GL11.GL_LINE_SMOOTH);
            glEnable(GL11.GL_TEXTURE_2D);
            glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            GL11.glColor4f(1, 1, 1, 1);
        }

    }

    public static void drawOutlineBoundingBox(AxisAlignedBB boundingBox) {

        Tessellator tessellator = Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public void drawSolidBox(AxisAlignedBB bb) {

        glBegin(GL_QUADS); {
            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.minY, bb.maxZ);

            glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.minZ);

            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);
        }
        glEnd();
    }

}

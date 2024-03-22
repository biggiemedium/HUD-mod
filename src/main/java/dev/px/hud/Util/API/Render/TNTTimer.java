package dev.px.hud.Util.API.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class TNTTimer {

    private final DecimalFormat timeFormatter = new DecimalFormat("0.00");
    private int checkTimer;
    private boolean playingBedwars;
    Minecraft mc = Minecraft.getMinecraft();

    public void init(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START
                // no need to check super often, they're likely not going to see tnt within 5 seconds
                // of moving out of a bedwars game/lobby
                || this.checkTimer++ < 250) {
            return;
        }

        this.checkTimer = 0;

        if(mc.getCurrentServerData().serverIP == null) {
            this.playingBedwars = false;
            return;
        }

        final WorldClient world = mc.theWorld;
        if (world == null) {
            this.playingBedwars = false;
            return;
        }

        final Scoreboard scoreboard = world.getScoreboard();
        if (scoreboard == null) {
            this.playingBedwars = false;
            return;
        }

        final ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective != null) {
            playingBedwars = EnumChatFormatting.getTextWithoutFormattingCodes(sidebarObjective.getDisplayName()).contains("BED WARS");
        }
    }

    public void renderTag(RenderTNTPrimed tntRenderer, EntityTNTPrimed tntPrimed, double x, double y, double z, float partialTicks) {
        // hypixel changes the fuse time in bedwars to explode around the 28th tick,
        // which makes the value of the fuse starting timer presumably 52 instead of 80
        // this can fluctuate between 27 and 28, but 28 seems to be more common, so we can sit with that instead
        int fuseTimer = this.playingBedwars ? tntPrimed.fuse - 28 : tntPrimed.fuse;
        if (fuseTimer < 1) return;
        double distance = tntPrimed.getDistanceSqToEntity(tntRenderer.getRenderManager().livingPlayer);

        if (distance <= 4096D) {
            float number = (fuseTimer - partialTicks) / 20F;
            String time = this.timeFormatter.format(number);
            FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.0F, (float) y + tntPrimed.height + 0.5F, (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

            int xMultiplier = 1; // Nametag x rotations should flip in front-facing 3rd person

            if (mc.gameSettings.thirdPersonView == 2) {
                xMultiplier = -1;
            }

            float scale = 0.02666667f;
            GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-scale, -scale, scale);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int stringWidth = fontrenderer.getStringWidth(time) >> 1;
            // refer to the comment at the top of the method
            float green = Math.min(fuseTimer / (this.playingBedwars ? 52 : 80f), 1f);
            Color color = new Color(1f - green, green, 0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(-stringWidth - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(-stringWidth - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(stringWidth + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(stringWidth + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(time, -fontrenderer.getStringWidth(time) >> 1, 0, color.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }
}

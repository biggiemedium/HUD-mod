package dev.px.hud.Util;

import dev.px.hud.Util.API.Util;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Renderutil extends Util {

    public static void drawRect(double d0, double d1, double d2, double d3, int i) {
        double d4;

        if (d0 < d2) {
            d4 = d0;
            d0 = d2;
            d2 = d4;
        }

        if (d1 < d3) {
            d4 = d1;
            d1 = d3;
            d3 = d4;
        }

        float f = (float) (i >> 24 & 255) / 255.0F;
        float f1 = (float) (i >> 16 & 255) / 255.0F;
        float f2 = (float) (i >> 8 & 255) / 255.0F;
        float f3 = (float) (i & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(d0, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d1, 0.0D).endVertex();
        worldrenderer.pos(d0, d1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect(float f, float f1, float f2, float f3, float f4, int i, int j) {
        drawRect(f, f1, f2, f3, j);
        float f5 = (float) (i >> 24 & 255) / 255.0F;
        float f6 = (float) (i >> 16 & 255) / 255.0F;
        float f7 = (float) (i >> 8 & 255) / 255.0F;
        float f8 = (float) (i & 255) / 255.0F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glLineWidth(f4);
        GL11.glBegin(1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static Color staticRainbow() {
        float hue = (float) (System.currentTimeMillis() % 11520L) / 12000.0f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        return new Color(red, green, blue);
    }

    public static Color dynamicRainbow(long offset, float brightness, int speed) {
        float hue = (float) (System.nanoTime() + offset * speed) / 1.0E10f % 1.0f;
        int color = (int) Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, brightness, 1.0f)), 16);
        return new Color(new Color(color).getRed() / 255.0f, new Color(color).getGreen() / 255.0f, new Color(color).getBlue() / 255.0f, new Color(color).getAlpha() / 255.0f);
    }
}

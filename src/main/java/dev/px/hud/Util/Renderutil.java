package dev.px.hud.Util;

import dev.px.hud.Mixin.Render.MixinGui;
import dev.px.hud.Util.API.Util;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.util.LinkedList;

public class Renderutil extends Util {

    protected static float zLevel;


    public static void drawRect(double x, double y, double width, double height, int color) {
        double d4;

        if (x < width) {
            d4 = x;
            x = width;
            width = d4;
        }

        if (y < height) {
            d4 = y;
            y = height;
            height = d4;
        }

        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, height, 0.0D).endVertex();
        worldrenderer.pos(width, height, 0.0D).endVertex();
        worldrenderer.pos(width, y, 0.0D).endVertex();
        worldrenderer.pos(x, y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedRect(float startX, float startY, float endX, float endY, float radius, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        float z = 0;
        if (startX > endX) {
            z = startX;
            startX = endX;
            endX = z;
        }

        if (startY > endY) {
            z = startY;
            startY = endY;
            endY = z;
        }

        double x1 = startX + radius;
        double y1 = startY + radius;
        double x2 = endX - radius;
        double y2 = endY - radius;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1);

        GlStateManager.color(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_POLYGON);

        double degree = Math.PI / 180;
        for (double i = 0; i <= 90; i += 1)
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        for (double i = 90; i <= 180; i += 1)
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 180; i <= 270; i += 1)
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 270; i <= 360; i += 1)
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.popMatrix();
    }

    public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0f;
        float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (col1 & 0xFF) / 255.0f;
        float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }

    public static void rect(final double x, final double y, final double width, final double height, final boolean filled, final Color color) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        if (color != null)
            color(color);
        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINES);

        {
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x + width, y);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x, y + height);
            if (!filled) {
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x, y + height);
                GL11.glVertex2d(x + width, y);
                GL11.glVertex2d(x + width, y + height);
            }
        }
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0f;
        float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (col1 & 0xFF) / 255.0f;
        float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }

    public static void gradient(final double x, final double y, final double width, final double height, final boolean filled, final Color color1, final Color color2) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableAlpha();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        if (color1 != null)
            color(color1);
        GL11.glBegin(filled ? GL11.GL_QUADS : GL11.GL_LINES);
        {
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x + width, y);
            if (color2 != null)
                color(color2);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x, y + height);
            if (!filled) {
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x, y + height);
                GL11.glVertex2d(x + width, y);
                GL11.glVertex2d(x + width, y + height);
            }
        }
        GL11.glEnd();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glShadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);
    }

    public static void gradient(final double x, final double y, final double width, final double height, final Color color1, final Color color2) {
        gradient(x, y, width, height, true, color1, color2);
    }

    public static void rect(final double x, final double y, final double width, final double height, final boolean filled) {
        rect(x, y, width, height, filled, null);
    }

    public static void rect(final double x, final double y, final double width, final double height, final Color color) {
        rect(x, y, width, height, true, color);
    }

    public static void rect(final double x, final double y, final double width, final double height) {
        rect(x, y, width, height, true, null);
    }


    public static void roundedRect(final double x, final double y, double width, double height, final double edgeRadius, final Color color) {
        final double halfRadius = edgeRadius / 2;
        width -= halfRadius;
        height -= halfRadius;

        float sideLength = (float) edgeRadius;
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        if (color != null)
            color(color);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 180; i <= 270; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
            }
            GL11.glVertex2d(x + sideLength, y + sideLength);
        }

        GL11.glEnd();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 0; i <= 90; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                GL11.glVertex2d(x + width + (sideLength * Math.cos(angle)), y + height + (sideLength * Math.sin(angle)));
            }
            GL11.glVertex2d(x + width, y + height);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 270; i <= 360; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                GL11.glVertex2d(x + width + (sideLength * Math.cos(angle)), y + (sideLength * Math.sin(angle)) + sideLength);
            }
            GL11.glVertex2d(x + width, y + sideLength);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 90; i <= 180; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength, y + height + (sideLength * Math.sin(angle)));
            }
            GL11.glVertex2d(x + sideLength, y + height);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        color(Color.white);

        // Main block
        rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);

        // Horizontal bars
        rect(x, y + halfRadius, edgeRadius / 2, height - halfRadius, color);
        rect(x + width, y + halfRadius, edgeRadius / 2, height - halfRadius, color);

        // Vertical bars
        rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
        rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
    }

    public static void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static void color(final double red, final double green, final double blue) {
        color(red, green, blue, 1);
    }

    public static void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static void color(Color color, final int alpha) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.5);
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

    /**
     * @author GiantNuker
     * @since 01/29/2022
     */
    public static class ScissorStack {
        // scissor stack
        private final LinkedList<Rectangle> scissorStack = new LinkedList<>();

        /**
         * Applies scissor test to a specified section of the screen
         * @param x Lower x
         * @param y Lower y
         * @param width Upper x
         * @param height Upper y
         */
        public void pushScissor(int x, int y, int width, int height) {
            Rectangle scissor;

            // resolution
            ScaledResolution resolution = new ScaledResolution(mc);

            // scaled
            int sx = x * resolution.getScaleFactor();
            int sy = (resolution.getScaledHeight() - (y + height)) * resolution.getScaleFactor();
            int sWidth = ((x + width) - x) * resolution.getScaleFactor();
            int sHeight = ((y + height) - y) * resolution.getScaleFactor();

            if (!scissorStack.isEmpty()) {
                GL11.glDisable(GL11.GL_SCISSOR_TEST);

                Rectangle last = scissorStack.getLast();

                int nx = Math.max(sx, last.x);
                int ny = Math.max(sy, last.y);

                int hDiff = sx - nx;
                int nWidth = Math.min(Math.min(last.width + (last.x - sx), last.width), sWidth + hDiff);

                int diff = sy - ny;
                int nHeight = Math.min(Math.min(last.height + (last.y - sy), last.height), hDiff + diff);

                scissor = new Rectangle(nx, ny, nWidth, nHeight);
            }

            else {
                scissor = new Rectangle(sx, sy, sWidth, sHeight);
            }

            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            if (scissor.width > 0 && scissor.height > 0) {
                GL11.glScissor(scissor.x, scissor.y, scissor.width, scissor.height);
            }

            else {
                GL11.glScissor(0, 0, 0, 0);
            }

            scissorStack.add(scissor);
        }

        /**
         * Disables scissor test
         */
        public void popScissor() {
            if (!scissorStack.isEmpty()) {
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                scissorStack.removeLast();

                if (!scissorStack.isEmpty()) {
                    Rectangle scissor = scissorStack.getLast();
                    GL11.glEnable(GL11.GL_SCISSOR_TEST);
                    GL11.glScissor(scissor.x, scissor.y, scissor.width, scissor.height);
                }
            }
        }
    }
}

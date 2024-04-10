package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.Color.Colorutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Render.Stencilutil;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class CompassElement extends RenderElement {

    public CompassElement() {
        super("Compass", 5, 65, 180, 28, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render2D(Render2DEvent event) {
        GlStateManager.pushMatrix();
        for (final Direction dir : Direction.values()) {
            final double rad = getPosOnCompass(dir);
            if(customFont.getValue()) {
                Fontutil.drawTextShadow(dir.name(), (getX() + 15 + (int) getXX(rad)), getY() + 15 + (int) getYY(rad), fontColor.getValue().getRGB());
            } else {
                mc.fontRendererObj.drawStringWithShadow(dir.name(), (getX() + (int) getXX(rad)), getY() + (int) getYY(rad), fontColor.getValue().getRGB());
            }
            setWidth(40);
            setHeight(customFont.getValue() ? (int) Fontutil.getHeight() * 3 : mc.fontRendererObj.FONT_HEIGHT * 3);
        }

        GlStateManager.popMatrix();
    }

    private double getXX(double rad) {
        return Math.sin(rad) * (2 * 10);
    }

    private double getYY(double rad) {
        final double epicPitch = MathHelper.clamp_double(mc.thePlayer.rotationPitch + 30.0f, -90.0f, 90.0f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * (2 * 10);
    }

    private double getPosOnCompass(final Direction dir) {
        final double yaw = Math.toRadians(MathHelper.wrapAngleTo180_double(mc.thePlayer.rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }

    private enum Direction {
        N("-Z"),
        W("-X"),
        S("+Z"),
        E("+X");

        private String alternate;

        Direction(final String alternate) {
            this.alternate = alternate;
        }

        public String getAlternate() {
            return this.alternate;
        }
    }
}

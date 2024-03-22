package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PotionsElement extends RenderElement {

    public PotionsElement() {
        super("Potions", 180, 20, 110, 40, HUDType.INFO);
        setTextElement(true);
    }

    int zLevel = 0;
    private int longestText = 118;
    private int adjustedHeight = 40;
    private Animation toggleAnimation = new Animation(300, false, Easing.LINEAR);
    int offsetY = 0;

    @Override
    public void render2D(Render2DEvent event) {

        this.toggleAnimation.setState(!mc.thePlayer.getActivePotionEffects().isEmpty());
        offsetY = 0;
        if(!toggleAnimation.getState()) {
            this.longestText = 118;
            this.adjustedHeight = 40;
        }

        GL11.glPushMatrix();
        Renderutil.ScissorStack stack = new Renderutil.ScissorStack();
        RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 6, new Color(10, 10, 10, 120));
        renderText("Potions Active", (getX() + 5), getY() + 3, fontColor.getValue().getRGB());

        Comparator<PotionEffect> comparator = new Comparator<PotionEffect>() {
            @Override
            public int compare(PotionEffect effect1, PotionEffect effect2) {
                int length1 = effect1.getEffectName().length();
                int length2 = effect2.getEffectName().length();
                return Integer.compare(length1, length2);
            }
        };

        java.util.ArrayList<PotionEffect> effects = new ArrayList<>();
        for (PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
            if (potionEffect.getDuration() != 0 && !potionEffect.getEffectName().contains("effect.nightVision")) {
                effects.add(potionEffect);
            } else {
                effects.remove(potionEffect);
            }
        }
        Collections.sort(effects, comparator);

        if(mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.longestText = 118;
            this.adjustedHeight = 40;
        }
        for (PotionEffect potionEffect : effects) {
            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
            String power = "";
            if (potionEffect.getAmplifier() == 0) {
                power = "I";
            } else if (potionEffect.getAmplifier() == 1) {
                power = "II";
            } else if (potionEffect.getAmplifier() == 2) {
                power = "III";
            } else if (potionEffect.getAmplifier() == 3) {
                power = "IV";
            } else if (potionEffect.getAmplifier() == 4) {
                power = "V";
            }
            String s = potionEffect.getEffectName().replace("potion.", "") + " " + power;
            String s2 = getDuration(potionEffect) + "";

            GlStateManager.pushMatrix();
            GlStateManager.resetColor();
            renderText(s + "  " + s2, getX() + 5, getY() + 20 + offsetY, fontColor.getValue().getRGB());
            GlStateManager.resetColor();
            offsetY += getFontHeight() + 1;
            if(getFontWidth(potionEffect.getEffectName()) > 118) {
                longestText = getFontWidth(potionEffect.getEffectName());
            } else {
                longestText = 118;
            }
            if(20 + offsetY > 40) {
                adjustedHeight = (int) ((20 + offsetY) * toggleAnimation.getAnimationFactor());
            } else {
                adjustedHeight = 40;
            }
            setWidth(longestText);
            setHeight(adjustedHeight);
            GlStateManager.popMatrix();
        }

        GL11.glPopMatrix();

        setWidth(longestText);
        setHeight(adjustedHeight);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getWorldRenderer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, this.zLevel).tex((float) (textureX) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.pos(x + width, y + height, this.zLevel).tex((float) (textureX + width) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.pos(x + width, y, this.zLevel).tex((float) (textureX + width) * 0.00390625F, (float) (textureY) * 0.00390625F).endVertex();
        bufferbuilder.pos(x, y, this.zLevel).tex((float) (textureX) * 0.00390625F, (float) (textureY) * 0.00390625F).endVertex();
        tessellator.draw();
    }

    public static String getDuration(PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return "**:**";
        } else {
            return StringUtils.ticksToElapsedTime(potionEffect.getDuration());
        }
    }
}

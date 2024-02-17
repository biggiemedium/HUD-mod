package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

public class TargetHUD extends RenderElement {

    public TargetHUD() {
        super("TargetHUD", 200, 200, HUDType.COMBAT);
        setWidth(115);
        setHeight(80);
    }

    /* Target checking */
    private EntityPlayer target;
    private boolean combatCheck;
    private Timer timer = new Timer();
    private Timer scaleTimer = new Timer();

    private double scale = 1;
    private float health;

    @Override
    public void render(float partialTicks) {

        if (timer.passed(4000)) {
            if (target != null && (target.getDistanceSqToEntity(mc.thePlayer) > 100 || mc.theWorld.getEntityByID(Objects.requireNonNull(target).getEntityId()) == null)) {
                this.scale = 0;
                timer.reset();
            } else {
                scale = 1;
            }
        }

        if(mc.currentScreen instanceof PanelGUIScreen) {
            if(PanelGUIScreen.INSTANCE.getCurrentPanel().getClass() == ClickGUI.class) {
                this.target = mc.thePlayer;
                this.scale = 1;
            }
        } else {
            if(!timer.passed(7)) {
                target = (EntityPlayer) Entityutil.getTarget(15, true);
                this.scale = 0;
            }
        }

        if(scale == 0) return;

        if(target != null) {

            GL11.glPushMatrix();

            GlStateManager.enableTexture2D();

            GL11.glPushMatrix();
            GlStateManager.translate((getX() + 38 + 2 + 129 / 2f) * (1 - scale), (getY() - 34 + 48 / 2f) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);
            Renderutil.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 1.5f, new Color(35, 33, 33, 200));
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GlStateManager.translate((getX() + 38 + 2 + 129 / 2f) * (1 - scale), (getY() - 34 + 48 / 2f) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);
            int scaleOffset = (int) (((EntityPlayer) target).hurtTime * 0.35f);
            if (target instanceof AbstractClientPlayer) {
                double offset = -(((AbstractClientPlayer) target).hurtTime * 23);
                Renderutil.color(new Color(255, (int) (255 + offset), (int) (255 + offset)));
                renderPlayerModelTexture(getX() + 5 + scaleOffset / 2f, getY() + 4 + scaleOffset / 2f, 3, 3, 3, 3, (30 - scaleOffset), (30 - scaleOffset), 24, 24.5f, (AbstractClientPlayer) target);
                renderPlayerModelTexture(getX() + 5 + scaleOffset / 2f, getY() + 4 + scaleOffset / 2f, 15, 3, 3, 3, 30 - scaleOffset, 30 - scaleOffset, 24, 24.5f, (AbstractClientPlayer) target);
                Renderutil.color(Color.WHITE);
            }
            GL11.glPopMatrix();

            if (!String.valueOf(((EntityPlayer) target).getHealth()).equals("NaN")) {
                health = (float) Math.random() * 20;
            } else {
                health = Math.min(20,Entityutil.getHealth(target));
            }

            /* Held item */
            if(target.getHeldItem() != null) {
                renderItemStack(target.getHeldItem(), getX() + 6, getY() + 40);
            }

            /* armor */
            int offset = 0;
            for(int i = 4 - 1; i >= 0; i--) {
                renderItemStack(mc.thePlayer.getCurrentArmor(i), getX() + (getWidth() - 16), getY() + offset);
                offset += 15;
            }

            /* Display Info */
            GlStateManager.pushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //    Renderutil.scissor(getX() + 32, getY() + 4 - Fontutil.getHeight() * 4, 91, 30);

            Fontutil.drawTextShadow(target.getName(), getX() + 32, getY() + 4, -1);
            Fontutil.drawTextShadow("Health: " + health, getX() + 32, getY() + (int) (4 + Fontutil.getHeight()), -1);

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();


            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GL11.glPopMatrix();
            scaleTimer.reset();
        }
    }

    public void renderPlayerModelTexture(float x, float y, float u, float v, int uWidth, int vHeight, int width, int height, final float tileWidth, final float tileHeight, final AbstractClientPlayer target) {
        ResourceLocation skin = target.getLocationSkin();
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        GL11.glEnable(GL11.GL_BLEND);
        this.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();

        GlStateManager.disableAlpha();
        mc.getRenderItem().zLevel = -150.0F;

        GlStateManager.disableCull();

        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y);

        GlStateManager.enableCull();

        mc.getRenderItem().zLevel = 0;

        GlStateManager.disableBlend();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        GlStateManager.disableDepth();
        GlStateManager.disableLighting();

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();

        GlStateManager.scale(2.0F, 2.0F, 2.0F);

        GlStateManager.enableAlpha();

        GlStateManager.popMatrix();
    }


    private void drawScaledCustomSizeModalRect(float x, float y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    @SubscribeEvent
    public void attack(AttackEntityEvent event) {
        if(event.entity instanceof EntityPlayer) {
            if(event.target != mc.thePlayer) {
                timer.reset();
            }
        }
        if(event.target == mc.thePlayer) {
            timer.reset();
        }
    }

}

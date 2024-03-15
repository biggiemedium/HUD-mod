package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
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

public class TargetHUD extends RenderElement {

    public TargetHUD() {
        super("TargetHUD", 200, 200, HUDType.COMBAT);
        setWidth(115);
        setHeight(80);
        setTextElement(false);
    }

    private EntityPlayer currentTarget = null;
    private Renderutil.ScissorStack scissorStack = new Renderutil.ScissorStack();
    private Animation openAnimation = new Animation(200, false, Easing.LINEAR);

    @Override
    public void render2D(Render2DEvent event) {

        if(mc.currentScreen == HUDMod.screen) {
            openAnimation.setState(true);
            currentTarget = mc.thePlayer;
        } else if (mc.currentScreen != HUDMod.screen && currentTarget == mc.thePlayer) {
            currentTarget = null;
        }

        if(currentTarget != null) {
            openAnimation.setState(true);
        } else {
            openAnimation.setState(false);
        }

        if(openAnimation.getAnimationFactor() > 0) {
            scissorStack.pushScissor(getX(), getY(), (int) (getWidth() * openAnimation.getAnimationFactor()), getHeight());


            RoundedShader.drawRound((float) getX(), (float) getY(), (float) (getWidth() * openAnimation.getAnimationFactor()), (float) getHeight(), 6, new Color(26, 26, 26, 120));
            Renderutil.drawBlurredShadow(getX(), getY(), (float) (getWidth() * openAnimation.getAnimationFactor()), getHeight(), 4,  new Color(26, 26, 26, 100));

            scissorStack.popScissor();
        }
    }

    @Override
    public void render(float partialTicks) {

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

    }

}

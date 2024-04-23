package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.HotbarModifications;
import dev.px.hud.Util.API.Animation.SimpleAnimation;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.EventRenderScoreBoard;
import dev.px.hud.Util.Event.Render.RenderImageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiIngame.class)
public abstract class MixinGUIIngame extends Gui {


    @Shadow
    protected static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");

    @Shadow
    protected abstract void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player);

    public SimpleAnimation simpleAnimation = new SimpleAnimation(0.0F);

    @Inject(method = "renderTooltip", at = @At("RETURN"), cancellable = true)
    public void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        RenderImageEvent event = new RenderImageEvent(sr, partialTicks);
        if(event.isCanceled()) {
            ci.cancel();
        }
    }

    @Overwrite
    public void renderTooltip(ScaledResolution sr, float partialTicks) {
        if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {

            boolean animation = HUDMod.elementInitalizer.getElementByClass(HotbarModifications.class).animation.getValue();
            int animationSpeed = HUDMod.elementInitalizer.getElementByClass(HotbarModifications.class).speed.getValue();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(widgetsTexPath);
            EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
            int i = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            this.zLevel = -90.0F;

            simpleAnimation.setAnimation(entityplayer.inventory.currentItem * 20, animationSpeed);
            int itemX = i - 91 + (animation ? (int) simpleAnimation.getValue() : (entityplayer.inventory.currentItem * 20));

            if(HUDMod.elementInitalizer.isElementToggled(HotbarModifications.class)) {
                switch(HUDMod.elementInitalizer.getElementByClass(HotbarModifications.class).mode.getValue()) {
                    case Vanilla:
                        this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
                        this.drawTexturedModalRect(i - 91 - 1 + (animation ? simpleAnimation.getValue() : (entityplayer.inventory.currentItem * 20)), sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
                        break;
                    case Skid:
                        drawRect(0, sr.getScaledHeight() - 22, sr.getScaledWidth(), sr.getScaledHeight() + 22, new Color(20, 20, 20, 180).getRGB());
                        drawRect(itemX, sr.getScaledHeight() - 22, itemX + 22, sr.getScaledHeight(), new Color(230, 230, 230, 180).getRGB());
                        break;
                    case Clear:
                        drawRect(itemX, sr.getScaledHeight() - 22, itemX + 22, sr.getScaledHeight(), new Color(230, 230, 230, 180).getRGB());
                        break;
                    case Round:
                        RoundedShader.drawRound(i - 91, sr.getScaledHeight() - 22, 180, 22, 4, new Color(26, 26, 26, 180));
                        RoundedShader.drawRound(itemX, sr.getScaledHeight() - 22, 22, sr.getScaledHeight(), 4, new Color(230, 230, 230, 150));
                }
            }else {
                this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
                this.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
            }

            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();

            for (int j = 0; j < 9; ++j) {
                int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                int l = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    public void renderScoreBoard(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
        EventRenderScoreBoard packet = new EventRenderScoreBoard(objective);
        MinecraftForge.EVENT_BUS.post(packet);

        if(packet.isCanceled()) {
            ci.cancel();
        }
    }

}

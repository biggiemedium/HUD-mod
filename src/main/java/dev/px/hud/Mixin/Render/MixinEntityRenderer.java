package dev.px.hud.Mixin.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.MotionBlur;
import dev.px.hud.Rendering.HUD.Mods.NoRender;
import dev.px.hud.Util.API.Render.MotionBlurRenderer;
import dev.px.hud.Util.Event.Render.HurtCamEvent;
import dev.px.hud.Util.Event.Render.GUIIngameRenderEvent;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Shadow
    private ShaderGroup theShaderGroup;

    @Shadow
    private boolean useShader;


    MotionBlurRenderer motion = new MotionBlurRenderer();
    /*
    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFog(int startCoords, float partialTicks, CallbackInfo ci) {
        if(HUDMod.elementInitalizer.isElementToggled(NoRender.class) && HUDMod.elementInitalizer.getElementByClass(NoRender.class).fog.getValue()) {
            ci.cancel();
        }
    }

     */

    /*
    // DONT FUCK WITH THIS
    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V"))
    public void onRender2D(GuiIngame guiIngame, float partialTicks) {
        GUIIngameRenderEvent packet = new GUIIngameRenderEvent(partialTicks);
        guiIngame.renderGameOverlay(partialTicks);
        MinecraftForge.EVENT_BUS.post(packet);
    }

     */

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void onHurtCam(float partialTicks, CallbackInfo ci) {
        HurtCamEvent event = new HurtCamEvent();
        MinecraftForge.EVENT_BUS.post(event);

        if(event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;"), expect = 0)
    public MovingObjectPosition movePos(WorldClient worldClient, Vec3 start, Vec3 end) {
        if(HUDMod.elementInitalizer.isElementToggled(NoRender.class) && HUDMod.elementInitalizer.getElementByClass(NoRender.class).viewClip.getValue()) {
            return null;
        } else {
            return worldClient.rayTraceBlocks(start, end);
        }
    }

    // Taken from soar client
    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader" + "/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE))
    public void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {
        /*
        List<ShaderGroup> shaders = new ArrayList<ShaderGroup>();

        if (this.theShaderGroup != null && this.useShader) {
            shaders.add(this.theShaderGroup);
        }
        ShaderGroup motionBlur = motion.getShader();

        if(HUDMod.elementInitalizer.getElementByClass(MotionBlur.class).isToggled()) {
            if (motionBlur != null){
                shaders.add(motionBlur);
            }

            for (ShaderGroup shader : shaders){
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                shader.loadShaderGroup(partialTicks);
                GlStateManager.popMatrix();
            }
        }

         */
    }

    @Inject(method = "updateShaderGroupSize", at = @At("RETURN"))
    public void updateShaderGroupSize(int width, int height, CallbackInfo ci) {
        /*
        if(Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) {
            return;
        }

        if (OpenGlHelper.shadersSupported) {
            ShaderGroup motionBlur = motion.getShader();

            if (motionBlur != null){
                motionBlur.createBindFramebuffers(width, height);
            }
        }


         */
    }
}

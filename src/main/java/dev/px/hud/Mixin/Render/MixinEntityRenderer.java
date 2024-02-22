package dev.px.hud.Mixin.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.NoRender;
import dev.px.hud.Util.Event.Render.HurtCamEvent;
import dev.px.hud.Util.Event.Render.GUIIngameRenderEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    // DONT FUCK WITH THIS
    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V"))
    public void onRender2D(GuiIngame guiIngame, float partialTicks) {
        GUIIngameRenderEvent packet = new GUIIngameRenderEvent(partialTicks);
        guiIngame.renderGameOverlay(partialTicks);
        MinecraftForge.EVENT_BUS.post(packet);
    }

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

}

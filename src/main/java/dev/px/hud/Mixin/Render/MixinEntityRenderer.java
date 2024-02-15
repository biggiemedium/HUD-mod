package dev.px.hud.Mixin.Render;

import com.sun.javafx.geom.Vec3d;
import dev.px.hud.Util.Event.HurtCamEvent;
import dev.px.hud.Util.Event.Render2dEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V"))
    public void onRender2D(GuiIngame guiIngame, float partialTicks) {
        Render2dEvent packet = new Render2dEvent(partialTicks);
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
}

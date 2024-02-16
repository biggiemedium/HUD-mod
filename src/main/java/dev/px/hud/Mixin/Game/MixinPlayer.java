package dev.px.hud.Mixin.Game;

import dev.px.hud.Util.Event.ClientUpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinPlayer {

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        ClientUpdateEvent packet = new ClientUpdateEvent();
        MinecraftForge.EVENT_BUS.post(packet);
        if(packet.isCanceled()) {
            ci.cancel();
        }
    }

}

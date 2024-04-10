package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.HUDMod;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {

    @Inject(method = "connectToServer", at = @At("RETURN"), cancellable = true)
    public void onConnection(ServerData data, CallbackInfo ci) {
        HUDMod.timeManager.setPlaytimeElapsed(System.currentTimeMillis());
    }

}

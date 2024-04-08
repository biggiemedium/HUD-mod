package dev.px.hud.Mixin.Render.GUI;

import dev.px.hud.Util.API.Alt.AltManagerGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {

    @Inject(method = "initGui", at = @At("RETURN"))
    public void initGui(CallbackInfo info) {
        buttonList.add(new GuiButton(417, 7, 7, 60, 20, "Alts"));
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void actionPerformed(GuiButton button, CallbackInfo info) {
        if (button.id == 417) {
            mc.displayGuiScreen(new AltManagerGUI(this));
        }
    }

}

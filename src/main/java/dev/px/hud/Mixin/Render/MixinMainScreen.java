package dev.px.hud.Mixin.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Rendering.MCGUI.HUDMenuGUI;
import dev.px.hud.Util.API.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinMainScreen extends GuiScreen {

    @Shadow(remap = false)
    private GuiButton realmsButton;

    //@Inject(method = "addSingleplayerMultiplayerButtons", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;addButton(Lnet/minecraft/client/gui/GuiButton;)Lnet/minecraft/client/gui/GuiButton;"), cancellable = true)
    //public void removeRealmsButtonMainMenu(int p_73969_1_, int p_73969_2_, CallbackInfo ci)
    //{
    //    buttonList.add(realmsButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("fml.menu.mods")));
    //    ci.cancel();
    //}

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

    }

    @Inject(method = "initGui", at = @At("TAIL"), cancellable = true)
    public void initGui(CallbackInfo info) {
        this.buttonList.add(new GuiButton(420, this.width / 2 + 2, this.height / 4 + 48 + 24 * 2, 98, 20, "HUD Mod"));
        this.buttonList.remove(realmsButton);
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    public void actionPerformed(GuiButton button, CallbackInfo info) {
        if(button.id == 420) {
            mc.displayGuiScreen(new HUDMenuGUI());
        }
    }

}

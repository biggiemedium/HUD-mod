package dev.px.hud.Mixin.Game;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.UnfocusedCPU;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "createDisplay", at = @At("TAIL"), cancellable = true)
    public void setDisplay(CallbackInfo ci) {
        Display.setTitle(HUDMod.NAME + " | " + HUDMod.VERSION);
    }

    @Inject(method = "Lnet/minecraft/client/Minecraft;getLimitFramerate()I", at = @At("HEAD"), cancellable = true)
    public void preGetLimitFramerate(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if (HUDMod.elementInitalizer.isElementToggled(UnfocusedCPU.class) && !Display.isActive()) {
                callbackInfoReturnable.setReturnValue(5);
            }
        } catch (NullPointerException e) {
        }
    }

    @Inject(method = "shutdown", at = @At("HEAD"), cancellable = true)
    public void shutDown(CallbackInfo ci) {
        HUDMod.configManager.save();
    }


    @Inject(method = "setWindowIcon", at = @At("HEAD"), cancellable = true)
    private void setGameIcon(CallbackInfo c) {
        if(Util.getOSType() == Util.EnumOS.OSX) {
            Renderutil.setDockIcon("/assets/minecraft/GUI/sped.png");
            c.cancel();
        } else {
            // windows impl
      //      c.cancel();
        }
    }

    /*
    @Inject(method = "runTick()V", at = @At("RETURN"))
    public void customGUIScreen(CallbackInfo ci) {
        Minecraft.getMinecraft().displayGuiScreen(new MainMenuGUI());
    }

     */
    


    private ByteBuffer convertImageToBuffer(BufferedImage bufferedimage) throws IOException {
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }
}

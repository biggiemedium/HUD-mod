package dev.px.hud.Mixin.Game;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.UnfocusedCPU;
import dev.px.hud.Rendering.MCGUI.MainMenuGUI;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Mixin(Minecraft.class)
public class MixinMinecraft {

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    @Inject(method = "Lnet/minecraft/client/Minecraft;getLimitFramerate()I", at = @At("HEAD"), cancellable = true)
    public void preGetLimitFramerate(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if (HUDMod.elementInitalizer.isElementToggled(UnfocusedCPU.class) && !Display.isActive()) {
                callbackInfoReturnable.setReturnValue(1);
            }
        } catch (NullPointerException e) {
        }
    }

<<<<<<< Updated upstream
    @Inject(method = "Lnet/minecraft/client/Minecraft;setWindowIcon()V", at = @At("HEAD"), cancellable = true)
    public void preSetWindowIcon(CallbackInfo callbackInfo) {
        try (InputStream in = HUDMod.class.getResourceAsStream("\\assets\\minecraft\\GUI\\sped.png\\")) {
            BufferedImage img = ImageIO.read(in);
            List<ByteBuffer> sizes = new ArrayList<>();
            int w = img.getWidth();
            do  {
                BufferedImage tmp = new BufferedImage(w, w, img.getType());
                tmp.createGraphics().drawImage(img, 0, 0, w, w, null);
                sizes.add(this.convertImageToBuffer(tmp));
                w >>= 1;
            } while (w >= 8);
            Display.setIcon(sizes.toArray(new ByteBuffer[sizes.size()]));
            callbackInfo.cancel();
        } catch (Exception e) {
            new RuntimeException("failed to override the window icon!", e).printStackTrace();
        }

    }
=======

>>>>>>> Stashed changes

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

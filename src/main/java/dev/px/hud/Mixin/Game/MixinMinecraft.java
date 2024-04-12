package dev.px.hud.Mixin.Game;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.UnfocusedCPU;
import dev.px.hud.Rendering.MCGUI.CustomMainMenuGUI;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow public abstract void displayGuiScreen(GuiScreen p_displayGuiScreen_1_);

    @Shadow public GuiScreen currentScreen;

    @Shadow public GuiIngame ingameGUI;

    @Shadow
    private boolean fullscreen;

    @Shadow
    public int displayWidth;

    @Shadow
    public int displayHeight;

    @Inject(method = "shutdown", at = @At("HEAD"), cancellable = true)
    public void onShutdown(CallbackInfo ci) {
        HUDMod.configManager.save();
    }

    @Inject(method = "createDisplay", at = @At("TAIL"), cancellable = true)
    public void setDisplayPost(CallbackInfo ci) {
        Display.setTitle(HUDMod.NAME + " | " + HUDMod.VERSION);
        Display.setResizable(true);
    }

    @Inject(method = "run", at = @At("HEAD"), cancellable = true)
    public void injectStuffTest(CallbackInfo ci) { // WHY DOES IT NOT WORK
        System.setProperty("devauth.enabled", "true");
        System.setProperty("devauth.configDir", "/Users/jameskemp/Devauth");
        System.setProperty("devauth.account", "main");
    }

    @Inject(method = "Lnet/minecraft/client/Minecraft;getLimitFramerate()I", at = @At("HEAD"), cancellable = true)
    public void preGetLimitFramerate(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if (HUDMod.elementInitalizer.isElementToggled(UnfocusedCPU.class)) {
                if(!Display.isActive()) {
                    if(!(currentScreen instanceof CustomMainMenuGUI)) {
                        callbackInfoReturnable.setReturnValue(5);
                    }
                }
            }
        } catch (NullPointerException e) {}
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
        } else if(Util.getOSType() == Util.EnumOS.WINDOWS) {
            // windows impl
      //      c.cancel();
        }
    }

    @Inject(method = "displayGuiScreen", at = @At("RETURN"), cancellable = true)
    public void displayGuiScreenInject(GuiScreen guiScreenIn, CallbackInfo ci) {
        if(guiScreenIn instanceof GuiMainMenu) {
            displayGuiScreen(new CustomMainMenuGUI());
        }
    }

    @Inject(method = "setInitialDisplayMode", at = @At(value = "HEAD"), cancellable = true)
    private void setInitialDisplayMode(CallbackInfo ci) throws LWJGLException {
        displayFix(ci, fullscreen, displayWidth, displayHeight);
    }

    @Inject(method = "toggleFullscreen", at = @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/opengl/Display;setVSyncEnabled(Z)V", shift = At.Shift.AFTER))
    private void toggleFullscreen(CallbackInfo ci) throws LWJGLException {
        fullScreenFix(fullscreen, displayWidth, displayHeight);
    }

    private ByteBuffer convertImageToBuffer(BufferedImage bufferedimage) throws IOException {
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    public void displayFix(CallbackInfo ci, boolean fullscreen, int displayWidth, int displayHeight) throws LWJGLException {
        Display.setFullscreen(false);
        if (fullscreen) {
            if (HUDMod.preferenceManager != null && HUDMod.preferenceManager.windowModifications.getValue()) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            } else {
                Display.setFullscreen(true);
                DisplayMode displaymode = Display.getDisplayMode();
                Minecraft.getMinecraft().displayWidth = Math.max(1, displaymode.getWidth());
                Minecraft.getMinecraft().displayHeight = Math.max(1, displaymode.getHeight());
            }
        } else {
            if (HUDMod.preferenceManager != null && HUDMod.preferenceManager.windowModifications.getValue()) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            } else {
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        }

        Display.setResizable(false);
        Display.setResizable(true);

        ci.cancel();
    }

    public void fullScreenFix(boolean fullscreen, int displayWidth, int displayHeight) throws LWJGLException {
        if (HUDMod.preferenceManager != null && HUDMod.preferenceManager.windowModifications.getValue()) {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        } else {
            Display.setFullscreen(fullscreen);
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
        }

        Display.setResizable(false);
        Display.setResizable(true);
    }
}

package dev.px.hud.Rendering.NewGUI;

import dev.px.hud.Rendering.NewGUI.Screens.MainScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class CSGOGui extends GuiScreen {

    private int x, y, width, height;
    private MainScreen screen;
    private ResourceLocation shaders = new ResourceLocation("minecraft", "shaders/post/blur" + ".json");

    public CSGOGui() {
        this.x = 100;
        this.y = 100;
        this.width = 395;
        this.height = 280;
        this.screen = new MainScreen(x, y, width, height);
    }

    @Override
    public void initGui() {
        screen.init();
    }

    @Override
    public void onGuiClosed() {

        if(mc.theWorld != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(screen == null) return;
        screen.drawScreen(mouseX, mouseY, partialTicks);

        if(mc.theWorld != null) {
            if(OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
                if(mc.entityRenderer.getShaderGroup() != null) {
                    mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }

                mc.entityRenderer.loadShader(shaders);

            }
            if(mc.currentScreen == null || mc.currentScreen != this) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(screen == null) return;
        screen.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(screen == null) return;
        screen.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        screen.keyPressed(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}

package dev.px.hud.ClickGUI;

import dev.px.hud.HUDMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * For some reason the clickgui displays however, it will not display
 */

public class HUDGUI extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.render(partialTicks);
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x_, y_;
        x_ = mouseX;
        y_ = mouseY;
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.mouseClicked(x_, y_, mouseButton);
        });
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            if(!element.isHidden()) {
                element.mouseRelease(mouseX, mouseY, state);
            }
        });
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static HUDGUI INSTANCE = new HUDGUI();
}

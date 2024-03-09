package dev.px.hud.Rendering.NewGUI;

import dev.px.hud.Rendering.NewGUI.Screens.MainContainer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class CSGOGui extends GuiScreen {

    public CSGOGui() {
        width = 200;
        height = 200;
        this.x = mc.displayWidth / 2 - width;
        this.y = mc.displayHeight / 2 - height;
    }

    private int x, y, width, height;
    private MainContainer mainContainer = new MainContainer(x, y, width, height);

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mainContainer.draw(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private enum Mode {
        Modules,
        Editor,
        Profile
    }

}

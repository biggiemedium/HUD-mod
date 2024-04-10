package dev.px.hud.Rendering.MCGUI;

import dev.px.hud.Util.API.Input.BindRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class RetardScreen extends GuiScreen {

    private GuiButton confirm;
    private GuiButton fuckOff;
    ScaledResolution sr = new ScaledResolution(mc);

    public RetardScreen() {

    }

    @Override
    public void initGui() {
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();

        this.buttonList.add(this.confirm = new GuiButton(420, this.width / 2, this.height / 6 + 96 - 56, 150, 20, "Confirm"));
        this.buttonList.add(this.fuckOff = new GuiButton(69, this.width / 2, this.height / 6 + 96 - 6 - 25, 150, 20, "Fuck off"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

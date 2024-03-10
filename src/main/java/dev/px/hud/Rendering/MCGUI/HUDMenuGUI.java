package dev.px.hud.Rendering.MCGUI;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class HUDMenuGUI extends GuiScreen {

    private GuiButton blurButton;
    private GuiButton doneButton;

    private String title = "HUD Mod";
    private int key;

    private boolean textOverride;
    private boolean textAntiAias;

    public HUDMenuGUI() {
        this.key = Keyboard.KEY_RSHIFT;
        this.textOverride = false;
        this.textAntiAias = true;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();

        this.doneButton = new GuiButton(69, this.width / 2, this.height - 38, "Done");
        this.blurButton = new GuiButton(69420, this.width / 2, 30, "Blur");
        buttonList.add(doneButton);
        buttonList.add(blurButton);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 69) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(mc.fontRendererObj, title, this.width / 2, 8, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isTextOverrided() {
        return textOverride;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static HUDMenuGUI getInstance = new HUDMenuGUI();
}

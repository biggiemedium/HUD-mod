package dev.px.hud.Rendering.MCGUI;

import dev.px.hud.Util.API.Input.BindRegistry;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class HUDMenuGUI extends GuiScreen {

    private GuiButton blurButton;
    private GuiButton keybindButton;
    private GuiButton doneButton;

    private String title = "HUD Mod";
    private int key;

    private boolean textOverride;
    private boolean textAntiAias;
   // private GuiScreen screen;

    public HUDMenuGUI() {
    //    this.screen = screen;
        this.key = Keyboard.KEY_RSHIFT;
        this.textOverride = false;
        this.textAntiAias = true;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.blurButton = new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, "Blur"));
        this.buttonList.add(this.keybindButton = new GuiButton(90, this.width / 2 - 155, this.height / 6 + 96 - 6 - 25, 150, 20, BindRegistry.guiKey == null ? "Keybinds" : "Keybind: " + Keyboard.getKeyName(BindRegistry.guiKey.getKeyCode())));

     //   buttonList.add(doneButton);
     //   buttonList.add(blurButton);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 101) { // blur

        }
        if(button.id == 200) {
            mc.displayGuiScreen(new CustomMainMenuGUI());
        }
        if(button.id == 90) {
            mc.displayGuiScreen(new GuiControls(this, mc.gameSettings));
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

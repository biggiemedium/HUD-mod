package dev.px.hud.Rendering.Panel;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.Rendering.Panel.Commands.CommandGUI;
import dev.px.hud.Rendering.Panel.HUDEditor.HudEditorPanel;
import dev.px.hud.Rendering.Panel.Profiles.PlayerProfiles;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PanelGUIScreen extends GuiScreen {

    public PanelGUIScreen() {
        this.panels.add(this.currentPanel = new ClickGUI());
        this.panels.add(new HudEditorPanel());
        this.panels.add(new PlayerProfiles());
        this.panels.add(new CommandGUI());
    }

    private ArrayList<Panel> panels = new ArrayList<>();
    private Panel currentPanel;
    private Color baseColor = new Color(57, 56, 56);

    /*
    We must make visuals for panels
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        int x = 0;

        for(Panel p : this.panels) {
            int renderX = (new ScaledResolution(Wrapper.mc).getScaledWidth() / 2) + x - (panels.size() * mc.fontRendererObj.FONT_HEIGHT);
            if(p == this.currentPanel) {
                // highlight current selected panel
                //Renderutil.drawRect(renderX - 2, 3, renderX + mc.fontRendererObj.getStringWidth(p.getName()) + 5, 15, baseColor.darker().getRGB());
                Renderutil.drawRoundedRect(renderX - 3, 3, renderX + mc.fontRendererObj.getStringWidth(p.getName()) + 5, 15,1, baseColor.darker().getRGB());
            }

            if(p != this.currentPanel) {
             //   Renderutil.drawRect(renderX - 2, 3, renderX + mc.fontRendererObj.getStringWidth(p.getName()) + 5, 15, baseColor.getRGB());
                Renderutil.drawRoundedRect(renderX - 3, 3, renderX + mc.fontRendererObj.getStringWidth(p.getName()) + 5, 15,1, baseColor.getRGB());
            }
            mc.fontRendererObj.drawStringWithShadow(p.getName(), renderX, 5, -1);
            x += mc.fontRendererObj.getStringWidth(p.getName()) + 20;
        }

        if(this.currentPanel.isDefaultBackground()) {
            this.drawDefaultBackground();
        }

        this.currentPanel.draw(mouseX, mouseY, partialTicks);
        if(mc.currentScreen == this) {
            HUDMod.notificationManager.render2D();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        int x = 0;

        for(Panel p : this.panels) {
            int renderX = (new ScaledResolution(Wrapper.mc).getScaledWidth() / 2) + x - (panels.size() * mc.fontRendererObj.FONT_HEIGHT);
            if(isHovered(mouseX, mouseY, renderX - 2, 2, renderX + mc.fontRendererObj.getStringWidth(p.getName()), 18)) {
                this.currentPanel = p;
            }
            x += mc.fontRendererObj.getStringWidth(p.getName()) + mc.fontRendererObj.FONT_HEIGHT;
        }

        this.currentPanel.mouseClicked(mouseX, mouseY, mouseButton);
    }

  //  @Override
  //  protected void keyTyped(char typedChar, int keyCode) throws IOException {
  //      this.currentPanel.keyTyped(typedChar, keyCode);
  //  }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.currentPanel.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
    }

    public ArrayList<Panel> getPanels() {
        return panels;
    }

    public void setPanels(ArrayList<Panel> panels) {
        this.panels = panels;
    }

    public Panel getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(Panel currentPanel) {
        this.currentPanel = currentPanel;
    }

    public static PanelGUIScreen INSTANCE = new PanelGUIScreen();

}

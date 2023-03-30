package dev.px.hud.ClickGUI;

import dev.px.hud.ClickGUI.Render.HUDFrame;
import dev.px.hud.HUD.Element;
import dev.px.hud.HUDMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    private ArrayList<HUDFrame> f;

    public int mouseX_ = 0;
    public int mouseY_ = 0;

    public ClickGUI() {
        this.f = new ArrayList<>();
        int offset = 0;
        for(Element.Category c : Element.Category.values()) {
            this.f.add(new HUDFrame(offset, 20, c));
            offset += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.render(partialTicks);
            element.drag(mouseX, mouseY);
        });

        this.mouseX_ = mouseX;
        this.mouseY_ = mouseY;
        this.f.forEach(frame -> {
            frame.drawScreen(mouseX, mouseY);
        });
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.drag(mouseX, mouseY);
            if(!element.isHidden()) {
                element.mouseRelease(mouseX, mouseY, state);
            }
        });

        this.f.forEach(frame -> {
            frame.mouseRelease(mouseX, mouseY, state);
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.f.forEach(frame -> {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        });

        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.mouseClicked(mouseX, mouseY, mouseButton);
        });
    }

    @Override
    public void onGuiClosed() {
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<HUDFrame> hudFrames() { return this.f; }

    public static ClickGUI INSTANCE = new ClickGUI();
}

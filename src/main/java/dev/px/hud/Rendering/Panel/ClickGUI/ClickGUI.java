package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.Panel.Panel;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends Panel {

    public ClickGUI() {
        super("GUI");
        int offsetNormal = 200;
        for(Element.HUDType t : Element.HUDType.values()) {
            this.frames.add(new Frame(t, offsetNormal, 20));

            offsetNormal += 120;
        }
    }

    private ArrayList<Frame> frames = new ArrayList<>();

    @Override
    public void draw(int mouseX, int mouseY) {
        for(Frame f : this.frames) {
            f.draw(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        for(Frame f : this.frames) {
            f.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f : this.frames) {
            f.mouseReleased(mouseX, mouseY);
        }
    }
}

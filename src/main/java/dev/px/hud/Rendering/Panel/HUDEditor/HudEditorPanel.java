package dev.px.hud.Rendering.Panel.HUDEditor;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.Panel;

import java.io.IOException;

public class HudEditorPanel extends Panel {

    public HudEditorPanel() {
        super("HUD");
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).dragging(mouseX, mouseY);
                ((RenderElement) e).render(partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseRelease(mouseX, mouseY, state);
            }
        }
    }
}

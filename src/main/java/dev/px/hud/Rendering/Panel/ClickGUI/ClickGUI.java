package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.Panel;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends Panel {

    public ClickGUI() {
        super("GUI");
        int offsetNormal = 200;
        for(Element.HUDType t : Element.HUDType.values()) {
            this.frames.add(new Frame(t, offsetNormal, 20, this));
            offsetNormal += 120;
        }
    }

    private ArrayList<Frame> frames = new ArrayList<>();
    private Renderutil.ScissorStack scissorStack = new Renderutil.ScissorStack();

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Frame focusedFrame = this.frames
                .stream()
                .filter(frame -> frame.isHovered2(mouseX, mouseY))
                .findFirst()
                .orElse(null);

        if(focusedFrame != null) {
            if(Mouse.hasWheel()) {
                focusedFrame.handleScroll(Mouse.getDWheel());
            }
        }


        for(Frame f : this.frames) {
            f.draw(mouseX, mouseY, partialTicks);
        }
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).dragging(mouseX, mouseY);
                ((RenderElement) e).render(partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        for(Frame f : this.frames) {
            f.mouseClicked(mouseX, mouseY, button);
        }

        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseClicked(mouseX, mouseY, button);
            }
        }

        HUDMod.soundInitalizer.playSound("click");
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f : this.frames) {
            f.mouseReleased(mouseX, mouseY);
        }

        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseRelease(mouseX, mouseY, state);
            }
        }
    }

    public Renderutil.ScissorStack getScissorStack() {
        return scissorStack;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }
}

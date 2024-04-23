package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.Panel;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Render.Texture;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends Panel {

    public ClickGUI() {
        super("GUI");
        int offsetNormal = 15;
        for(Element.HUDType t : Element.HUDType.values()) {
            this.frames.add(new Frame(t, new ScaledResolution(mc).getScaledWidth() - 100, offsetNormal, this));
            offsetNormal += 50;
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
                int scroll = Mouse.getDWheel();
                focusedFrame.handleScroll(scroll);
            }
        }

        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                if(e.isToggled()) {
                    ((RenderElement) e).dragging(mouseX, mouseY);
                    ((RenderElement) e).render(partialTicks);
                    ((RenderElement) e).render2D(new Render2DEvent(partialTicks, new ScaledResolution(mc)));
                }
            }
        }

        for(Frame f : this.frames) {
            f.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        for(Frame f : this.frames) {
            f.mouseClicked(mouseX, mouseY, button);
        }

        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                if(e.isToggled()) {
                    ((RenderElement) e).mouseClicked(mouseX, mouseY, button);
                }
            }
        }

  //      HUDMod.soundInitalizer.playSound("click");
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_BACK) {
            mc.currentScreen = null;
        }

        for(Frame f : this.frames) {
            f.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f : this.frames) {
            f.mouseReleased(mouseX, mouseY);
        }

        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                if(e.isToggled()) {
                    ((RenderElement) e).mouseRelease(mouseX, mouseY, state);
                }
            }
        }
    }

    public Renderutil.ScissorStack getScissorStack() {
        return scissorStack;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public Frame getFramebyName(String name) {
        for(Frame f : frames) {
            if(f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }

    public static ClickGUI INSTANCE = new ClickGUI();
}

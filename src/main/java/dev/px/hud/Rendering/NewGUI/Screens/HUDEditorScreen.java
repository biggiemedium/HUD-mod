package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class HUDEditorScreen extends Screen {

    private int x, y, width, height;

    public HUDEditorScreen() {
        super("Edit HUD");
        setResourceLocation(new ResourceLocation("minecraft", "GUI/visual.png"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                if(e.isToggled()) {
                    ((RenderElement) e).dragging(mouseX, mouseY);
                    ((RenderElement) e).render(partialTicks);
                    ((RenderElement) e).render2D(new Render2DEvent(partialTicks, new ScaledResolution(Minecraft.getMinecraft())));
                }
            }
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int button) {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                ((RenderElement) e).mouseRelease(mouseX, mouseY, button);
            }
        }
    }
}

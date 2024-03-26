package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Renderutil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class ModuleScreen extends Screen {

    public int x, y, width, height;
    private Animation toggleModSlier = new Animation(250, false, Easing.LINEAR);
    private RenderList mode;
    private boolean searchBarActive;

    private enum RenderList {
        Render,
        Toggle
    }

    public ModuleScreen(int x, int y, int width, int height) {
        super("Mods");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mode = RenderList.Render; // default
        this.searchBarActive = false;
        this.setResourceLocation(new ResourceLocation("minecraft", "GUI/movement.png"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        GL11.glPushMatrix();
        // Mod sorter
        RoundedShader.drawRound(x + 5, y + 9, 75, 17, 4, new Color(38, 38, 38));
        RoundedShader.drawRound(x + 5 + (int) ((75 / 2) * toggleModSlier.getAnimationFactor()), y + 9, (75 / 2), 17, 4, new Color(255, 255, 255, 180));
        Fontutil.drawText("Render", (int) (x + 7), (int) (y + 5 + Fontutil.getHeight()), -1);
        Fontutil.drawText("Mods", (int) (x + 75 - (Fontutil.getWidth("Mods"))), (int) (y + 5 + Fontutil.getHeight()), -1);
        GL11.glPopMatrix();

        // Search bar
        

        // Buttons
        GL11.glPushMatrix();
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(mode == RenderList.Render) {
                if (e instanceof RenderElement) {

                }
            } else if(mode == RenderList.Toggle) {
                if(e instanceof ToggleableElement) {

                }
            }
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        super.onClick(mouseX, mouseY, button);

        if(button == 0) {
            if(isHovered(x + 5, y + 9, (40), 17, mouseX, mouseY)) {
                mode = RenderList.Render;
                toggleModSlier.setState(false);
            } else if(isHovered(x + 5 + (40), y + 9, (40), 17, mouseX, mouseY)) {
                mode = RenderList.Toggle;
                toggleModSlier.setState(true);
            }

        }
    }

    public boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Dimension;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Shader.Shaders.GradientShader;
import dev.px.hud.Util.Renderutil;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class ModuleScreen extends Screen {

    public int x, y, width, height;
    private Animation toggleModSlier = new Animation(250, false, Easing.LINEAR);
    private RenderList mode;
    private boolean searchBarActive;
    private Dimension<Integer> moduleScissor;
    private double scrollY = 0;

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
        this.moduleScissor = new Dimension<>(x, y + 9 + 17, width, height - (9 + 17));
        this.setResourceLocation(new ResourceLocation("minecraft", "GUI/movement.png"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        this.moduleScissor.update(x, y + 9 + 17, width, height - (9 + 17));
        int offsetY = 0;

        // Hover Handler
        if(isHovered(x, y + 9, width, height - 9, mouseX, mouseY)) {
            scrollY += Mouse.getDWheel() * 0.03;
        }

        if(scrollY > 0) {
            scrollY = 0;
        }

        GL11.glPushMatrix();
        // Mod sorter
        RoundedShader.drawRound(x + 10, y + 9, 75, 17, 4, new Color(38, 38, 38));
        RoundedShader.drawRound(x + 10 + (int) ((75 / 2) * toggleModSlier.getAnimationFactor()), y + 9, (75 / 2), 17, 4, new Color(
                HUDMod.colorManager.getAlternativeColor().getRed(),
                HUDMod.colorManager.getAlternativeColor().getGreen(),
                HUDMod.colorManager.getAlternativeColor().getBlue(), 160));
        //new Color(255, 255, 255, 180)

        Fontutil.drawText("Render", (int) (x + 10), (int) (y + 5 + Fontutil.getHeight()), -1);
        Fontutil.drawText("Mods", (int) (x + 80 - (Fontutil.getWidth("Mods"))), (int) (y + 5 + Fontutil.getHeight()), -1);
        GL11.glPopMatrix();

        // Search bar

        // Buttons
        GL11.glPushMatrix();

        int column = 0;
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e == null) continue;
            if(mode == RenderList.Render && e instanceof RenderElement) { continue; }
                    // Dimensions and scissors
                    Renderutil.ScissorStack stack = new Renderutil.ScissorStack();
                    this.moduleScissor = new Dimension<>(x, y + 9 + 17, width, height - (9 + 17));
                    Dimension<Integer> moduleButton = new Dimension<>(x + 10, (y + (int) scrollY + 26) + 5 + offsetY, 115, 20);
                    stack.pushScissor(moduleScissor.getX(), moduleScissor.getY(), moduleScissor.getWidth(), moduleScissor.getHeight());

                    RoundedShader.drawRoundOutline(x + 10 + (column * (moduleButton.getWidth() + 40)), moduleButton.getY(), moduleButton.getWidth(), moduleButton.getHeight(), 2, 0.1f, new Color(38, 38, 38),
                            new Color(HUDMod.colorManager.getAlternativeColor().getRed(),
                                    HUDMod.colorManager.getAlternativeColor().getGreen(),
                                    HUDMod.colorManager.getAlternativeColor().getBlue(), 100));
                    //RoundedShader.drawRound((x + 10) + (column * (moduleButton.getWidth() + 40)), moduleButton.getY(), moduleButton.getWidth(), moduleButton.getHeight(), 2, new Color(38, 38, 38));
                    Fontutil.drawText(e.getName(), (x + 12) + (column * (moduleButton.getWidth() + 40)), moduleButton.getY() + (int) moduleButton.getHeight() / 2, -1);

                    // Thank u chat GPT
                    column++;
                    if(column >= 2) {
                        column = 0;
                        offsetY += moduleButton.getHeight() + 7;
                    }

                    stack.popScissor();
        }
        GL11.glPopMatrix();

        if(scrollY < -(offsetY - 30)) {
            scrollY = -(offsetY - 30);
        }

        // Scrollbar
        GL11.glPushMatrix();
        int scrollbarWidth = 6;
        Dimension<Integer> scrollBar = new Dimension<>(x + (width - scrollbarWidth), (int) (y + 26 + (-scrollY)), scrollbarWidth, 28);
        
        RoundedShader.drawRoundOutline(scrollBar.getX(), scrollBar.getY(), scrollBar.getWidth(), scrollBar.getHeight(), 4, 0.1f, new Color(38, 38, 38), new Color(HUDMod.colorManager.getAlternativeColor().getRed(),
                HUDMod.colorManager.getAlternativeColor().getGreen(),
                HUDMod.colorManager.getAlternativeColor().getBlue(), 75));

        GL11.glPopMatrix();

    }

    @Override
    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        super.onClick(mouseX, mouseY, button);

        if(button == 0) {
            if(isHovered(x + 10, y + 9, (40), 17, mouseX, mouseY)) {
                mode = RenderList.Render;
                this.scrollY = 0;
                toggleModSlier.setState(false);
            } else if(isHovered(x + 10 + (40), y + 9, (40), 17, mouseX, mouseY)) {
                mode = RenderList.Toggle;
                this.scrollY = 0;
                toggleModSlier.setState(true);
            }

            for(Element e : HUDMod.elementInitalizer.getElements())  {
                if(e == null) continue;


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

package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.Util.API.Math.Dimension;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Renderutil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class MainContainer {

    public int x, y, width, height;
    private ArrayList<SubComponent> components = new ArrayList<>();
    private SubComponent currentScreen;

    public MainContainer(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(components == null) {
            components = new ArrayList<>();
        }
        handleArray();
        if(currentScreen == null) {
            this.currentScreen = components.get(0);
        }
    }

    private void handleArray() {
        this.components.add(new ModuleContainer(x, y, width / 4, height));
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        if(currentScreen == null) {
            this.currentScreen = components.get(0);
        }

        GL11.glPushMatrix();
        RoundedShader.drawRound(x, y, width, height, 2, new Color(0xff181A17));
        GL11.glPopMatrix();

    }

    public void mouseClick(int state, int mouseX, int mouseY) {

    }
}

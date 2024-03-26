package dev.px.hud.Rendering.NewGUI.Screens;

import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class Screen {

    private String name;
    private ResourceLocation resourceLocation;
    public int x, y, width, height;

    public Screen(String name) {
        this.name = name;
        this.resourceLocation = null;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Screen(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.resourceLocation = null;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {}

    public void onClick(int mouseX, int mouseY, int button) throws IOException {}

    public void onRelease(int mouseX, int mouseY, int button) {}

    public void onType(char typedChar, int keyCode) throws IOException {}

}

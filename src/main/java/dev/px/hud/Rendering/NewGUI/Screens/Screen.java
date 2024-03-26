package dev.px.hud.Rendering.NewGUI.Screens;

import java.io.IOException;

public class Screen {

    private String name;
    public int x, y, width, height;

    public Screen(String name) {
        this.name = name;
    }

    public Screen(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

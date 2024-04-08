package dev.px.hud.Rendering.NewGUI.Components;

import dev.px.hud.Util.Settings.Setting;

public class Component<T> {

    public int x, y, width, height;
    private Setting<T> setting;

    public Component(int x, int y, int width, int height, Setting<T> setting) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setting = setting;
    }

    public void update(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

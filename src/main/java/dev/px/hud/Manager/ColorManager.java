package dev.px.hud.Manager;

import dev.px.hud.Util.API.Util;

import java.awt.*;

public class ColorManager extends Util {

    private int r, g, b;
    private Color deafultColor = new Color(39, 179, 206);

    public ColorManager() {
        this.r = 39;
        this.g = 179;
        this.b = 206;
    }

    public void resetColor() {
        this.r = deafultColor.getRed();
        this.g = deafultColor.getGreen();
        this.b = deafultColor.getBlue();
    }

    public Color getCurrentColor() {
        return new Color(r, g, b);
    }

    public int getRed() {
        return r;
    }

    public void setRed(int r) {
        this.r = r;
    }

    public int getGreen() {
        return g;
    }

    public void setGreen(int g) {
        this.g = g;
    }

    public int getBlue() {
        return b;
    }

    public void setBlue(int b) {
        this.b = b;
    }

    public Color getDeafultColor() {
        return deafultColor;
    }

    public void setDeafultColor(Color deafultColor) {
        this.deafultColor = deafultColor;
    }
}

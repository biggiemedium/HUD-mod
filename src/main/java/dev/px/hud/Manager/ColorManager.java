package dev.px.hud.Manager;

import dev.px.hud.Util.API.Util;

import java.awt.*;

public class ColorManager extends Util {

    private Color mainColor = new Color(39, 179, 206);
    private Color alternativeColor = new Color(236, 133, 209);

    public ColorManager() {

    }

    public void resetColor() {
        this.mainColor = new Color(39, 179, 206);
        this.alternativeColor = new Color(236, 133, 209);
    }

    public Color getAlternativeColor() {
        return alternativeColor;
    }

    public void setAlternativeColor(Color alternativeColor) {
        this.alternativeColor = alternativeColor;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }
}

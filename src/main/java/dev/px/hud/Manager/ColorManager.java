package dev.px.hud.Manager;

import dev.px.hud.Util.API.Render.Color.AccentColor;
import dev.px.hud.Util.API.Util;

import java.awt.*;
import java.util.ArrayList;

public class ColorManager extends Util {

    private Color mainColor = new Color(39, 179, 206);
    private Color alternativeColor = new Color(236, 133, 209);

    private ArrayList<AccentColor> accentColors = new ArrayList<>();
    private AccentColor currentColor;

    public ColorManager() {

        this.accentColors.add(new AccentColor("Clear", new Color(255, 255, 255, 0), new Color(255, 255, 255, 0)));
        this.accentColors.add(new AccentColor("Default", new Color(39, 179, 206), new Color(236, 133, 209)));

        if(currentColor == null) {
            currentColor = accentColors.get(0);
        }
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

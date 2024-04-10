package dev.px.hud.Manager;

import dev.px.hud.Util.API.Render.Color.AccentColor;
import dev.px.hud.Util.API.Util;

import java.awt.*;
import java.util.ArrayList;

public class ColorManager extends Util {

    private Color mainColor = new Color(39, 179, 206);
    private Color alternativeColor = new Color(236, 133, 209);

    private ArrayList<AccentColor> accentColors = new ArrayList<>();
    public AccentColor currentColor;

    public ColorManager() {

        Add(new AccentColor("Default", new Color(39, 179, 206), new Color(236, 133, 209)));
        Add(new AccentColor("Deep Ocean", new Color(61, 79, 143), new Color(1, 19, 63)));
        Add(new AccentColor("Melon", new Color(173, 247, 115), new Color(128, 243, 147)));
        Add(new AccentColor("Neon Red", new Color(210, 39, 48), new Color(184, 27, 45)));
        Add(new AccentColor("Pink Blood", new Color(226, 0, 70), new Color(255, 166, 200)));
        Add(new AccentColor("Lemon", new Color(252, 248, 184), new Color(255, 243, 109)));
        Add(new AccentColor("Blaze Orange", new Color(254, 169, 76), new Color(253, 130, 0)));
        Add(new AccentColor("Sunset Pink", new Color(253, 145, 21), new Color(245, 106, 230)));

        if(currentColor == null) {
            currentColor = accentColors.get(0);
        }
        this.mainColor = currentColor.getMainColor();
        this.alternativeColor = currentColor.getAlternativeColor();
    }

    public void Add(AccentColor accentColor) {
        this.accentColors.add(accentColor);
    }

    public AccentColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(AccentColor currentColor) {
        this.currentColor = currentColor;
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

    public ArrayList<AccentColor> getAccentColors() {
        return accentColors;
    }

    public void setAccentColors(ArrayList<AccentColor> accentColors) {
        this.accentColors = accentColors;
    }
}

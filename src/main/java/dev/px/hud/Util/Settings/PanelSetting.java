package dev.px.hud.Util.Settings;

import java.util.function.Predicate;

public class PanelSetting<T> extends Setting<T> {


    public PanelSetting(String name, T value) {
        super(name, value);
    }

    public PanelSetting(String name, T value, T min, T max) {
        super(name, value, min, max);
    }

    public PanelSetting(String name, T value, Predicate<T> visibility) {
        super(name, value, visibility);
    }

    public PanelSetting(String name, T value, T min, T max, Predicate<T> visibility) {
        super(name, value, min, max, visibility);
    }


    public enum PrefernceMode {
        UI,
        GAME,
        PRIVACY
    }
}

package dev.px.hud.Util.Settings.ClientSettings;

import dev.px.hud.Util.Settings.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Preference<T> {

    private ArrayList<Setting<T>> settings;

    public Preference(ArrayList<Setting<T>> setting) {
        this.settings = setting;
    }


}

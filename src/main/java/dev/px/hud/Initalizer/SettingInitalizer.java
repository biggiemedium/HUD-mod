package dev.px.hud.Initalizer;

import dev.px.hud.Util.Settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingInitalizer {

    public ArrayList<Setting> settings;

    public SettingInitalizer() {
        this.settings = new ArrayList<>();
    }

    public Setting Build(Setting setting) {
        this.settings.add(setting);
        return setting;
    }

    public <T> Setting BuildValue(Setting<T> value) {
        this.settings.add(value);
        return value;
    }

    public ArrayList<Setting> getSettingsArrayList() {
        return settings;
    }

}

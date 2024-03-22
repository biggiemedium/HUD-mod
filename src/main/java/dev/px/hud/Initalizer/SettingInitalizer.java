package dev.px.hud.Initalizer;

import dev.px.hud.Util.Settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingInitalizer {

    private ArrayList<Setting> clientPreferences = new ArrayList<>();

    public Setting<Boolean> NCPCluster;

    public SettingInitalizer() {
        this.NCPCluster = create(new Setting<>("NCP Cluster", true));
    }

    public <T> Setting<T> create(Setting<T> hudSetting) {
        this.clientPreferences.add(hudSetting);
        return hudSetting;
    }
}

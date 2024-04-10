package dev.px.hud.Util.Settings.ClientSettings;

import dev.px.hud.Util.Settings.Setting;

import java.util.ArrayList;

public class PreferenceManager {

    private ArrayList<Setting> preferences;

    // Preferences static
    public Setting<Boolean> BACKGROUND;
    public Setting<Boolean> CUSTOMFONT;
    public Setting<Boolean> NCPCluster;
    public Setting<Boolean> ESPCluster;
    public Setting<Boolean> windowModifications;

    public PreferenceManager() {
        this.preferences = new ArrayList<>();

        BACKGROUND = Add(new Setting("Element Background", false));
        CUSTOMFONT = Add(new Setting("Custom Font", true));
        NCPCluster = Add(new Setting<>("NCP Cluster", true));
        ESPCluster = Add(new Setting<>("ESP Cluster", true));
        windowModifications = Add(new Setting<>("Window Modifications", true));
    }

    public <T> Setting Add(Setting preference) {
        this.preferences.add(preference);
        return preference;
    }

    public ArrayList<Setting> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Setting> preferences) {
        this.preferences = preferences;
    }
}

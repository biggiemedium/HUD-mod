package dev.px.hud.Initalizer;

import dev.px.hud.Util.Config.Config;
import dev.px.hud.Util.Config.Configs.ClientPreferences;
import dev.px.hud.Util.Config.Configs.FramePositions;

import java.util.ArrayList;

public class ConfigInitalizer {

    private ArrayList<Config> configs = new ArrayList<>();

    public ConfigInitalizer() {
        Add(new FramePositions());
        Add(new ClientPreferences());
    }

    private void Add(Config config) {
        this.configs.add(config);
    }

    public void loads() {
        this.configs.forEach(load -> {
            load.loads();
        });
    }

    public void saves() {
        this.configs.forEach(save -> {
            save.saves();
        });
    }
}

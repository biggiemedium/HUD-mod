package dev.px.hud.Initalizer;

import dev.px.hud.Util.Config.Config;
import dev.px.hud.Util.Config.Configs.ClientPreferences;
import dev.px.hud.Util.Config.Configs.FramePositions;
import dev.px.hud.Util.Config.Configs.RenderElementConfig;

import java.util.ArrayList;

public class ConfigInitalizer {

    private ArrayList<Config> configs = new ArrayList<>();

    public ConfigInitalizer() {
        Add(new FramePositions());
        Add(new ClientPreferences());
        Add(new RenderElementConfig());
    }

    private void Add(Config config) {
        this.configs.add(config);
    }

    public void loadAll() {
        this.configs.forEach(load -> {
            load.loads();
        });
    }

    public void savesAll() {
        for(Config c : this.configs) {
            c.saves();
        }
    }
}

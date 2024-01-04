package dev.px.hud.Util.Event;

import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SettingUpdateEvent extends Event {

    private Setting<?> setting;

    public SettingUpdateEvent(Setting<?> setting) {
        this.setting = setting;
    }

    public Setting<?> getSetting() {
        return setting;
    }
}

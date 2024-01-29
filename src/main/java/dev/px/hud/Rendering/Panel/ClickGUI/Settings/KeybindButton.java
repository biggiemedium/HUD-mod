package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Keybind;
import dev.px.hud.Util.Settings.Setting;

public class KeybindButton extends SettingButton<Keybind> {

    public KeybindButton(Button button, double x, double y, Setting<Keybind> setting) {
        super(button, x, y, setting);
    }

    private int key;
    private boolean listening = false;

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
    }

}

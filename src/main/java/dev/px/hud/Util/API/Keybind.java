package dev.px.hud.Util.API;

import dev.px.hud.Rendering.HUD.Element;

public class Keybind {

    private int keybind;

    public Keybind() {
        this.keybind = -1;
    }

    public Keybind(int keybind) {
        this.keybind = keybind;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }
}

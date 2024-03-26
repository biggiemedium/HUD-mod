package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.Rendering.Panel.Settings.ClientSettings;

public class ClientSettingsScreen extends Screen {

    public int x, y, width, height;

    public ClientSettingsScreen(int x, int y, int width, int height) {
        super("Settings");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}

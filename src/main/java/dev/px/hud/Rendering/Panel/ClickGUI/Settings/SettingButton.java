package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class SettingButton<T> {

    private double x;
    private double y;
    private double width;
    private double height;
    private Setting<T> setting;
    private Button button;
    protected Minecraft mc = Minecraft.getMinecraft();

    public SettingButton(Button button, double x, double y, Setting<T> setting) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.setting = setting;
        this.width = button.getWidth();
        this.height = 15; // Module button height
    }

    public SettingButton(Button button, double x, double y, double width, double height, Setting<T> setting) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.setting = setting;
        this.width = width;
        this.height = height;
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {

    }

    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void scroll(int in) {

    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void scroll(double in) {}

    public void setWidth(double width) {
        this.width = width;
    }

    public Setting<T> getSetting() {
        return setting;
    }

    public void setSetting(Setting<T> setting) {
        this.setting = setting;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}

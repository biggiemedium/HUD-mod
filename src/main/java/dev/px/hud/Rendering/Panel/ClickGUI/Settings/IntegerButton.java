package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.Settings.Setting;

import java.io.IOException;
import java.text.DecimalFormat;

public class IntegerButton extends SettingButton<Integer> {

    private Setting<Integer> setting;
    private boolean dragging;

    public IntegerButton(Button button, double x, double y, Setting<Integer> setting) {
        super(button, x, y, button.getWidth(), 20, setting);
        this.setting = setting;
        this.dragging = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

}

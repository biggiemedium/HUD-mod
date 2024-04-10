package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Input.Keybind;
import dev.px.hud.Util.Settings.Setting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class KeybindButton extends SettingButton<Keybind> {

    private int key;
    private Setting<Keybind> setting;
    private boolean listening = false;
    private double featureHeight;

    public KeybindButton(Button button, double x, double y, Setting<Keybind> setting) {
        super(button, x, y, setting);
        this.setting = setting;
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        GL11.glScaled(0.55, 0.55, 0.55); {
            float scaledX = ((float) getX() + 6) * 1.81818181F;
            float scaledY = ((float) featureHeight + 4) * 1.81818181F;
            String v = setting.getValue().getKeybind() == -1 ? "None" : Keyboard.getKeyName(setting.getValue().getKeybind());
            Fontutil.drawTextShadow("Keybind: " + v, scaledX, scaledY, -1);
        }

        GL11.glScaled(1.81818181, 1.81818181, 1.81818181);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isMouseOver(getX(), getY(), getWidth(), getHeight(), mouseX, mouseY)) {
            if(button == 0) {
                this.listening = !this.listening;
            }
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.listening = false;
    }

    public void keyTyped(char key, int keyCode) {
        if(listening) {
            if(keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_BACK) { // Escape key
                listening = false;
                this.setting.getValue().setKeybind(-1);
                return;
            } else {
                listening = false;
                this.setting.getValue().setKeybind(keyCode);
                return;
            }

        }
    }

    boolean isMouseOver(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }
}

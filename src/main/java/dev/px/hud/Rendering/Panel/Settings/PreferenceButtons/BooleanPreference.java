package dev.px.hud.Rendering.Panel.Settings.PreferenceButtons;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;

import java.awt.*;
import java.io.IOException;

public class BooleanPreference extends PreferenceButton<Boolean> {

    private Setting<Boolean> setting;
    private Animation toggleAnimation;
    private Animation hoverAnimation;

    public BooleanPreference(int x, int y, int width, int height, Setting<Boolean> setting) {
        super(x, y, width, height, setting);
        this.setting = setting;
        toggleAnimation = new Animation(300, setting.getValue(), Easing.LINEAR);
        hoverAnimation = new Animation(100, false, Easing.LINEAR);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        toggleAnimation.setState(setting.getValue());
        hoverAnimation.setState(isMouseOver(getX(), getY(), getWidth(), getHeight(), mouseX, mouseY));

        if(hoverAnimation.getAnimationFactor() > 0) {
            RoundedShader.drawRound(getX(), getY(), getWidth() * (int) hoverAnimation.getAnimationFactor(), getHeight() * (int) hoverAnimation.getAnimationFactor(), 2, new Color(40, 40, 40, 50));
        }

        RoundedShader.drawRound(getX(), getY(), getWidth(), getHeight(), 2, new Color(38, 38, 38));
        Fontutil.drawTextShadow(setting.getName(), getX() + 4, (getY() + getHeight() / 2) - 2, -1);
        RoundedShader.drawRound(getX() + 2, getY() + getHeight() - 0.5f, getWidth() - 4, 0.5f, 4, new Color(255, 255, 255));

        RoundedShader.drawRound(getX() + getWidth() - 27, getY() + 2, 22, 10, 4, !toggleAnimation.getState() ? new Color(26, 26, 26) : HUDMod.colorManager.getMainColor());
        RoundedShader.drawRound(getX() + getWidth() - 25 + (12f * (float) toggleAnimation.getAnimationFactor()), getY() + 3f, 8, 8, 4, new Color(255, 255, 255));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(button == 0) {
            if(isMouseOver(getX(), getY(), getWidth(), getHeight(), mouseX, mouseY)) {
                this.setting.setValue(!this.setting.getValue());
            }
        }
    }

    boolean isMouseOver(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }
}

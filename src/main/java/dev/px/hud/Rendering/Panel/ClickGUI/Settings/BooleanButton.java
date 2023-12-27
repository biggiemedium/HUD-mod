package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.Settings.Setting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class BooleanButton extends SettingButton<Boolean> {

    private Setting<Boolean> setting;
    private boolean toggled;
    private Animation toggleAnimation = new Animation(300, false, Easing.EXPO_OUT);

    public BooleanButton(Button button, double x, double y, Setting<Boolean> setting) {
        super(button, x, y, button.getWidth(), 10, setting);
        this.setting = setting;
        this.toggled = setting.getValue();
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        double featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        this.toggleAnimation.setState(setting.getValue());

       mc.fontRendererObj.drawStringWithShadow(setting.getName(), (float) getButton().getX(), (int) featureHeight, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX >= this.getX() && mouseY >= this.getY() && mouseX <= this.getX() + this.getWidth() && mouseY <= this.getY() + this.getHeight()) {
            if(button == 0) {

            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

     boolean isMouseOver(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= this.getX() && mouseY >= this.getY() && mouseX <= this.getX() + this.getWidth() && mouseY <= this.getY() + this.getHeight();
    }
}

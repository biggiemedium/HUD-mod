package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.Event.Client.SettingUpdateEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class BooleanButton extends SettingButton<Boolean> {

    private Setting<Boolean> setting;
    private boolean toggled;
    private Animation toggleAnimation = new Animation(300, false, Easing.EXPO_OUT);
    private double featureHeight;

    public BooleanButton(Button button, double x, double y, Setting<Boolean> setting) {
        super(button, x, y, button.getWidth(), 15  , setting);
        this.setting = setting;
        this.toggled = setting.getValue();
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        this.toggleAnimation.setState(setting.getValue());
        Color toggleColor = this.setting.getValue() ? new Color(39, 179, 206) : new Color(0x181A18);

        Renderutil.drawRect((float) getX(), (float)  featureHeight, (float) getWidth(), (float)  getHeight(), new Color(0x181A18).darker());

        Renderutil.drawRoundedRect(getX() + getWidth() - 12, featureHeight + 2, 8, 8, 2,  new Color(22, 22, 17));
        if(toggleAnimation.getState()) {
            Renderutil.drawRoundedRect(getX() + getWidth() - 7 - (5 * toggleAnimation.getAnimationFactor()), featureHeight + 7 - (5 * toggleAnimation.getAnimationFactor()), 8 * toggleAnimation.getAnimationFactor(), 8 * toggleAnimation.getAnimationFactor(), 2, new Color(39, 179, 206));
        }

        // setting name
        GL11.glScaled(0.55, 0.55, 0.55); {
            float scaledX = ((float) getX() + 6) * 1.81818181F;
            float scaledY = ((float) featureHeight + 4) * 1.81818181F;
            Fontutil.drawTextShadow(getSetting().getName(), scaledX, scaledY, -1);
        }

        GL11.glScaled(1.81818181, 1.81818181, 1.81818181);

     //   Renderutil.drawRect(getX() + 1, featureHeight, 4, getHeight(), new Color(39, 179, 206, 255).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isMouseOver((float) getX(), (float) featureHeight, (float) getWidth(), (float) getHeight(), mouseX, mouseY)) {
            if(button == 0) {
                this.setting.setValue(!setting.getValue());
                this.toggleAnimation.setState(setting.getValue());
            } else if(button == 1) {

            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {

    }

    @SubscribeEvent
    public void settingUpdate(SettingUpdateEvent event) {
        if(event.getSetting() == this.setting) {
            toggleAnimation.setState(this.setting.getValue());
        }

    }

    boolean isMouseOver(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }
}

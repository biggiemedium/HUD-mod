package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

/*
Taken from cosmos client
 */
public class IntegerButton extends SettingButton<Number> {

    private Setting<Number> setting;
    private boolean dragging;
    private double featureHeight;

    private boolean leftHeld;

    public IntegerButton(Button button, double x, double y, Setting<Number> setting) {
        super(button, x, y, button.getWidth(), 20, setting);
        this.setting = setting;
        this.dragging = false;
        this.leftHeld = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        Renderutil.drawRect((float) getX(), (float)  featureHeight, (float) getWidth(), (float)  getHeight(), new Color(0x181A18).darker());

        // setting name and value
        GL11.glScaled(0.55, 0.55, 0.55); {
            float scaledX = ((float) getX() + 6) * 1.81818181F;
            float x2 = (float) (getX() + (getWidth() - Fontutil.getWidth(String.valueOf(getSetting().getValue())) - 2)) * 1.81818181F;
            float scaledY = ((float) featureHeight + 1) * 1.81818181F;
            Fontutil.drawTextShadow(getSetting().getName(), scaledX, scaledY, -1);
            Fontutil.drawTextShadow(String.valueOf(getSetting().getValue()), x2, scaledY, -1);
        }
        GL11.glScaled(1.81818181, 1.81818181, 1.81818181);

        double highestPoint = featureHeight;
        double lowestPoint = highestPoint + getHeight();

        // check if it's able to be interacted with
        if (highestPoint >= getButton().getParent().getY() + getButton().getParent().getHeight() + 2 && lowestPoint <= getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getParent().getFullheight() + 2) {
            if (leftHeld) {
                if (isMouseOver(getX(), featureHeight + 13, getWidth(), getHeight() - 13, mouseX, mouseY)) {

                    float percentFilled = ((mouseX - (float) getX()) * 130 / (((float) getX() + ( (float) getWidth() - 6)) - (float) getX()));

                    Number max = getSetting().getMax();
                    Number min = getSetting().getMin();

                    if (getSetting().getValue() instanceof Double) {
                        double valueSlid = MathHelper.clamp_double(Mathutil.round(percentFilled * ((max.doubleValue() - min.doubleValue()) / 130.0D) + min.doubleValue(), 2), min.doubleValue(), max.doubleValue());

                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        } else {
                            getSetting().setValue(valueSlid);
                        }

                    } else if (getSetting().getValue() instanceof Float) {
                        float valueSlid = MathHelper.clamp_float(Mathutil.round(percentFilled * (float) ((max.floatValue() - min.floatValue()) / 130.0D) + min.floatValue(), 2), min.floatValue(), max.floatValue());
                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        } else {
                            getSetting().setValue(valueSlid);
                        }

                    } else if(getSetting().getValue() instanceof Integer) {
                        int valueSlid = MathHelper.clamp_int((int) Mathutil.round(percentFilled * (float) ((max.intValue() - min.intValue()) / 130.0) + min.intValue(), 0), min.intValue(), max.intValue());

                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -0)); // rounding scale 0
                        } else {
                            getSetting().setValue(valueSlid);
                        }
                    }

                }

                else if (isMouseOver(getX(), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMin());
                }

                else if (isMouseOver(getX() + (getWidth() - 5), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMax());
                }
            }
        }

        float sliderWidth = 85 * (getSetting().getValue().floatValue() - getSetting().getMin().floatValue()) / (getSetting().getMax().floatValue() - getSetting().getMin().floatValue());
        if (sliderWidth < 2) {
            sliderWidth = 2;
        }
        if (sliderWidth > 85) {
            sliderWidth = 85;
        }

        Renderutil.drawRoundedRect(getButton().getParent().getX() + 6, featureHeight + 14, getButton().getParent().getWidth() - 10, 3, 2, new Color(23, 23, 29, 255));
        if (getSetting().getValue().doubleValue() > getSetting().getMin().doubleValue()) {
            Renderutil.drawRoundedRect(getButton().getParent().getX() + 6, featureHeight + 14, sliderWidth, 3, 2, HUDMod.colorManager.getMainColor());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(button == 0) {
            this.leftHeld = true;
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
        this.leftHeld = false;
    }

    boolean isMouseOver(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }

}

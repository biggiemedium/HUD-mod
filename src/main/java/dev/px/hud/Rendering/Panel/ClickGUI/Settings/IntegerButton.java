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

        // module feature bounds
        double highestPoint = featureHeight;
        double lowestPoint = highestPoint + getHeight();

        // check if it's able to be interacted with
        if (highestPoint >= getButton().getParent().getY() + getButton().getParent().getHeight() + 2 && lowestPoint <= getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getParent().getFullheight() + 2) {
            if (leftHeld) {
                if (this.isMouseOver(getButton().getParent().getX(), featureHeight + 13, getButton().getParent().getWidth(), getHeight() - 13, mouseX, mouseY)) {
                    // the percentage of the slider that is filled
                    float percentFilled = ((mouseX - getButton().getParent().getX()) * 130 / ((getButton().getParent().getX() + (getButton().getParent().getWidth() - 6)) - getButton().getParent().getX()));

                    Number max = getSetting().getMax();
                    Number min = getSetting().getMin();

                    // set the value based on the type
                    if (getSetting().getValue() instanceof Double) {
                        double valueSlid = MathHelper.clamp_double(Mathutil.round(percentFilled * ((max.doubleValue() - min.doubleValue()) / 130.0D) + min.doubleValue(), 2), min.doubleValue(), max.doubleValue());

                        // exclude number
                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        }

                        else {
                            getSetting().setValue(valueSlid);
                        }
                    }

                    else if (getSetting().getValue() instanceof Float) {
                        float valueSlid = MathHelper.clamp_float(Mathutil.round(percentFilled * (float) ((max.floatValue() - min.floatValue()) / 130.0D) + min.floatValue(), 2), min.floatValue(), max.floatValue());

                        // exclude number
                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        }

                        else {
                            getSetting().setValue(valueSlid);
                        }
                    }
                }

                // if less than min, setting is min
                else if (isMouseOver(getButton().getParent().getX(), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMin());
                }

                // if greater than max, setting is max
                else if (isMouseOver(getButton().getParent().getX() + (getButton().getParent().getWidth() - 5), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMax());
                }
            }
        }

        // slider length
        float sliderWidth = 91 * (getSetting().getValue().floatValue() - getSetting().getMin().floatValue()) / (getSetting().getMax().floatValue() - getSetting().getMin().floatValue());

        // clamp
        if (sliderWidth < 2) {
            sliderWidth = 2;
        }

        if (sliderWidth > 91) {
            sliderWidth = 91;
        }

        // slider
        Renderutil.drawRoundedRect(getButton().getParent().getX() + 6, featureHeight + 14, getButton().getParent().getWidth() - 10, 3, 2, new Color(23, 23, 29, 255));
        if (getSetting().getValue().doubleValue() > getSetting().getMin().doubleValue()) {
            Renderutil.drawRoundedRect(getButton().getParent().getX() + 6, featureHeight + 14, sliderWidth, 3, 2, HUDMod.colorManager.getCurrentColor());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(button == 0) {
            this.leftHeld = true;
        }

        Util.sendClientSideMessage("L click " + leftHeld + " Val " + getSetting().getValue().doubleValue());


        // module feature bounds
        double highestPoint = featureHeight;
        double lowestPoint = highestPoint + getHeight();

        // check if it's able to be interacted with
        if (highestPoint >= getButton().getParent().getY() + getButton().getParent().getHeight() + 2 && lowestPoint <= getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getParent().getFullheight() + 2) {
            if (button == 0) {
                if (this.isMouseOver(getButton().getParent().getX(), featureHeight + 13, getButton().getParent().getWidth(), getHeight() - 13, mouseX, mouseY)) {
                    // the percentage of the slider that is filled
                    float percentFilled = ((mouseX - getButton().getParent().getX()) * 130 / ((getButton().getParent().getX() + (getButton().getParent().getWidth() - 6)) - getButton().getParent().getX()));

                    Number max = getSetting().getMax();
                    Number min = getSetting().getMin();

                    // set the value based on the type
                    if (getSetting().getValue() instanceof Double) {
                        double valueSlid = MathHelper.clamp_double(Mathutil.round(percentFilled * ((max.doubleValue() - min.doubleValue()) / 130.0D) + min.doubleValue(), 2), min.doubleValue(), max.doubleValue());

                        // exclude number
                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        }

                        else {
                            getSetting().setValue(valueSlid);
                        }
                    }

                    else if (getSetting().getValue() instanceof Float) {
                        float valueSlid = MathHelper.clamp_float(Mathutil.round(percentFilled * (float) ((max.floatValue() - min.floatValue()) / 130.0D) + min.floatValue(), 2), min.floatValue(), max.floatValue());

                        if (getSetting().isExclusion(valueSlid)) {
                            getSetting().setValue(valueSlid + Math.pow(1, -2));
                        } else {
                            getSetting().setValue(valueSlid);
                        }
                    }
                }

                else if (isMouseOver(getButton().getParent().getX(), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMin());
                }

                else if (isMouseOver(getButton().getParent().getX() + (getButton().getParent().getWidth() - 5), featureHeight + 13, 5, getHeight() - 13, mouseX, mouseY)) {
                    getSetting().setValue(getSetting().getMax());
                }
            }
        }

        // slider length
        float sliderWidth = 91 * (getSetting().getValue().floatValue() - getSetting().getMin().floatValue()) / (getSetting().getMax().floatValue() - getSetting().getMin().floatValue());

        // clamp
        if (sliderWidth < 2) {
            sliderWidth = 2;
        }

        if (sliderWidth > 91) {
            sliderWidth = 91;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
        this.leftHeld = false;
        Util.sendClientSideMessage("L click" + leftHeld);
    }

    boolean isMouseOver(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }

}

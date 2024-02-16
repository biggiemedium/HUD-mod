package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EnumButton extends SettingButton<Enum> {

    private Setting<Enum> setting;
    private boolean expanded = false; // why the hell does it start closed while expanded ??
    private double featureHeight;
    private Animation expandAnimation = new Animation(200, false, Easing.LINEAR);

    public EnumButton(Button button, double x, double y, Setting<Enum> setting) {
        super(button, x, y, button.getWidth(), 16, setting);
        this.setting = setting;
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        float expandHeight = 0 + (float) getHeight();
        this.expandAnimation.setState(expanded);

        Renderutil.drawRect((float) getX(), (float)  featureHeight, (float) getWidth(), (float) getHeight() + (float) ((expandHeight + getHeight()) * expandAnimation.getAnimationFactor()), new Color(0x181A18).darker());
      //  Renderutil.drawRect(getX(), featureHeight, 2, getHeight() + expandHeight, new Color(39, 179, 206).getRGB());

        GL11.glScaled(0.55, 0.55, 0.55); {
            float scaledX = ((float) getX() + 7) * 1.81818181F;
            float scaledY = ((float) featureHeight + 5) * 1.81818181F;
            float scaledWidth = (float) (getX() + getWidth() - (Fontutil.getWidth(getSetting().getName()) * 0.55F) - 6) * 1.81818181F;
            float scaledWidth2 = (float) (getX() + getWidth() - (Fontutil.getWidth(expanded ? "v" : "<") * 0.55F) - 2) * 1.81818181F;

            Fontutil.drawTextShadow(getSetting().getName(), scaledX, scaledY, -1);
            Fontutil.drawTextShadow(getSetting().getValue().name(), scaledWidth, scaledY, -1);
            Fontutil.drawTextShadow(expanded ? "v" : "<", scaledWidth2, scaledY, -1);
        }

        GL11.glScaled(1.81818181, 1.81818181, 1.81818181);

        if(expanded) {
          //  getButton().getParent().getClickGUI().getScissorStack().pushScissor((int) getX(), (int)  featureHeight, (int)  getWidth(), (int) (expandHeight * expandAnimation.getAnimationFactor()));
            List<Enum> enums = Arrays.asList(getValues().getEnumConstants());

                for(Enum e : enums) {
                GL11.glScaled(0.55, 0.55, 0.55); {
                    float scaledX = ((float) getX() + 4) * 1.81818181F;
                    float scaledY = ((float) featureHeight + expandHeight + 5) * 1.81818181F;
                    float scaledWidth = (float) (getX() + (getWidth() / 2) - (Fontutil.getWidth(getSetting().getName()) * 0.55F)) * 1.81818181F;

                    Fontutil.drawTextShadow(e.name(), scaledWidth, scaledY, -1);
                    if(getSetting().getValue() == e) {
                        Fontutil.drawTextShadow(">", scaledX, scaledY, -1);
                    }
                }

                GL11.glScaled(1.81818181, 1.81818181, 1.81818181);

                expandHeight += getHeight();
            }
                expandHeight -= getHeight();
      //      getButton().getParent().getClickGUI().getScissorStack().popScissor();
        }

        getButton().addSettingOffset(expandHeight * expandAnimation.getAnimationFactor());
        getButton().getParent().addFeatureOffset(expandHeight * expandAnimation.getAnimationFactor());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isMouseOver((float) getX(), (float) featureHeight, (float) getWidth(), (float) getHeight(), mouseX, mouseY)) {
            List<Enum> values = Arrays.asList(getValues().getEnumConstants());
            int in = values.indexOf(setting.getValue());
            if(button == 0) {
                if(!expanded) {
                    setting.setValue(in + 1 < values.size() ? values.get(in + 1) : values.get(0));
                }
            } else if(button == 1) {
                this.expanded = !this.expanded;
                this.expandAnimation.setState(expanded);
              //  Util.sendClientSideMessage("Value: " + setting.getValue(), true);
              //  Util.sendClientSideMessage("animation: " + expandAnimation.getState() + " factor " + expandAnimation.getAnimationFactor(), false);
            }
        }

        if(expanded) {
            float expandHeight = (float) featureHeight + (float) getHeight();
            List<Enum> enums = Arrays.asList(getValues().getEnumConstants());

            for(Enum e : enums) {

                if(isMouseOver((float) getX(), (float) (expandHeight), (float) getWidth(), (float) getHeight(), mouseX, mouseY)) {
                    if(button == 0) {
                        getSetting().setValue(e);
                    }
                }

                // put above code at top of loop??
                expandHeight += getHeight();
            }
            expandHeight -= getHeight();
        }
    }

    private Class<Enum> getValues() {
        return setting.getValue().getDeclaringClass();
    }

    boolean isMouseOver(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }
}

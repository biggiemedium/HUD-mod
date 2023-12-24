package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.Panel.ClickGUI.Settings.BooleanButton;
import dev.px.hud.Rendering.Panel.ClickGUI.Settings.SettingButton;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class Button {

    private String name;
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean open;
    private Element element;
    private Frame parent;
    private Minecraft mc = Minecraft.getMinecraft();

    private Animation toggleAnimation = new Animation(400, false, Easing.LINEAR);
    private Animation openAnimation = new Animation(400, false, Easing.LINEAR);
    private double featureHeight;
    private double settingOffset;
    private ArrayList<SettingButton<?>> settingButtons;

    public Button(int x, int y, Frame parent, Element element) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.getWidth();
        this.height = 15; // why was this 2 smaller than the frame?
        this.element = element;
        this.name = element.getName();
        this.open = false;
        this.settingButtons = new ArrayList<>();
        this.element.getSettings().forEach(setting -> {
            if(setting.getValue() instanceof Boolean) {
                settingButtons.add(new BooleanButton(this, getX(), getY(), (Setting<Boolean>) setting));
            }
        });
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = (this.parent.getY() + this.parent.getHeight() + this.parent.getComponentOffset() + this.parent.getScroll() + 2);
        this.parent.addFeatureOffset(height);
        this.toggleAnimation.setState(this.element.isVisible());
        this.openAnimation.setState(this.isOpen());

        if(openAnimation.getAnimationFactor() > 0) {
            this.settingOffset = getParent().getComponentOffset();
            this.settingButtons.forEach(settingButton -> {
                if(settingButton.getSetting().isVisible()) {
                    settingButton.draw(mouseX, mouseY, partialTicks);
                    this.settingOffset += settingButton.getHeight();
                    this.parent.addComponentOffset(settingButton.getHeight() * openAnimation.getAnimationFactor());
                }
            });
        }

        Renderutil.rect(x, y, width, height, true, new Color(0x181A18));
        Renderutil.drawGradientRect(x, featureHeight, x + (width * toggleAnimation.getAnimationFactor()), y + height, new Color(0x181A18).brighter().brighter().getRGB(), new Color(0x181A18).getRGB());
        mc.fontRendererObj.drawStringWithShadow(this.element.getName(), (float) this.x + 2, (int) featureHeight + (3), -1);
        mc.fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", ((float) this.x) + (float) (width - mc.fontRendererObj.getStringWidth(this.open ? "-" : "+")) - 3, (int) featureHeight + 3, -1);

    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                element.setVisible(!element.isVisible());
                this.toggleAnimation.setState(element.isVisible());
            } else if(button == 1) {
                this.open = !this.open;
                this.openAnimation.setState(open);
            }
            Util.sendClientSideMessage("Element: " + element.getName() + " Visible " + element.isVisible());
        }
    }

    public void scroll(float in) {
        if(open) {
            settingButtons.forEach(settingButton -> {
                settingButton.scroll(in);
            });
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Frame getParent() {
        return parent;
    }

    public void setParent(Frame parent) {
        this.parent = parent;
    }

    public double getFeatureHeight() {
        return featureHeight;
    }

    public double getSettingOffset() {
        return settingOffset;
    }

    public ArrayList<SettingButton<?>> getSettingButtons() {
        return settingButtons;
    }
}

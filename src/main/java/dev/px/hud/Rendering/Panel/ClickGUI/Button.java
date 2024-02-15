package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Rendering.Panel.ClickGUI.Settings.*;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Keybind;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
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
    private Animation openAnimation = new Animation(200, false, Easing.LINEAR);
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
        this.name = element == null ? "" : element.getName(); // WHY IS IT NULLLLLL
        this.open = false;
        if(this.element != null) {
        this.settingButtons = new ArrayList<>();
            if (this.element.getSettings() != null) {
                this.element.getSettings().forEach(setting -> {
                    if (setting.getValue() instanceof Boolean) {
                        settingButtons.add(new BooleanButton(this, getX(), getY(), (Setting<Boolean>) setting));
                    }
                    if (setting.getValue() instanceof Enum) {
                        settingButtons.add(new EnumButton(this, getX(), getY(), (Setting<Enum>) setting));
                    }
                    if (setting.getValue() instanceof Float) {
                    }
                    if (setting.getValue() instanceof Integer) {
                        settingButtons.add(new IntegerButton(this, getX(), getY(), (Setting<Number>) setting));
                    }
                    if (setting.getValue() instanceof Double) {
                    }

                   // if(this.element instanceof ToggleableElement) {
                   //     this.settingButtons.add(new KeybindButton(this, getX(), getY(), (Setting<Keybind>) setting));
                   // }

                });
            }
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = (this.parent.getY() + this.parent.getHeight() + this.parent.getComponentOffset() + this.parent.getScroll() + 2);
        this.parent.addFeatureOffset(height);

        if(this.element != null) {
            this.toggleAnimation.setState(this.element.isToggled());
            this.openAnimation.setState(this.isOpen());

            if (openAnimation.getAnimationFactor() > 0) {
                this.settingOffset = getParent().getComponentOffset();
                this.settingButtons.forEach(settingButton -> {
                    if (settingButton.getX() != this.getX()) {
                        settingButton.setX(getX());
                    }
                    if (settingButton.getSetting().isVisible()) {
                        settingButton.draw(mouseX, mouseY, partialTicks);
                        this.settingOffset += settingButton.getHeight();
                        this.parent.addComponentOffset(settingButton.getHeight() * openAnimation.getAnimationFactor());
                    }
                });
            }
        }

            Renderutil.rect(x, featureHeight, width, height, true, new Color(0x181A18));
        if(element != null) {
            Renderutil.drawGradientRect(x, featureHeight, x + (width * toggleAnimation.getAnimationFactor()), featureHeight + height, new Color(0x181A18).brighter().brighter().getRGB(), new Color(0x181A18).getRGB());
        }
            //mc.fontRendererObj.drawStringWithShadow(this.element.getName(), (float) this.x + 2, (int) featureHeight + (3), -1);
            //mc.fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", ((float) this.x) + (float) (width - mc.fontRendererObj.getStringWidth(this.open ? "-" : "+")) - 3, (int) featureHeight + 3, -1);

        if(element != null) {
            GL11.glScaled(0.8, 0.8, 0.8);
            {

                // scaled position
                float scaledX = ((float) getX() + 4) * 1.25F;
                float scaledY = ((float) featureHeight + 4.5F) * 1.25F;
                float scaledWidth = ((float) (getX() + getWidth()) - (Fontutil.getWidth("...") * 0.8F) - 3) * 1.25F;

                Fontutil.drawTextShadow(getElement().getName(), scaledX, scaledY, Color.WHITE.getRGB());
                if (this.settingButtons.size() > 2) { // Text element
                    Fontutil.drawTextShadow("...", scaledWidth, scaledY - 3, new Color(255, 255, 255).getRGB());
                }
            }

            GL11.glScaled(1.25, 1.25, 1.25);
        }

    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        if(this.element != null) {
            if (isHovered(mouseX, mouseY)) {
                if (button == 0) {
                    element.setToggled(!element.isToggled());
                    this.toggleAnimation.setState(element.isToggled());
                    if (element instanceof ToggleableElement) {
                        ((ToggleableElement) element).setEnabled(((ToggleableElement) element).isToggled());
                    }
                } else if (button == 1) {
                    this.open = !this.open;
                    this.openAnimation.setState(open);
                }
                // Util.sendClientSideMessage("Element: " + element.getName() + " Visible " + element.isVisible());
            }

            if (open) {
                for (SettingButton<?> s : this.settingButtons) {
                    try {
                        s.mouseClicked(mouseX, mouseY, button);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        if(this.element != null) {
            if (open) {
                settingButtons.forEach(settingButton -> {
                    settingButton.mouseReleased(mouseX, mouseY);
                });
            }
        }
    }

    public void scroll(float in) {
        if(this.element != null) {
            if (open) {
                settingButtons.forEach(settingButton -> {
                    settingButton.scroll(in);
                });
            }
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > featureHeight && mouseX < x + width && mouseY < featureHeight + height;
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

    public void addSettingOffset(double settingOffset) {
        this.settingOffset += settingOffset;
    }



    public ArrayList<SettingButton<?>> getSettingButtons() {
        return settingButtons;
    }
}

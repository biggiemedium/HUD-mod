package dev.px.hud.Rendering.HUD;

import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;

import java.awt.*;

public class RenderElement extends Element {

    private String name;

    private int x;
    private int y;
    private int width;
    private int height;

    private int dragX;
    private int dragY;

    private boolean dragging;
    private Element.HUDType hudType;
    private boolean textElement;

    protected Setting<Boolean> customFont;
    protected Setting<Boolean> rainbowText;
    protected Setting<Color> fontColor;

    public RenderElement(String name, int x, int y, int width, int height, Element.HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hudType = hudType;
        this.textElement = false;
        this.dragging = false;
        settingDefaults();
    }

    public RenderElement(String name, int x, int y, Element.HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = mc.fontRendererObj.getStringWidth(name);
        this.height = mc.fontRendererObj.FONT_HEIGHT;
        this.hudType = hudType;
        this.textElement = false;
        this.dragging = false;
        settingDefaults();
    }

    public RenderElement(String name, int x, int y, int width, int height, Element.HUDType hudType, boolean textElement) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hudType = hudType;
        this.textElement = textElement;
        this.dragging = false;
        settingDefaults();
    }

    public RenderElement(String name, int x, int y, Element.HUDType hudType, boolean textElement) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = mc.fontRendererObj.getStringWidth(name);
        this.height = mc.fontRendererObj.FONT_HEIGHT;
        this.hudType = hudType;
        this.textElement = textElement;
        this.dragging = false;
        settingDefaults();
    }

    public void dragging(int mouseX, int mouseY) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        Renderutil.drawRect(getX() - 1, getY() - 1, getWidth() + 2, getHeight() + 2, new Color(54, 54, 54, dragging ? 150 : 100));
    }

    public void settingDefaults() {
        customFont = new Setting<Boolean>("CustomFont", false, v -> isTextElement());
        this.rainbowText = new Setting<>("Rainbow Text", false, v -> isTextElement());
        fontColor = new Setting<Color>("Color", new Color(255, 255, 255), v -> isTextElement() && rainbowText.getValue());
            getSettings().add(customFont);
            getSettings().add(rainbowText);
            getSettings().add(fontColor);
    }

    protected void renderText(String text, int x, int y, int color) {
        int c = rainbowText.getValue() ? Colorutil.rainbow(8, 1, 0.7f, 0.8f, 0.8f).getRGB() : color;
        if(customFont.getValue()) {
            Fontutil.drawTextShadow(text, x, y, c);
        } else {
            mc.fontRendererObj.drawStringWithShadow(text, x, y, c);
        }
    }

    protected int getFontWidth(String text) {
        return customFont.getValue() ? (int) Fontutil.getWidth(text) : mc.fontRendererObj.getStringWidth(text);
    }

    protected int getFontHeight() {
        return customFont.getValue() ? (int) Fontutil.getHeight() : mc.fontRendererObj.FONT_HEIGHT;
    }


    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            if (button == 0) {
                    dragging = true;
                    dragX = x - mouseX;
                    dragY = y - mouseY;
            }
        }

        if(button == 2 || button == 1) {
            if(isHovered(mouseX, mouseY)) {
                this.setTextElement(!this.isTextElement());
            }
        }
    }

    public void render(float partialTicks) {
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

    public boolean isTextElement() {
        return textElement;
    }

    public void setTextElement(boolean textElement) {
        this.textElement = textElement;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    public HUDType getHudType() {
        return hudType;
    }

    @Override
    public void setHudType(HUDType hudType) {
        this.hudType = hudType;
    }
}

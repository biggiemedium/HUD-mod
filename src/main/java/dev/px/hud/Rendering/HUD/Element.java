package dev.px.hud.Rendering.HUD;

import dev.px.hud.Util.Event.SettingUpdateEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.ArrayList;

public class Element implements Wrapper {

    private String name;

    private int x;
    private int y;
    private int width;
    private int height;

    private int dragX;
    private int dragY;

    private boolean visible;
    private boolean dragging;
    private boolean renderElement;
    private HUDType hudType;

    protected Minecraft mc = Minecraft.getMinecraft();
    protected ArrayList<Setting<?>> settings;

    public Element(String name, int x, int y, int width, int height, HUDType hudType) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
        this.renderElement = true;

        this.settings = new ArrayList<>();
    }

    public Element(String name, int x, int y, HUDType hudType) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = mc.fontRendererObj.getStringWidth(name);
        this.height = mc.fontRendererObj.FONT_HEIGHT;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
        this.renderElement = true;
        this.settings = new ArrayList<>();
    }

    public Element(String name, int x, int y, int width, int height, HUDType hudType, boolean renderElement) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
        this.renderElement = renderElement;

        this.settings = new ArrayList<>();
    }

    public Element(String name, int x, int y, HUDType hudType, boolean renderElement) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = mc.fontRendererObj.getStringWidth(name);
        this.height = mc.fontRendererObj.FONT_HEIGHT;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
        this.renderElement = renderElement;
        this.settings = new ArrayList<>();
    }

    public void render(float partialTicks) {
    }

    public void dragging(int mouseX, int mouseY) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
            GuiScreen.drawRect(x - 1, y - 1, x + width + 5, y + height + 5, new Color(10, 10, 10, 100).getRGB());
            // Animation.moveTowards(x, y, 5);
        }
    }


    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(button == 0) {
            if(isHovered(mouseX, mouseY)) {
                dragging = true;
                dragX = x - mouseX;
                dragY = y - mouseY;
            }
        }

        if(button == 2 || button == 1) {
            if(isHovered(mouseX, mouseY)) {
                this.setVisible(!this.isVisible());
            }
        }
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

    protected <T> Setting<T> create(Setting<T> hudSetting) {
        this.settings.add(hudSetting);
        return hudSetting;
    }

    public String getName() {
        return name;
    }

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public HUDType getHudType() {
        return hudType;
    }

    public void setHudType(HUDType hudType) {
        this.hudType = hudType;
    }


    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
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

    public boolean isRenderElement() {
        return renderElement;
    }

    public void setRenderElement(boolean renderElement) {
        this.renderElement = renderElement;
    }

    public enum HUDType {
        COMBAT("Combat"),
        INFO("Info"),
        RENDER("Render"),
        MOD("Mods");

        HUDType(String name) {
            this.name = name;
        }

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

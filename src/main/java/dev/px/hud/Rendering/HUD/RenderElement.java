package dev.px.hud.Rendering.HUD;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

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
    private boolean visible;

    public RenderElement(String name, int x, int y, int width, int height, Element.HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
    }

    public RenderElement(String name, int x, int y, Element.HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = mc.fontRendererObj.getStringWidth(name);
        this.height = mc.fontRendererObj.FONT_HEIGHT;
        this.hudType = hudType;
        this.visible = false;
        this.dragging = false;
    }

    public void dragging(int mouseX, int mouseY) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
            GL11.glPushMatrix();
            GuiScreen.drawRect(x - 1, y - 1, x + width + 5, y + height + 5, new Color(10, 10, 10, 100).getRGB());
            GL11.glPopMatrix();
        }
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
                this.setVisible(!this.isVisible());
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

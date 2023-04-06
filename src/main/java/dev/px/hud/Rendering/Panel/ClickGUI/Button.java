package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.Command.Command;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class Button {

    private String name;
    private int x, y, width, height;
    private boolean open;
    private Element element;
    private Frame parent;
    private Minecraft mc = Minecraft.getMinecraft();

    public Button(int x, int y, Frame parent, Element element) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.getWidth();
        this.height = 13; // 2 smaller than frame height
        this.element = element;
        this.name = element.getName();
    }

    public void draw(int mouseX, int mouseY) {
        Renderutil.rect(x , y, width, height, true, this.element.isVisible() ? new Color(0x181A18).brighter().brighter() : new Color(0x181A18));
        mc.fontRendererObj.drawStringWithShadow(this.element.getName(), this.x + 2, this.y + (3), -1);
        mc.fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", this.x + (width - mc.fontRendererObj.getStringWidth(this.open ? "-" : "+")) - 3, this.y + 3, -1);
    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                element.setVisible(!element.isVisible());
            } else if(button == 1) {
                this.open = !this.open;
            }
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
}

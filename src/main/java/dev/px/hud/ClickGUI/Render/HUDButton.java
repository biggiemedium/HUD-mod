package dev.px.hud.ClickGUI.Render;

import dev.px.hud.HUD.Element;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.awt.*;
import java.util.ArrayList;

public class HUDButton implements Wrapper {

    private HUDFrame parent;

    private int x, y, width, height;
    private boolean open;
    private Element element;


    public HUDButton(HUDFrame hudFrame, int x, int y, Element element) {
        this.parent = hudFrame;

        this.x = x;
        this.y = y;
        this.width = hudFrame.getWidth();
        this.height = 13;
        this.open = false;
        this.element = element;
    }

    public void drawScreen(int mouseX, int mouseY) {

        Renderutil.drawRect(x , y, x + width, y + this.height, buttonColor(mouseX, mouseY).getRGB());
        mc.fontRendererObj.drawStringWithShadow(this.element.getName(), x + 2, y + 2, -1);

        if(!open) return;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(button == 0) {
            if(hovered(mouseX, mouseY)) {
                this.element.setHidden(!this.element.isHidden());
                mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("hidden " + String.valueOf(this.element.isHidden())));
            }
        }

        if(button == 1) {
            if(hovered(mouseX, mouseY)) {

            }
         }
    }

    private boolean hovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 13;
    }

    private Color buttonColor(int mouseX, int mouseY) {
        if(hovered(mouseX, mouseY)) {
            if(!this.element.isHidden()) {
                return new Color(50, 50, 50, 200).darker().darker();
            }
            return new Color(50, 50, 50, 200).darker();
        }
        if(!this.element.isHidden() && !hovered(mouseX, mouseY)) {
            return new Color(50, 50, 50, 200).darker().darker();
        }

        return new Color(50, 50, 50, 200);
    }

    public HUDFrame getParent() {
        return parent;
    }

    public void setParent(HUDFrame parent) {
        this.parent = parent;
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
}

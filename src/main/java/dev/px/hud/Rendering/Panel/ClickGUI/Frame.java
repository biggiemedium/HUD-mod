package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.ArrayList;

public class Frame {

    private int x, y, width, height;
    private boolean open, dragging;
    private String name;
    private int dragXPos, dragYPos;
    private Element.HUDType type;
    private float scroll;
    private double featureOffset;
    protected Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Button> buttons;

    public Frame(Element.HUDType type, int x, int y) {
            this.name = type.getName();
            this.x = x;
            this.y = y;
            this.width = 88;
            this.height = 15;
            this.type = type;
            this.open = true;
            this.dragging = false;
            this.scroll = 0;
            this.buttons = new ArrayList<>();

            int offset = this.height;
            for(Element b : HUDMod.elementInitalizer.getElementByType(type)) {
                if(b.getHudType() == type) {
                    this.buttons.add(new Button(this.x, this.y + offset, this, b));
                    offset += 15;
                }
            }
    }

    public void draw(int mouseX, int mouseY) {
        if(dragging){
            this.x = mouseX - dragXPos;
            this.y = mouseY - dragYPos;
        }

        Renderutil.drawRect(this.x, this.y,this.x + this.width,this.y + (int)this.height, new Color(0xff181A17).getRGB());
        Renderutil.drawRect(this.x, this.y + height, this.x + width, this.y + height - 2, new Color(39, 179, 206).getRGB());
        mc.fontRendererObj.drawStringWithShadow(type.getName(), this.getX() + (getWidth() / 2.0F) - mc.fontRendererObj.getStringWidth(type.getName()), this.getY() + this.getHeight() / 2 - (mc.fontRendererObj.FONT_HEIGHT / 2.0F), 0xffffffff);


        if(!open) return;

        this.featureOffset = 0;

        // make sure the scroll doesn't go farther than our bounds
        double scaledComponentOffset = getComponentOffset() - 1400;
        scroll = (float) MathHelper.clamp_double(scroll, -Math.max(0, scaledComponentOffset - height), 0);

        int count = 0;
        int offset = 0;
        for(Button b : this.buttons) {
            if(b.getX() != this.x) {
                b.setX(this.x);
            }

            if(b.getY() != y + height + offset) {
                b.setY(y + height + offset);
            }

            b.draw(mouseX, mouseY);
            offset += b.getHeight();
            count += b.getHeight();
        }

        Color color = new Color(25, 163, 191);

        // Vertical
        Renderutil.gradient(this.getX() - 1, this.getY(), 1, this.getHeight() + count + 2, color.brighter().brighter(), color.darker());
        Renderutil.gradient(this.getX() + this.getWidth() - 1, this.getY(), 1, this.getHeight() + count + 2, color.brighter().brighter(), color.darker());

        // Horizontal
        Renderutil.gradient(this.getX() - 1, this.getY() - 1, this.getWidth() + 1, 1, color.darker(), color.brighter().brighter());
        Renderutil.gradient(this.getX() - 1, this.getY() + this.getHeight() + count + 1, this.getWidth(), 1, color.darker(), color.brighter().brighter());

        //Black bar at the bottom
        Renderutil.rect(this.getX(), this.getY() + 1 + this.getHeight() + count - 1, this.getWidth() - 1, 1, new Color(0xff232623));
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                this.dragging = true;
                this.dragXPos = mouseX - this.x;
                this.dragYPos = mouseY - this.y;
            }
            if(button == 1) {
                this.open = !this.open;
            }
        }

        for(Button b : this.buttons) {
            b.mouseClick(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public void handleScroll(int in) {
        if (open) {
            scroll += in * 0.05;
            //moduleComponents.forEach(moduleComponent -> {
            //    moduleComponent.onScroll(in);
            //});
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public double getComponentOffset() {
        return featureOffset;
    }

    public void addComponentOffset(double in) {
        featureOffset += in;
    }

    public float getScroll() {
        return scroll;
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

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDragXPos() {
        return dragXPos;
    }

    public void setDragXPos(int dragXPos) {
        this.dragXPos = dragXPos;
    }

    public int getDragYPos() {
        return dragYPos;
    }

    public void setDragYPos(int dragYPos) {
        this.dragYPos = dragYPos;
    }

    public Element.HUDType getType() {
        return type;
    }

    public void setType(Element.HUDType type) {
        this.type = type;
    }

    public void setScroll(float scroll) {
        this.scroll = scroll;
    }

    public double getFeatureOffset() {
        return featureOffset;
    }

    public void setFeatureOffset(double featureOffset) {
        this.featureOffset = featureOffset;
    }

    public Minecraft getMc() {
        return mc;
    }

    public void setMc(Minecraft mc) {
        this.mc = mc;
    }
}

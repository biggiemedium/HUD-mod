package dev.px.hud.ClickGUI.Render;

import dev.px.hud.HUD.Element;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.Classutil;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

public class HUDFrame implements Wrapper {

    private String name;

    private int x, y, width, height;
    private boolean open;
    private Element.Category category;

    private ArrayList<HUDButton> buttons;

    private int mouseX, mouseY;
    private boolean dragging;

    private Color color;

    public HUDFrame(int x, int y, Element.Category category) {
        this.name = category.getAlias();
        this.x = x;
        this.y = y;
        this.width = 88;
        this.height = 13;

        this.open = true;
        this.category = category;
        this.buttons = new ArrayList<>();
        addButtons();
    }

    private void addButtons() {
        for(Element e : HUDMod.elementInitalizer.getElementbyType(this.category)) {
            this.buttons.add(new HUDButton(this, this.x, this.y, e));
        }
    }

    public void drawScreen(int mouseX, int mouseY) {


        // TODO: Add outline to frame
        this.color = new Color(Renderutil.staticRainbow().getRGB());
        Renderutil.drawRect(x - 2, y, (x + width + 2), y + height, new Color(139, 48, 215, 100).getRGB());
        mc.fontRendererObj.drawStringWithShadow(this.category.getAlias(), x + 2, y + 2, -1);

        drawBar();

        if(dragging) {
            this.x = mouseX - this.mouseX;
            this.y = mouseY - this.mouseY;
        }
        resetOverflow();

        // ensure that this stays above the return statement
        if(!open) return;

        int extraHeight = 0;
        for(HUDButton b : this.buttons) {
            if(b.getX() != this.x) {
                b.setX(this.x);
            }
            if(b.getY() != y + height + extraHeight) {
                b.setY(y + height + extraHeight);
            }
            b.drawScreen(mouseX, mouseY);
            extraHeight += b.getHeight();
        }
    }

    public void mouseRelease(int x, int y, int button) {
        this.dragging = false;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(button == 0) {
            if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 13){
                dragging = true;
                mouseX = mouseX - this.x;
                mouseY = mouseY - this.y;
            }
        }

        if(button == 1) {
            if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 13) {
                if(!open) {
                    open = true;
                } else if(open) {
                    open = false;
                }
            }
        }

        int x_, y_;
        x_ = mouseX;
        y_ = mouseY;

        if(!open) return;
        for(HUDButton b : this.buttons) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void drawBar() {
        Renderutil.drawRect(0, 0, 30, 1, new Color(18, 18, 18, 40).getRGB());

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.25f, 1.25f, 1);
        mc.fontRendererObj.drawStringWithShadow("PX HUD Mod!" + " " + "v:1.0", 3, 2, -1);
        GlStateManager.popMatrix();
    }


    public void resetOverflow() {
        int screenWidth = new ScaledResolution(mc).getScaledWidth();
        int screenHeight = new ScaledResolution(mc).getScaledHeight();

        if (this.width < 0) {
            if (this.x > screenWidth)
                this.x = screenWidth;

            if (this.x + this.width < 0)
                this.x = -this.width;
        }

        else {
            if (this.x < 0)
                this.x = 0;

            if (this.x + this.width > screenWidth)
                this.x = screenWidth - this.width;
        }

        if (this.y < 0)
            this.y = 0;

        if (this.y + this.height > screenHeight)
            this.y = screenHeight - this.height;
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

    public Element.Category getCategory() {
        return category;
    }

    public void setCategory(Element.Category category) {
        this.category = category;
    }

    public ArrayList<HUDButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<HUDButton> buttons) {
        this.buttons = buttons;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

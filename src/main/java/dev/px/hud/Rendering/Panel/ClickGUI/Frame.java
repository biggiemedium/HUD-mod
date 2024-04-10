package dev.px.hud.Rendering.Panel.ClickGUI;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Elements.Combat.Armor;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
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
    private float fullheight;

    private ArrayList<Button> buttons;
    private Animation openAnimation = new Animation(300, false, Easing.LINEAR);
    private ClickGUI clickGUI;
    private boolean expanding;

    public Frame(Element.HUDType type, int x, int y, ClickGUI clickGUI) {
            this.name = type.getName();
            this.x = x;
            this.y = y;
            this.width = 88;
            this.height = 15;
            this.type = type;
            this.open = false;
            this.dragging = false;
            this.scroll = 0;
            this.fullheight = 196;
            this.clickGUI = clickGUI;
            this.buttons = new ArrayList<>();

            int offset = this.height;
            for(Element b : HUDMod.elementInitalizer.getElementByType(type)) {
                if(b.getHudType() == type) {
                    this.buttons.add(new Button(this.x, this.y + offset, this, b));
                    offset += 15;
                }
            }

            for(int i = 0; i < 100; i++) {
                this.buttons.add(new Button(this.x, this.y + offset, this, null));
                offset += 15;
            }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        if(dragging){
            this.x = mouseX - dragXPos;
            this.y = mouseY - dragYPos;
        }
        this.openAnimation.setState(open);
        if (open) {
            long interactingWindows = getClickGUI().getFrames()
                    .stream()
                    .filter(categoryFrameComponent -> !categoryFrameComponent.equals(this))
                    .filter(categoryFrameComponent -> categoryFrameComponent.isExpanding() || categoryFrameComponent.isDragging())
                    .count();

            if(interactingWindows <= 0) {
                if (isDragging() || expanding) {
                //    fullheight = MathHelper.clamp_float((mouseY - getY()) - height, 0, 350);
                }
            }
        }


        Renderutil.drawRoundedRect(this.x, this.y + 1, this.x + this.width, this.y + (int) this.fullheight * (float) openAnimation.getLinearFactor(), 1, new Color(0xff181A17).getRGB());
        Renderutil.drawRoundedRect(this.x, this.y,this.x + this.width,this.y + (int)this.height, 1, new Color(0xff181A17).getRGB());
        // Horizontal line across title bar
        //Renderutil.drawRoundedRect(this.x - 0.8f, this.y + height, this.x + this.width, this.y + this.height - 2, 1, new Color(39, 179, 206).getRGB());
        Renderutil.drawRoundedRect(this.x - 0.8f, this.y + height, this.x + this.width, this.y + this.height - 2, 1, new Color(HUDMod.colorManager.getMainColor().getRGB()).getRGB());
        // Line going down entire bar

        //Fontutil.drawTextShadow(type.getName(), getX(), (int) getY() + (int) (width / 2) - (int) Fontutil.getWidth(type.getName()), -1);
      //  mc.fontRendererObj.drawStringWithShadow(type.getName(), this.getX() + (getWidth() / 2.0F) - mc.fontRendererObj.getStringWidth(type.getName()), this.getY() + this.getHeight() / 2 - (mc.fontRendererObj.FONT_HEIGHT / 2.0F), 0xffffffff);
        Fontutil.drawTextShadow(type.getName(), (getX() + (getWidth() - Fontutil.getWidth(type.getName())) / 2), getY() + 5, -1);
        double scaledComponentOffset = getComponentOffset() - 1400;
        scroll = (float) MathHelper.clamp_double(scroll, -Math.max(0, scaledComponentOffset - fullheight), 0);

        if(open) {
            this.featureOffset = 0;
        }

        if(openAnimation.getAnimationFactor() > 0) {
            this.getClickGUI().getScissorStack().pushScissor(x, getY() + height, width, (int) (fullheight * getOpenAnimation().getAnimationFactor()));
            this.featureOffset = 0;

            double count = 0;
            double offset = 0;
                for(Button b : this.buttons) {
                    if(b.getX() != this.x) {
                        b.setX(this.x);
                    }
                    if(b.getY() != this.y + featureOffset + getHeight()) {
                        b.setY(this.y + featureOffset + getHeight());
                    }

                    b.draw(mouseX, mouseY, partialTicks);
                    offset += b.getHeight();
                    count += b.getHeight();
                }
            this.getClickGUI().getScissorStack().popScissor();
        }
    }


    public void mouseClicked(int mouseX, int mouseY, int button) {
            if (isHovered(mouseX, mouseY)) {
                if (button == 0) {
                    this.dragging = true;
                    this.dragXPos = mouseX - this.x;
                    this.dragYPos = mouseY - this.y;
                }
                if (button == 1) {
                    this.open = !this.open;
                    this.openAnimation.setState(open);
                }

        }

            if(isHovered2(mouseX, mouseY)) {
                if(button == 0) {
                    expanding = true;
                }
            }

        if(open) {
            for (Button b : this.buttons) {
                b.mouseClick(mouseX, mouseY, button);
            }
        }
    }

    public void keyTyped(char key, int keyCode) {
        if(open) {
            this.buttons.forEach(button -> {
                button.keyTyped(key, keyCode);
            });
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
        this.expanding = false;
        if(open) {
            this.buttons.forEach(button -> {
                button.mouseReleased(mouseX, mouseY);
            });
        }
    }

    public void handleScroll(float in) {
        if (open) {
            scroll += in * 0.05;
            buttons.forEach(button -> button.scroll(in));
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public boolean isHovered2(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.fullheight;
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

    public void addFeatureOffset(double featureOffset) {
        this.featureOffset += featureOffset;
    }
    public Minecraft getMc() {
        return mc;
    }

    public void setMc(Minecraft mc) {
        this.mc = mc;
    }

    public float getFullheight() {
        return fullheight;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public Animation getOpenAnimation() {
        return openAnimation;
    }

    public ClickGUI getClickGUI() {
        return clickGUI;
    }

    public void setFeatureOffset(double featureOffset) {
        this.featureOffset = featureOffset;
    }

    public void setFullheight(float fullheight) {
        this.fullheight = fullheight;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    public void setOpenAnimation(Animation openAnimation) {
        this.openAnimation = openAnimation;
    }

    public void setClickGUI(ClickGUI clickGUI) {
        this.clickGUI = clickGUI;
    }

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }
}

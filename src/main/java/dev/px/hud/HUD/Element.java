package dev.px.hud.HUD;

import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.ArrayList;

public class Element implements Wrapper {

    private String name;
    private String description;

    private int x;
    private int y;

    private int width;
    private int height;

    private int mouseX;
    private int mouseY;

    private boolean hidden;
    private Category category;
    private boolean dragging;

    private ArrayList<Setting<?>> settings;

    public Element(String name, int defaultX, int defaultY, Category category) {
        this.name = name;
        this.x = defaultX;
        this.y = defaultY;

        this.width = mc.fontRendererObj.getStringWidth(name) + 4;
        this.height = mc.fontRendererObj.FONT_HEIGHT + 2;

        this.settings = new ArrayList<>();

        this.category = category;
        this.hidden = true;
        this.dragging = false;
    }

    public Element(String name, int defaultX, int defaultY, int width, int height, Category category) {
        this.name = name;
        this.x = defaultX;
        this.y = defaultY;

        this.width = width;
        this.height = height;

        this.settings = new ArrayList<>();

        this.category = category;
        this.hidden = true;
        this.dragging = false;
    }

    public void render(float partialTicks) {

        if(this.isHidden()) return;

        resetOverflow();
    }

    public void mouseClicked(int mouseX_, int mouseY_, int button) {
        if (width < 0) {
            if (button == 0 && mouseX_ < x && mouseX_ > x + width && mouseY_ > y && mouseY_ < y + height) {
                dragging = true;
                this.mouseX = mouseX_ - this.x;
                this.mouseY = mouseY_ - this.y;
            }
        } else {
            if (button == 0 && mouseX_ > x && mouseX_ < x + width && mouseY_ > y && mouseY_ < y + height) {
                dragging = true;
                this.mouseX = mouseX_ - this.x;
                this.mouseY = mouseY_ - this.y;
            }
        }
    }

    public void drag(int mouseX, int mouseY) {
        if(dragging) {
            this.x = mouseX - this.mouseX;
            this.y = mouseY - this.mouseY;
            Renderutil.drawRect(x - 1, y - 1, x + width + 5, y + height + 5, new Color(10, 10, 10, 100).getRGB());
        }
    }

    public void mouseRelease(int x, int y, int button) {
        this.dragging = false;
    }

    public void toggleState() {
        if (this.hidden)
            MinecraftForge.EVENT_BUS.unregister(this);
        else
            MinecraftForge.EVENT_BUS.register(this);

        this.hidden = !this.hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean hasSettings() {
        return settings.size() > 0;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Setting<?>> settings) {
        this.settings = settings;
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

    public enum Category {
        COMBAT("Combat"),
        PLAYER("Player"),
        MISC("Misc");

        String name;

        Category(String alias) {
            name = alias;
        }

        public String getAlias() {
            return name;
        }
    }
}

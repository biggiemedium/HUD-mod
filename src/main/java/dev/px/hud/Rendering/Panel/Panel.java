package dev.px.hud.Rendering.Panel;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class Panel {

    private String name;
    private boolean defaultBackground;

    public Panel(String name) {
        this.name = name;
        this.defaultBackground = false;
    }

    public Panel(String name, boolean defaultBackground) {
        this.name = name;
        this.defaultBackground = defaultBackground;
    }

    public Panel() {
        this.name = "";
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {

    }

    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void scroll(int in) {
        if(Mouse.hasWheel()) {

        }
    }

    public boolean isDefaultBackground() {
        return defaultBackground;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultBackground(boolean defaultBackground) {
        this.defaultBackground = defaultBackground;
    }

    public String getName() {
        return name;
    }

    public boolean isNameValid() {
        return !(this.name).equals("");
    }

    protected Minecraft mc = Minecraft.getMinecraft();

}

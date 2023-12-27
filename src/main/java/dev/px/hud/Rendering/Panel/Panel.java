package dev.px.hud.Rendering.Panel;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class Panel {

    private String name;

    public Panel(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public boolean isNameValid() {
        return !(this.name).equals("");
    }

    protected Minecraft mc = Minecraft.getMinecraft();

}

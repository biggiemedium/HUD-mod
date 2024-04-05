package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.Rendering.Panel.Settings.ClientSettings;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Shader.Shaders.GradientShader;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ClientSettingsScreen extends Screen {

    public int x, y, width, height;

    public ClientSettingsScreen(int x, int y, int width, int height) {
        super("Settings");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setResourceLocation(new ResourceLocation("minecraft", "GUI/client.png"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        Fontutil.drawText("Preferences", x + 10, y + 10, -1);
        RoundedShader.drawGradientCornerRL(x + 5, (y + 10 + (int) (Fontutil.getHeight() + 2)), Fontutil.getWidth("Preferences") + 30, 0.4f, 4, new Color(255, 255, 255, 200), new Color(255, 255, 255, 0));

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}

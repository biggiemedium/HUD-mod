package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Dimension;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.API.Render.GlUtils;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Render.Texture;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreen {

    public int x;
    public int y;
    public int width;
    public int height;

    public boolean dragging = false;
    private int dragX, dragY;

    private Animation startupAnimation = new Animation(200, false, Easing.LINEAR);
    private boolean close = false;

    private Minecraft mc = Minecraft.getMinecraft();
    private ArrayList<Screen> screens = new ArrayList<>();
    private Screen currentScreen = null;

    private Dimension<Integer> screenPanels;
    private Dimension<Integer> GUIButton;

    public MainScreen(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        screenPanels = new Dimension<>(x + 75, y + 20, width - 75, height - 20);

        this.screens.add(new ModuleScreen(screenPanels.getX(), screenPanels.getY(), screenPanels.getWidth(), screenPanels.getHeight()));
        this.screens.add(new ClientSettingsScreen(screenPanels.getX(), screenPanels.getY(), screenPanels.getWidth(), screenPanels.getHeight()));
        this.screens.add(new HUDEditorScreen());

        if(currentScreen == null) {
            currentScreen = screens.get(0);
        }
    }

    public void init() {
        startupAnimation.setState(true);
        this.close = false;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        if(currentScreen.getName().equalsIgnoreCase("HUD Editor")) {
            GUIButton = new Dimension<>(sr.getScaledWidth() / 2 - 25 , sr.getScaledHeight() / 2 - 12, 35, 20);
            RoundedShader.drawRound(GUIButton.getX() , GUIButton.getY(), GUIButton.getWidth(), GUIButton.getHeight(), 6, new Color(30, 30, 30));
            Fontutil.drawText("GUI", (int) (GUIButton.getX() + (GUIButton.getWidth() / 2 - (Fontutil.getWidth("GUI") / 2))), (int) GUIButton.getY() + (int) (GUIButton.getHeight() / 2 - (Fontutil.getHeight() / 2)), -1);

            return;
        }

        if(dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        if(!dragging) {
            if(this.getX() + this.getWidth() > sr.getScaledWidth()) {
                x = sr.getScaledWidth() - ((this.getWidth() + this.getX()) - this.getX()) - 2;
            }
            if(this.getY() + this.getHeight() > sr.getScaledHeight()) {
                y = sr.getScaledHeight() - ((this.getY() + this.getHeight()) - this.getY()) - 2;
            }
            if(x < 0) {
                x = 2;
            }
            if(y < 0) {
                y = 2;
            }
        }

        if(close) {
            startupAnimation.setState(false);
            if(!startupAnimation.getState() && startupAnimation.getAnimationFactor() <= 0) {
                mc.displayGuiScreen(null);
            }
        }

        if(startupAnimation.getAnimationFactor() > 0) {
            GlUtils.startScale(((this.getX()) + (this.getX() + this.getWidth())) / 2, ((this.getY()) + (this.getY() + this.getHeight())) / 2, (float) startupAnimation.getAnimationFactor());
            // Main screen
            GL11.glPushMatrix();
            RoundedShader.drawRound(x, y, width, height, 3, new Color(0xff181A17));
            GL11.glPopMatrix();

            // Side panel
            GL11.glPushMatrix();
            // bar
            RoundedShader.drawRound(x, y, 75, height, 3, new Color(40, 40, 40));
            RoundedShader.drawRound(x, y, width, 15, 3, new Color(40, 40, 40));

            // title
            Fontutil.drawTextShadow(HUDMod.NAME, x + 7, y + 6, -1);
            Fontutil.drawTextShadow(HUDMod.VERSION, x + (Fontutil.getWidth(HUDMod.NAME) - 2), (int) (y + 6 + Fontutil.getHeight()), new Color(255, 255, 255, 200).getRGB());
            RoundedShader.drawRound(x + 4, (int) (y + 9 + (Fontutil.getHeight() + 9)), Fontutil.getWidth(HUDMod.NAME) + 14, 1, 2, new Color(255, 255, 255));

            // Buttons
            int offsetY = y + 40;
            int buttonHeight = 20;
            for(Screen s : screens) {
                Texture t = new Texture(s.getResourceLocation());
                if(currentScreen == null) {
                    currentScreen = screens.get(0);
                }

                if(s == currentScreen) {
                    //RoundedShader.drawRound(x + 2, offsetY - 1, 72, 19, 1, Colorutil.interpolateColorsBackAndForth(15, 1, HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), true));
                    RoundedShader.drawRound(x + 1, offsetY, 74, 17, 1.5f, new Color(38, 38, 38));
                    RoundedShader.drawRound(x, offsetY, 0.8f, 17, 1, new Color(255, 255, 255));

                    screenPanels.update(x + 75, y + 20, width - 75, height - 20);
                    currentScreen.setX(screenPanels.getX());
                    currentScreen.setY(screenPanels.getY());
                    currentScreen.setWidth(screenPanels.getWidth());
                    currentScreen.setHeight(screenPanels.getHeight());
                    currentScreen.render(mouseX, mouseY, partialTicks);
                } else {
                    RoundedShader.drawRound(x + 1, offsetY, 74, 17, 1, new Color(38, 38, 38));
                }

                if(t.getLocation() != null) { // im so confused
                    t.renderT((int) (x + 4), offsetY + 2, 14, 14);
                }


                Fontutil.drawTextShadow(s.getName(), x + 23, (int) (offsetY + (Fontutil.getHeight() / 2)), -1);
                offsetY += 17;
            }
            GL11.glPopMatrix();

            GlUtils.stopScale();
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 20) {
            this.dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }

        if(currentScreen.getName().equalsIgnoreCase("HUD Editor")) {
            if(this.GUIButton != null) {
                if(isHovered(GUIButton.getX(), GUIButton.getY(), GUIButton.getWidth(), GUIButton.getHeight(), mouseX, mouseY)) {

                    this.currentScreen = screens.get(0); // Mod
                }
            }
        }

        if(startupAnimation.getAnimationFactor() > 0) {
            try {
                currentScreen.onClick(mouseX, mouseY, button);
            } catch (IOException e) {e.printStackTrace();}
            int offset = y + 40;
            for(Screen s : screens) {
                if(isHovered(x, offset, 74, 17, mouseX, mouseY) && button == 0) {
                    this.currentScreen = s;
                }
                offset += 17;
            }

        }
    }

    public boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    public void keyPressed(char typedChar, int keyCode) {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            this.close = true;
        }
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

    public Animation getStartupAnimation() {
        return startupAnimation;
    }

    public void setStartupAnimation(Animation startupAnimation) {
        this.startupAnimation = startupAnimation;
    }

    private class Button {

        public int x;
        public int y;
        public int width;
        public int height;
        private boolean selected = false;

        public Button(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.selected = false;
        }

        public void render(int mouseX, int mouseY) {

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
    }
}

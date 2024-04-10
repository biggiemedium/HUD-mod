package dev.px.hud.Rendering.Panel.Settings;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Panel.Panel;
import dev.px.hud.Rendering.Panel.Settings.PreferenceButtons.BooleanPreference;
import dev.px.hud.Rendering.Panel.Settings.PreferenceButtons.PreferenceButton;
import dev.px.hud.Util.API.Animation.SimpleAnimation;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Render.Color.AccentColor;
import dev.px.hud.Util.API.Render.Color.Colorutil;
import dev.px.hud.Util.API.Render.GlUtils;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClientSettings extends Panel {

    private int x, y, width, height;
    private int dragX = 0, dragY = 0;
    private boolean dragging = false;
    private ScaledResolution sr = new ScaledResolution(mc);
    private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0f);
    private boolean canToggle = false;
    private double scrollX;
    private double scrollY;
    private ArrayList<PreferenceButton> buttons = new ArrayList<>();

    public ClientSettings() {
        super("Settings");
        this.x = sr.getScaledWidth() / 2 - 150;
        this.y = sr.getScaledHeight() / 2 - 100;
        this.width = 250;
        this.height = 215;
        this.scrollY = 0;

        int offset = 0;
        for(Setting s : HUDMod.preferenceManager.getPreferences()) {
            if(s != null) {
                if (s.getValue() instanceof Boolean) {
                    this.buttons.add(new BooleanPreference(x + 10, y + 45 + offset, width - 75, 15, s));
                    offset += 16;
                }
            }
        }

    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        if(dragging){
            this.x = mouseX - dragX;
            this.y = mouseY - dragY;
        }

        RoundedShader.drawRoundOutline(x, y, width, height, 4,0.2f, new Color(0xff181A17), new Color(255, 255, 255));
        Fontutil.drawTextShadow("Settings", x + width / 2 - Fontutil.getWidth("Settings"), y + 3, -1);

        // Preferences
        Renderutil.ScissorStack stack = new Renderutil.ScissorStack();
        Fontutil.drawTextShadow("Client Preferences", x + 10, y + 45 - (int) (Fontutil.getHeight() + 3), -1);
        RoundedShader.drawRound(x + 10, y + 45, width - 75, 70, 4, new Color(38, 38, 38));
        stack.pushScissor(x + 10, y + 45, width - 75, 70);
        int offset = 0;
        for (PreferenceButton b : this.buttons) {
            if(b.getX() != this.x + 10) {
                b.setX(this.x + 10);
            }
            if(b.getY() != this.y + 45 + offset + (int) scrollY) {
                b.setY(this.y + 45 + offset + (int) scrollY);
            }
            b.draw(mouseX, mouseY, partialTicks);
            offset += b.getHeight() + 1;
        }
        stack.popScissor();

        if(isMouseOver(x + 10, y + 45, width - 75, 70, mouseX, mouseY)) {
            this.scrollY += Mouse.getDWheel() * 0.02;
        }

        if(scrollY > 10) {
            scrollY = 10;
        } else if(scrollY < -offset + 20) {
            scrollY = (-offset + 20);
        }

        // Color picker
        // Ashamed to say I skidded this from soar :(
        // It had to be done...
        int offsetX = 14;
        int colorIndex = 1;
        stack.pushScissor(x + 95, y + 155, 153, 52);
        for(AccentColor c : HUDMod.colorManager.getAccentColors()) {
            Color color1 = Colorutil.interpolateColorsBackAndForth(15, 0, c.getMainColor(), c.getAlternativeColor(), false);
            Color color2 = Colorutil.interpolateColorsBackAndForth(15, 90, c.getMainColor(), c.getAlternativeColor(), false);
            Color color3 = Colorutil.interpolateColorsBackAndForth(15, 180, c.getMainColor(), c.getAlternativeColor(), false);
            Color color4 = Colorutil.interpolateColorsBackAndForth(15, 270, c.getMainColor(), c.getAlternativeColor(), false);
            boolean selectedColor = HUDMod.colorManager.getAccentColors().equals(c);

            RoundedShader.drawGradientRound(x + 95 + offsetX + scrollAnimation.getValue(), y + 178 , 20, 20, 10, color1, color2, color3, color4);
            c.opactiy.setAnimation(selectedColor ? 255 : 0, 14);

            GlUtils.startScale(x + 95 + offsetX + scrollAnimation.getValue() + 6, y + 178 + 6, 20 - 12, 20 - 12, c.zoom.getValue());
            RoundedShader.drawRound(x + 95 + offsetX + scrollAnimation.getValue() + 6, y + 178 + 6, 20 - 12, 20 - 12, 4, Colorutil.interpolateColorsBackAndForth(15, 1, new Color(255, 255, 255, 200), new Color(26, 26, 26, 200), false));
            GlUtils.stopScale();

            offsetX += 28;
            colorIndex++;
        }

        stack.popScissor();
        RoundedShader.drawGradientRound(x + 20, y + 178 - 40, 60, 60, 4,
                Colorutil.interpolateColorsBackAndForth(15, 0, HUDMod.colorManager.currentColor.getMainColor(), HUDMod.colorManager.currentColor.getAlternativeColor(), false),
                Colorutil.interpolateColorsBackAndForth(15, 90, HUDMod.colorManager.currentColor.getMainColor(), HUDMod.colorManager.currentColor.getAlternativeColor(), false),
                Colorutil.interpolateColorsBackAndForth(15, 180, HUDMod.colorManager.currentColor.getMainColor(), HUDMod.colorManager.currentColor.getAlternativeColor(), false),
                Colorutil.interpolateColorsBackAndForth(15, 270, HUDMod.colorManager.currentColor.getMainColor(), HUDMod.colorManager.currentColor.getAlternativeColor(), false));

        Fontutil.drawTextShadow(HUDMod.colorManager.currentColor.getName(), x + 25 + 65, y + 155, -1);

        int scroll = Mouse.getDWheel();
        if(scroll > 0) { // up
            if(scrollX < -10) {
                scrollX +=20;
            }else {
                if(colorIndex > 5) {
                    scrollX = 0;
                }
            }
        } else if(scroll < 0) {
            if(scrollX > -((colorIndex - 7.5) * 28)) {
                scrollX -=20;
            }

            if(colorIndex > 5) {
                if(scrollX < -((colorIndex - 9) * 28)) {
                    scrollX = -((colorIndex - 7.6) * 28);
                }
            }
        }
        scrollAnimation.setAnimation((float) scrollX, 16);
        if(isMouseOver(x + 95, y + 155, 200, 52, mouseX, mouseY)) {
            canToggle = true;
        } else {
            canToggle = false;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isMouseOver(x, y, width, 15, mouseX, mouseY)) {
            if(button == 0) {
                dragging = true;
                this.dragX = mouseX - this.x;
                this.dragY = mouseY - this.y;
            }
        }

        int offsetX = 14;
        for(AccentColor c : HUDMod.colorManager.getAccentColors()) {
            if(isMouseOver(x + 95 + offsetX + scrollAnimation.getValue(), y + 178, 20, 20, mouseX, mouseY) && canToggle) {
                if(button == 0) {
                    HUDMod.colorManager.setCurrentColor(c);
                }
            }

            offsetX += 28;
        }

        for(PreferenceButton b : buttons) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;

        for(PreferenceButton b : buttons) {
            b.mouseReleased(mouseX, mouseY);
        }

    }

    boolean isMouseOver(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }
}

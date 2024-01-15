package dev.px.hud.Rendering.HUD.Elements.Info;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Settings.Setting;

import java.util.Calendar;

public class WelcomeElement extends RenderElement {

    public WelcomeElement() {
        super("Welcomer", 150, 20, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Boolean> ogTheme = create(new Setting<>("OG theme", true, v -> !rainbowText.getValue()));
    Setting<Boolean> time = create(new Setting<>("Time sensitive", true, v -> !ogTheme.getValue()));

    @Override
    public void render(float partialTicks) {
        renderText(getWelcomeMessage(), getX(), getY(), fontColor.getValue().getRGB());
        setWidth(getFontWidth(getWelcomeMessage()));
        setHeight(getFontHeight());
    }

    public String getWelcomeMessage() {

        final int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        final String morning = "Morning, " + ChatFormatting.GOLD + mc.thePlayer.getName() + ChatFormatting.RESET;
        final String afternoon = "Afternoon, " + ChatFormatting.GOLD + mc.thePlayer.getName() + ChatFormatting.RESET;
        final String evening = "Evening, " + ChatFormatting.GOLD + mc.thePlayer.getName() + ChatFormatting.RESET;

        if(ogTheme.getValue()) {
            if(time < 12) {
                return morning;
            } else if (time < 16) {
                return afternoon;
            } else {
                return evening;
            }
        } else {
            if(this.time.getValue()) {
                if(time < 12) {
                    return "Morning, " + mc.thePlayer.getName();
                } else if (time < 16) {
                    return "Afternoon, " + mc.thePlayer.getName();
                } else {
                    return "Evening, " + mc.thePlayer.getName();
                }
            } else {
                return "Welcome " + mc.thePlayer.getName();
            }
        }
    }


}

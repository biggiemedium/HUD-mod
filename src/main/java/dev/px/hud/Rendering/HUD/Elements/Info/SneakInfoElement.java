package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.AutoSprint;
import dev.px.hud.Rendering.HUD.RenderElement;

public class SneakInfoElement extends RenderElement {

    public SneakInfoElement() {
        super("Sneak Info", 2, 125, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render(float partialTicks) {

        renderText(getSprintState(), getX(), getY(), fontColor.getValue().getRGB());

        setWidth(getFontWidth(getSprintState()));
        setWidth(getFontHeight());
    }

    public String getSprintState() {

        final String sneaking = "Sneaking";
        final String running = "Sprinting";
        final String toggled = "Sprinting (Toggled)";

        if(mc.thePlayer.isSneaking()) {
            return sneaking;
        } else if (mc.thePlayer.isSprinting()) {
            return running;
        } else if(HUDMod.elementInitalizer.isElementToggled(AutoSprint.class)) {
            return toggled;
        } else {
            return "None";
        }
    }
}

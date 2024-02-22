package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.AutoSprint;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;

public class SneakInfoElement extends RenderElement {

    public SneakInfoElement() {
        super("Sneak Info", 2, 125, HUDType.INFO);
        setTextElement(true);
    }

    @Override
    public void render(float partialTicks) {
        setWidth(25);
        setWidth(getFontHeight() + 4);
        renderText(getSprintState(), getX(), getY(), fontColor.getValue().getRGB());
    }

    public String getSprintState() {

        String state = "None";
        final String sneaking = "Sneaking";
        final String running = "Sprinting";
        final String toggled = "Sprinting (Toggled)";

        if(mc.thePlayer.isSneaking()) {
            state =  sneaking;
        } else if (mc.thePlayer.isSprinting()) {
            state = running;
        } else if(HUDMod.elementInitalizer.isElementToggled(AutoSprint.class)) {
            state = toggled;
        } else if (mc.currentScreen instanceof PanelGUIScreen) {
            state = "Sprinting";
        } else {
            return "None";
        }
        return state;
    }
}

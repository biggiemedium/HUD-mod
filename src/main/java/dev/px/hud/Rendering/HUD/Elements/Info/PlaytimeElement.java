package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PlaytimeElement extends RenderElement {

    public PlaytimeElement() {
        super("Playtime", 25, 100, HUDType.INFO);
        setTextElement(true);
        setHeight(70);
        setWidth(85);
    }

    @Override
    public void render(float partialTicks) {
        String s = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(HUDMod.playTime),
                TimeUnit.MILLISECONDS.toSeconds(HUDMod.playTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(HUDMod.playTime)));

        mc.fontRendererObj.drawStringWithShadow("Playtime: " + s, getX(), getY(), -1);
    }
}

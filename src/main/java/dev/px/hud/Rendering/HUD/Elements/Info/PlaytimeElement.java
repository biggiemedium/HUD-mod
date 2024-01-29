package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.RoundedShader;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class PlaytimeElement extends RenderElement {

    public PlaytimeElement() {
        super("Playtime", 25, 100, HUDType.INFO);
        setTextElement(true);
        setHeight(70);
        setWidth(85);
    }

    @Override
    public void render(float partialTicks) {

    }

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            RoundedShader.drawRoundOutline(getX(), getY(), getWidth(), getHeight(), 6, 2, new Color(35, 33, 33, 225), new Color(33, 30, 30, 255));
        }
    }

}

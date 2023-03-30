package dev.px.hud.Util;

import dev.px.hud.ClickGUI.ClickGUI;
import dev.px.hud.ClickGUI.HUDGUI;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render2dEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class Classutil extends Util {

    public Classutil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if(world == null || player == null) return;

        if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            HUDMod.elementInitalizer.getElements().forEach(element -> {
                if(!element.isHidden()) {
                    element.render(event.partialTicks);
                }
            });
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent event) {
        try {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if(keyCode <= 0)
                        return;

                    if(keyCode == Keyboard.KEY_RCONTROL) {
                        mc.displayGuiScreen(new ClickGUI());
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
}

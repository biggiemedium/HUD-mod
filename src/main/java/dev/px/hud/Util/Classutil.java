package dev.px.hud.Util;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Panel.Panel;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Util;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Classutil extends Util {

    public Classutil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if(world == null || player == null) return;

        if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            for(Element e : HUDMod.elementInitalizer.getElements()) {
                if(e.isVisible()) {
                    e.render(event.partialTicks);
                }
            }
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
                        mc.displayGuiScreen(new PanelGUIScreen());
                    }

                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
}

package dev.px.hud.Util;

import dev.px.hud.Initalizer.SoundInitalizer;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Panel.Panel;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Chatutil;
import dev.px.hud.Util.API.Util;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Classutil extends Util {

    public Classutil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
            for (Element e : HUDMod.elementInitalizer.getElements()) {
                if (e.isVisible()) {
                    e.render(event.partialTicks);
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

                    if(keyCode == Keyboard.KEY_RSHIFT) {
                        mc.displayGuiScreen(new PanelGUIScreen());
                    }

                    HUDMod.elementInitalizer.getElements().forEach(element -> {
                        if(element instanceof ToggleableElement) {
                            if(keyCode == ((ToggleableElement) element).keybind) {
                                element.setVisible(!element.isVisible());
                            }
                        }
                    });
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }

    @SubscribeEvent
    public void joinEvent(EntityJoinWorldEvent event) {
        if(event.entity == mc.thePlayer) {
            Util.sendClientSideMessage("To open the GUI press RSHIFT!", true);
        }
    }
}

package dev.px.hud.Util;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.DropdownNotification;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.HackDetector.DetectionCheck;
import dev.px.hud.Util.API.Input.BindRegistry;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Client.ChatReceiveEvent;
import dev.px.hud.Util.Event.Client.ElementToggleEvent;
import dev.px.hud.Util.Event.Client.SettingUpdateEvent;
import dev.px.hud.Util.Event.ReceivePacketEvent;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor extends Util {

    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private ResourceLocation shaders = new ResourceLocation("minecraft", "shaders/post/blur" + ".json");
    public DetectionCheck check = new DetectionCheck();

    @SubscribeEvent
    public void onRubberBand(ReceivePacketEvent event) {
        if(event.getPacket() instanceof S08PacketPlayerPosLook) {
            HUDMod.notificationManager.AddPushNotification(new Notification("Rubberband", "Rubber banding detected", Notification.NotificationType.ERROR, 4000));
        }
    }


    @SubscribeEvent
    public void onChatRecieve(ChatReceiveEvent event) {
        if(event.getMessage().getUnformattedText().toLowerCase().contains("players remain")) {
            if(HUDMod.serverManager.isOnHypixel()) {

            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            HUDMod.elementInitalizer.getElements().forEach(e -> {
                if(e instanceof RenderElement) {
                    if(e.isToggled()) {
               //         ((RenderElement) e).render2D(event, event.partialTicks);
                    }
                }
            });

            if(!(mc.currentScreen instanceof PanelGUIScreen)) {
                HUDMod.notificationManager.render2D();
            }

        } else if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            ScaledResolution sc = new ScaledResolution(mc);
            Render2DEvent renderEvent = new Render2DEvent(event.partialTicks, sc);
            MinecraftForge.EVENT_BUS.post(renderEvent);
            HUDMod.elementInitalizer.getElements().forEach(element -> {
                if(element instanceof RenderElement) {
                    if(element.isToggled()) {
                        ((RenderElement) element).render2D(renderEvent);
                    }
                }
            });

        }

    }

    @SubscribeEvent
    public void onRenderPost(RenderGameOverlayEvent.Post event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            HUDMod.elementInitalizer.getElements().forEach(e -> {
                if(e instanceof RenderElement) {
                    if(e.isToggled()) {
                        ((RenderElement) e).render(event.partialTicks);
                    }
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

                    if(mc.thePlayer != null && mc.theWorld != null) {
                        if (keyCode == BindRegistry.guiKey.getKeyCode()) {
                            mc.displayGuiScreen(HUDMod.screen);
                        }
                        if(keyCode == Keyboard.KEY_PERIOD) {
                            mc.displayGuiScreen(new GuiChat());
                        }

                        if (keyCode == Keyboard.KEY_U) {
                            HUDMod.notificationManager.AddDropdownNotification(new DropdownNotification("Example text", 2));
                        }
                    }

                    for(Element e : HUDMod.elementInitalizer.getElements()) {
                        if(e instanceof ToggleableElement) {
                            if(keyCode == ((ToggleableElement) e).getKey()) {
                                ((ToggleableElement) e).toggle();
                            }
                        }
                    }

                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }

    @SubscribeEvent
    public void joinEvent(EntityJoinWorldEvent event) {
        if(event.entity == mc.thePlayer) {
            assert BindRegistry.guiKey != null;
            Util.sendClientSideMessage("To open the GUI press " + Keyboard.getKeyName(BindRegistry.guiKey.getKeyCode()) + "!", true);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(event.type == TickEvent.Type.CLIENT) {
            if(Util.isNull()) {
                return;
            }
            HUDMod.elementInitalizer.getElements().forEach(element -> {
                if (element instanceof ToggleableElement) {
                if (element.isToggled()) {
                    ((ToggleableElement) element).onUpdate();
                }
            }
            });

            check.onUpdate();
        }

    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if(Util.isNull()) {
            return;
        }

        mc.mcProfiler.startSection("hudmod");
        Render3dEvent render3dEvent = new Render3dEvent(event.partialTicks);
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof ToggleableElement) {
                if(((ToggleableElement) e).isToggled()) {
                    ((ToggleableElement) e).onRender(render3dEvent);
                }
            }
        }
        mc.mcProfiler.endSection();
    }

    @SubscribeEvent
    public void onEntityHit(AttackEntityEvent event) {
        HUDMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onEnableMod(ElementToggleEvent.ElementEnableEvent event) {

    }

    @SubscribeEvent
    public void onSettingUpdate(SettingUpdateEvent event) {
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof RenderElement) {
                if(event.getSetting() == HUDMod.preferenceManager.CUSTOMFONT) {
                    ((RenderElement) e).customFont.setValue(HUDMod.preferenceManager.CUSTOMFONT.getValue());
                }
            }
        }

    }

}

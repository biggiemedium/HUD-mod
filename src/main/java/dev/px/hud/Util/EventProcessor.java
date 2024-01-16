package dev.px.hud.Util;

import com.sun.javafx.geom.Vec3d;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Entity.Playerutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.ElementToggleEvent;
import dev.px.hud.Util.Event.Render3dEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class EventProcessor extends Util {

    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            HUDMod.elementInitalizer.getElements().forEach(e -> {
                if(e instanceof RenderElement) {
                    if(e.isToggled()) {
                        ((RenderElement) e).render(event.partialTicks);
                    }
                }
            });

            HUDMod.notificationManager.render2D();
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
            Util.sendClientSideMessage("To open the GUI press RSHIFT!", true);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(event.type == TickEvent.Type.CLIENT) {
            HUDMod.elementInitalizer.getElements().forEach(element -> {
                if (element instanceof ToggleableElement) {
                if (element.isToggled()) {
                    ((ToggleableElement) element).onUpdate();
                }
            }
            });
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if(Util.isNull()) {
            return;
        }


        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glLineWidth(1.0F);
        Render3dEvent render3dEvent = new Render3dEvent(event.partialTicks);
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            if(e instanceof ToggleableElement) {
                if(((ToggleableElement) e).isToggled()) {
                    ((ToggleableElement) e).onRender(render3dEvent);
                }
            }
        }
        GL11.glLineWidth(1.0F);
        GL11.glPopAttrib();
    }

    @SubscribeEvent
    public void onEntityHit(AttackEntityEvent event) {
        HUDMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onEnableMod(ElementToggleEvent.ElementEnableEvent event) {

    }

}

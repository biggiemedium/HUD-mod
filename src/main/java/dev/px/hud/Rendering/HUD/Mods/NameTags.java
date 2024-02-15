package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render3dEvent;
import dev.px.hud.Util.Event.RenderNametagEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameTags extends ToggleableElement {

    public NameTags() {
        super("Name tags", "", HUDType.RENDER);
    }

    @Override
    public void enable() {

    }

    /*
    @Override
    public void onRender(Render3dEvent event) {
        double pX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosX();
        double pY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosY();
        double pZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ();

    }
    
     */

    @SubscribeEvent
    public void nameTagRender(RenderLivingEvent.Specials.Pre<AbstractClientPlayer> event) {
        event.setCanceled(true);
    }

}

package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Mixin.Render.MixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameTags extends ToggleableElement {

    public NameTags() {
        super("Name tags", "", HUDType.RENDER);
    }

    Setting<Integer> distance = create(new Setting<>("Distance", 35, 1, 75));

    @Override
    public void enable() {

    }


    @Override
    public void onRender(Render3dEvent event) {

        if(mc.getRenderManager() == null || mc.getRenderManager().options == null) return;
        for(EntityPlayer e : mc.theWorld.playerEntities) {
            if(mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) { continue; }
            double pX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosX();
            double pY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosY();
            double pZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - ((MixinRenderManager) mc.getRenderManager()).getRenderPosZ();


        }

    }

    @SubscribeEvent
    public void onNameTagRender(RenderLivingEvent.Specials.Pre<EntityPlayer> event) {
        event.setCanceled(true);
    }

}

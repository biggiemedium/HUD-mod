package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render.RenderArmorEvent;
import dev.px.hud.Util.Event.World.PlayerPotionEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT) // does this rain strength thing send server packets ???
public class NoRender extends ToggleableElement {

    public NoRender() {
        super("No Render", "Prevents rendering", HUDType.RENDER);
        INSTANCE = this;
    }

    public Setting<Boolean> viewClip = create(new Setting<>("View Clip", true));
    public Setting<Boolean> fire = create(new Setting<>("Fire", false));
    public Setting<Boolean> fog = create(new Setting<>("Fog", true));
    public Setting<Boolean> armor = create(new Setting<>("Armor", false));
    public Setting<Boolean> weather = create(new Setting<>("Weather", false));
    public Setting<Boolean> cluster = create(new Setting<>("Cluster", true));

    @SubscribeEvent
    public void onArmorRender(RenderArmorEvent event) {
        if(armor.getValue()) {
            switch (event.getSlot())
            {
                case 1:
                    event.model.bipedRightLeg.showModel = true;
                    event.model.bipedLeftLeg.showModel = true;
                    break;
                case 2:
                    event.model.bipedBody.showModel = true;
                    event.model.bipedRightLeg.showModel = true;
                    event.model.bipedLeftLeg.showModel = true;
                    break;
                case 3:
                    event.model.bipedBody.showModel = true;
                    event.model.bipedRightArm.showModel = true;
                    event.model.bipedLeftArm.showModel = true;
                    break;
                case 4:
                    event.model.bipedHead.showModel = true;
                    event.model.bipedHeadwear.showModel = true;
            }
        }
    }

    @SubscribeEvent
    public void onFogDensity(final EntityViewRenderEvent.FogDensity event) {
        if (fog.getValue()) {
            event.density = (0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRender(RenderBlockOverlayEvent event) {
        if(event.overlayType == RenderBlockOverlayEvent.OverlayType.FIRE) {
            if(fire.getValue()) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onUpdate() {
        if(mc.thePlayer == null || mc.theWorld == null) return;
        if(weather.getValue()) {
            if(mc.theWorld.isRaining() || mc.theWorld.isThundering()) {
                mc.theWorld.setRainStrength(0.0f);
               // mc.theWorld.setThunderStrength(0);
            }
        }
    }

    public static NoRender INSTANCE;
}

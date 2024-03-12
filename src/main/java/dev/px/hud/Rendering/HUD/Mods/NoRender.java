package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        if(player == null || world == null) return;

        if(weather.getValue()) {
            if(world.isRaining() || world.isThundering()) {
                world.setRainStrength(0.0f);
                world.setThunderStrength(0.0f);
            }
        }

    }

    public static NoRender INSTANCE;
}

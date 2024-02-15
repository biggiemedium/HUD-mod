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



    Setting<Boolean> armor = create(new Setting<>("Armor", false));
    Setting<Integer> armorTransparency = create(new Setting<>("Transparency", 200, 0, 255, v -> armor.getValue()));



    @SubscribeEvent
    public void onRender(RenderBlockOverlayEvent event) {
        if(event.overlayType == RenderBlockOverlayEvent.OverlayType.FIRE) {
            if(fire.getValue()) {
                event.setCanceled(true);
            }
        }
    }



    public static NoRender INSTANCE;
}

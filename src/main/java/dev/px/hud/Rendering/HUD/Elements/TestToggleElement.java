package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.SpotifyAPIClient;
import dev.px.hud.Util.Event.Render.EventRenderScoreBoard;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TestToggleElement extends ToggleableElement {

    public TestToggleElement() {
        super("Test Toggle", HUDType.MOD);
    }



    @Override
    public void enable() {
    }

    @Override
    public void onUpdate() {

    }

}

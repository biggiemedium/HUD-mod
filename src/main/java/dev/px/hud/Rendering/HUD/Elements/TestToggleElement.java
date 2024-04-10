package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TestToggleElement extends ToggleableElement {

    public TestToggleElement() {
        super("Test Toggle", HUDType.MOD);
    }

    private EntityPlayer player;

    @Override
    public void onUpdate() {

    }

}

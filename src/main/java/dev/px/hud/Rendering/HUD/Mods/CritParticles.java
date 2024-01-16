package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Bus.Listener.EventHandler;
import dev.px.hud.Util.Event.Bus.Listener.Listener;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CritParticles extends ToggleableElement {

    public CritParticles() {
        super("Crit particles", "Crits", HUDType.RENDER);
    }

    private Setting<Integer> amount = create(new Setting<>("Amount", 1, 1, 10));

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @EventHandler
    public Listener<AttackEntityEvent> entityEventListener = new Listener<>(event -> {
        if(event.target instanceof EntityLivingBase) {
            Util.sendClientSideMessage("HIT", true);
        }
    });
    public void onAttack(AttackEntityEvent event) {

    }
}

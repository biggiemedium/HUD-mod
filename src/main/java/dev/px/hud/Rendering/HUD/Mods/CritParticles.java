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
        super("Crit particles", "Crits", HUDType.MOD);
    }

    private Setting<Integer> amount = create(new Setting<>("Amount", 5, 1, 10));
    private Setting<Particle> particleType = create(new Setting<>("Particle", Particle.Magic));
    private enum Particle {
        Crit,
        Magic,
        Flame
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @EventHandler
    public Listener<AttackEntityEvent> entityEventListener = new Listener<>(event -> {
        if(event.target == null) return;
        if(mc.thePlayer.getDistance(event.target.posX, event.target.posY, event.target.posZ) > 10) return;
        if(event.target instanceof EntityLivingBase) {
            if(((EntityLivingBase) event.target).hurtTime >= 5) {
                switch (particleType.getValue()) {
                    case Crit:
                        for (int i = 0; i < amount.getValue(); i++) {
                            mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.CRIT);
                        }
                        break;
                    case Flame:
                        for (int i = 0; i < amount.getValue(); i++) {
                            mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.FLAME);
                        }
                        break;
                    case Magic:
                        for (int i = 0; i < amount.getValue(); i++) {
                            mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.CRIT_MAGIC);
                        }
                        break;
                }
            }
        }
    });

}

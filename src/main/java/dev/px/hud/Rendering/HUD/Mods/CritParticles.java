package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Util;
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

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if(event.target instanceof EntityLivingBase) {
            if(event.target != mc.thePlayer) {
                for (int i = 0; i < amount.getValue(); i++) {
                    mc.effectRenderer.emitParticleAtEntity(event.target, EnumParticleTypes.CRIT);
                }
            }
        }
    }
}

package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Bus.Listener.EventHandler;
import dev.px.hud.Util.Event.Bus.Listener.Listener;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CritParticles extends ToggleableElement {

    public CritParticles() {
        super("Crit particles", "Crits", HUDType.MOD);
    }

    private Setting<Integer> amount = create(new Setting<>("Amount", 5, 1, 10));
    private Setting<Particle> particleType = create(new Setting<>("Particle", Particle.Blood));

    private enum Particle {
        Blood,
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

    @SubscribeEvent
    public void onPlayerHit(dev.px.hud.Util.Event.Entity.AttackEntityEvent event) {
        if(event.entity == null) return;
        if(event.entity instanceof EntityLivingBase) {
            for (int i = 0; i < amount.getValue(); i++) {
                switch (particleType.getValue()) {
                    case Blood:
                        mc.theWorld.spawnParticle(EnumParticleTypes.BLOCK_CRACK, event.entity.posX, event.entity.posY + event.entity.height - 0.75, event.entity.posZ, 0, 0, 0, Block.getStateId(Blocks.redstone_block.getDefaultState()));
                        break;
                    case Magic:
                        mc.theWorld.spawnParticle(EnumParticleTypes.CRIT_MAGIC, event.entity.posX, event.entity.posY + event.entity.height - 0.75, event.entity.posZ, 0, 0, 0);
                    break;
                    case Crit:
                        mc.theWorld.spawnParticle(EnumParticleTypes.CRIT, event.entity.posX, event.entity.posY + event.entity.height - 0.75, event.entity.posZ, 0, 0, 0);
                        break;
                    case Flame:
                        mc.theWorld.spawnParticle(EnumParticleTypes.FLAME, event.entity.posX, event.entity.posY + event.entity.height - 0.75, event.entity.posZ, 0, 0, 0);
                        break;
                }
            }
        }
    }

}

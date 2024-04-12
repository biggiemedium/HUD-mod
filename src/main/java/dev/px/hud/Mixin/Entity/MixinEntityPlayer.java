package dev.px.hud.Mixin.Entity;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.HackDetector.Detection;
import dev.px.hud.Util.Event.Entity.AttackEntityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

        //public int airTicks;

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void detectHacker(CallbackInfo ci) {
        if(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(this.posX, this.posY - 1, this.posZ)).getBlock() == Blocks.air) {
            for(Detection d : HUDMod.eventProcessor.check.detector.getDetections()) {
                d.airTicks++;
            }
        }else{
            for(Detection d : HUDMod.eventProcessor.check.detector.getDetections()) {
                d.airTicks = 0;
            }
        }
    }


    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    public void attackEntity(Entity entity, CallbackInfo ci) {
        if(entity.canAttackWithItem()) {
            AttackEntityEvent event = new AttackEntityEvent(entity);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }
}

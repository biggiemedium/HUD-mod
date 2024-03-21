package dev.px.hud.Mixin.Game.Entity;

import dev.px.hud.Util.Event.World.PlayerPotionEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {

    @Inject(method = "isPotionActive", at = @At("HEAD"), cancellable = true)
    public void isPotionActive(Potion potionIn, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        PlayerPotionEvent event = new PlayerPotionEvent(potionIn);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

}

package dev.px.hud.Mixin.Render.Model;

import dev.px.hud.Util.Event.Render.RenderArmorEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerBipedArmor.class)
public class MixinLayerBipedArmor {

    @Inject(method = "func_177179_a", at = @At(value = "HEAD"), cancellable = true)
    protected void setModelSlotVisible(ModelBiped model, int slotIn, CallbackInfo info) {
        RenderArmorEvent packet = new RenderArmorEvent(model, slotIn);
        MinecraftForge.EVENT_BUS.post(packet);

        if (packet.isCanceled())
            info.cancel();
    }

}

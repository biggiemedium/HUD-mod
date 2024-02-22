package dev.px.hud.Mixin.Render.Model;

import dev.px.hud.Rendering.HUD.Mods.OldAnimations;
import net.minecraft.client.model.ModelBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ModelBiped.class)
public class MixinModelBiped {

    @ModifyConstant(method = "setRotationAngles", constant = @Constant(floatValue = -0.5235988F))
    private float cancelRotation(float original) {
        return OldAnimations.INSTANCE.sword.getValue() ? 0 : original;
    }

}

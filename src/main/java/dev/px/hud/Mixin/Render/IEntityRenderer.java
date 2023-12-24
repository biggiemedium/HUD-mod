package dev.px.hud.Mixin.Render;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface IEntityRenderer {

    @Invoker(value = "setupCameraTransform")
    void invokeSetupCameraTransform(float var1, int var2);

}

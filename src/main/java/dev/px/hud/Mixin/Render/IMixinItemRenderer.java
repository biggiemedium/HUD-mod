package dev.px.hud.Mixin.Render;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface IMixinItemRenderer {

    @Accessor("equippedProgress")
    float getEqippedProgress();



}

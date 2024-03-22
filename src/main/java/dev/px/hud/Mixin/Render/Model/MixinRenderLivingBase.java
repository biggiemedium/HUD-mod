package dev.px.hud.Mixin.Render.Model;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RendererLivingEntity.class)
public class MixinRenderLivingBase <T extends EntityLivingBase> extends Render<T> {

    protected MixinRenderLivingBase() {
        super(null);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}

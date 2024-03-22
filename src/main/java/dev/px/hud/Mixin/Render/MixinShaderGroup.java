package dev.px.hud.Mixin.Render;

import dev.px.hud.Util.API.Render.MotionBlurRenderer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShaderGroup.class)
public abstract class MixinShaderGroup implements MotionBlurRenderer.IMixinShaderGroup {

    @Override
    @Accessor
    public abstract List<Shader> getListShaders();
}

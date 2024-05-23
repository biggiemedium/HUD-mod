package dev.px.hud.Util.API.Render;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import java.util.List;

import net.minecraft.client.shader.Shader;
import java.io.IOException;

public class MotionBlurRenderer {

    private static ResourceLocation location = new ResourceLocation("minecraft:shaders/post/motion_blur.json");
    private static final Logger logger = LogManager.getLogger();

    private Minecraft mc = Minecraft.getMinecraft();
    private ShaderGroup shader;
    private float shaderBlur;

    public static MotionBlurRenderer INSTANCE = new MotionBlurRenderer();

    public float getBlurFactor() {
        Element e = HUDMod.elementInitalizer.getElementByClass(dev.px.hud.Rendering.HUD.Mods.MotionBlur.class);
        if(e == null || !e.isToggled()) {
            return 0.0f;
        }
        return HUDMod.elementInitalizer.getElementByClass(dev.px.hud.Rendering.HUD.Mods.MotionBlur.class).amount.getValue();
    }

    public ShaderGroup getShader() {

        if (shader == null) {
            shaderBlur = Float.NaN;

            try {
                shader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
                shader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            } catch (JsonSyntaxException | IOException error) {
                logger.error("Could not load motion blur shader", error);
                return null;
            }
        }

        if (shaderBlur != getBlurFactor()) {
            ((IMixinShaderGroup)shader).getListShaders().forEach((shader) -> {
                ShaderUniform blendFactorUniform = shader.getShaderManager().getShaderUniform("BlurFactor");

                if (blendFactorUniform != null) {
                    blendFactorUniform.set(getBlurFactor());
                }
            });

            shaderBlur = getBlurFactor();
        }

        return shader;
    }

    public interface IMixinShaderGroup {
        List<Shader> getListShaders();
    }
}

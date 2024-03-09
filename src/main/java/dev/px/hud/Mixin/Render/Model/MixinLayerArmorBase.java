package dev.px.hud.Mixin.Render.Model;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Mods.NoRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Elementars
 */
@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase {

    @Inject(method = "renderLayer", at = @At("HEAD"), cancellable = true)
    public void renderArmor(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float p_177182_4_, float p_177182_5_, float p_177182_6_, float p_177182_7_, float p_177182_8_, int armorSlot, CallbackInfo ci) {
        NoRender nr = HUDMod.elementInitalizer.getElementByClass(NoRender.class);
        if(nr.isToggled() && nr.armor.getValue()) {
            ci.cancel();
        }

    }
}

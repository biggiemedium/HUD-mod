package dev.px.hud.Mixin.Render.Item;

import dev.px.hud.Rendering.HUD.Mods.OldAnimations;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem {

    @Unique
    private EntityLivingBase lastEntityToRenderFor = null;

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        lastEntityToRenderFor = entityToRenderFor;
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(" + "Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    public void renderItemModelForEntity_renderItem(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {

        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON && lastEntityToRenderFor instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) lastEntityToRenderFor;
            ItemStack heldStack = p.getHeldItem();
            if (heldStack != null && p.getItemInUseCount() > 0 && heldStack.getItemUseAction() == EnumAction.BLOCK) {
                renderSwordTransformation();
            }
        }

    }

    private void renderSwordTransformation() {
        if(OldAnimations.INSTANCE.sword.getValue()) {
            GlStateManager.translate(-0.15f, -0.2f, 0);
            GlStateManager.rotate(70, 1, 0, 0);
            GlStateManager.translate(0.119f, 0.2f, -0.024f);
        }
    }

}

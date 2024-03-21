package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ItemStackElement extends RenderElement {

    public ItemStackElement() {
        super("Item stack", 50, 50, HUDType.COMBAT);
        setTextElement(false);
    }

    private Animation horizontalAnimation = new Animation(200, false, Easing.LINEAR);
    private float customWidth = 75;
    private Renderutil.ScissorStack scissorStack = new Renderutil.ScissorStack();
    private ItemStack lastStack = null;
    private String lastname = null;
    private int stackSize;

    @Override
    public void render2D(Render2DEvent event) {

        setWidth(75);
        setHeight(30);
        if(horizontalAnimation == null) {
            horizontalAnimation = new Animation(300, false, Easing.LINEAR);
        }

        if (mc.currentScreen instanceof PanelGUIScreen) {

        }

        if(mc.thePlayer.getHeldItem() != null) {
            if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                horizontalAnimation.setState(true);
                lastStack = mc.thePlayer.getHeldItem();
                lastname = mc.thePlayer.getHeldItem().getDisplayName();
                stackSize = mc.thePlayer.getHeldItem().stackSize;
            } else {
                horizontalAnimation.setState(false);
            }
        } else {
            horizontalAnimation.setState(false);
        }

        if(horizontalAnimation.getAnimationFactor() > 0) {
            GL11.glPushMatrix();
            scissorStack.pushScissor(getX(), getY(), (int) (customWidth * horizontalAnimation.getAnimationFactor()), getHeight());

            RoundedShader.drawRound((float) getX(), (float) getY(), (float) (customWidth * horizontalAnimation.getAnimationFactor()), (float) getHeight(), 6, new Color(26, 26, 26, 120));
            Renderutil.drawBlurredShadow(getX(), getY(), (float) (customWidth * horizontalAnimation.getAnimationFactor()), getHeight(), 4,  new Color(26, 26, 26, 100));
            String s = stackSize == 1 ? stackSize + " Block" : stackSize + " Blocks";
            renderText(s, getX() + (getWidth() - 1 - getFontWidth(s)), getY() + + (getHeight() / 2) - 2, -1);

            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(lastStack, getX() + 3, getY() + (getHeight() / 2) - 7);
            //  mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, lastStack, getX() + 3, getY() + (getHeight() / 2) - 7, null);
            RenderHelper.disableStandardItemLighting();
            mc.getRenderItem().zLevel = 0.0F;
            GL11.glPopMatrix();

            scissorStack.popScissor();
            GL11.glPopMatrix();
        }
    }

}

package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Entity.Playerutil;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, HUDType.INFO);
        setTextElement(true);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }

    private enum Mode {
        UP, SIDE
    }

    private final ArrayList<Float> speeds = new ArrayList<>();
    private double lastVertices;
    private float currentSpeed;

    @Override
    public void render(float partialTicks) {

    }

    @Override
    public void render2D(Render2DEvent event) {
        setWidth(55);
        setHeight(55);
        GL11.glPushMatrix();
        RoundedShader
                .drawGradientRound
                        ((float) getX(), (float)  getY(), (float) getWidth(), (float) getHeight(),6f,
                Colorutil.applyOpacity(Colorutil.interpolateColorsBackAndForth(15, 270, HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), true), .85f),
                Colorutil.interpolateColorsBackAndForth(15, 0, HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), true),
                Colorutil.interpolateColorsBackAndForth(15, 180, HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), true),
        Colorutil.interpolateColorsBackAndForth(15, 90, HUDMod.colorManager.getMainColor(), HUDMod.colorManager.getAlternativeColor(), true));
        GL11.glPopMatrix();
        String s;

        int off = 0;
        for(EntityPlayer p : mc.theWorld.playerEntities) {
            if(p == null) {
                continue;
            }
            if(p.isDead) {
                continue;
            }
            off += getFontHeight();
            renderText(p.getName() + Entityutil.getHealth(p), getX(), getY() + off, -1);
        }

    }

    private float getSpeedInKM() {
        double deltaX = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double deltaZ = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;

        float distance = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

        double floor = Math.floor(( distance/1000.0f ) / ( 0.05f/3600.0f ));

        String formatter = String.valueOf(floor);

        if (!formatter.contains("."))
            formatter += ".0";

        return Float.valueOf(formatter);
    }

}

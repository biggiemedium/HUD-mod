package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import net.minecraft.util.MathHelper;

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

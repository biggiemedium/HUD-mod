package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;

public class CoordinateElement extends RenderElement {

    public CoordinateElement() {
        super("Coordinates", 2, 85, HUDType.INFO);
        setTextElement(true);
    }


    private Setting<Boolean> round = create(new Setting<>("Round", false));
    private Setting<Mode> mode = create(new Setting<>("Mode", Mode.Side));

    // defaults

    @Override
    public void render(float partialTicks) {
        drawBackground();
        switch (mode.getValue()) {
            case Up:
                String x = "X: " + Mathutil.round(mc.thePlayer.posX, round.getValue() ? 0 : 1);
                String y = "Y: " + Mathutil.round(mc.thePlayer.posY, round.getValue() ? 0 : 1);
                String z = "Z: " + Mathutil.round(mc.thePlayer.posZ, round.getValue() ? 0 : 1);

                renderText(getPlayerX(), getX(), getY(), fontColor.getValue().getRGB());
                renderText(getPlayerY(), getX(), getY() + getFontHeight(), fontColor.getValue().getRGB());
                renderText(getPlayerZ(), getX(), getY() + (getFontHeight() * 2), fontColor.getValue().getRGB());
                setWidth(getFontWidth(x));
                setHeight(getFontHeight() * 3);
                break;
            case Side:
                String hpos =
                        "X: " + Mathutil.round(mc.thePlayer.posX, round.getValue() ? 0 : 1) +
                        " Y: " + Mathutil.round(mc.thePlayer.posY, round.getValue() ? 0 : 1) +
                        " Z: " + Mathutil.round(mc.thePlayer.posZ, round.getValue() ? 0 : 1);

                String xyz = getPlayerX() + " " + getPlayerY() + " " + getPlayerZ();
                renderText(xyz, getX(), getY(), fontColor.getValue().getRGB());
                setWidth(getFontWidth(hpos));
                setHeight(getFontHeight());
                break;
        }
    }

    private enum Mode {
        Side,
        Up
    }

    private String getPlayerX() {
        String s = "X: ";
        double px = Mathutil.round(mc.thePlayer.posX, 1);
        double pxr = Mathutil.round(mc.thePlayer.posX, 0);
        if(round.getValue()) {
            s = "X: " + pxr;
        } else {
            s = "X: " + px;
        }

        return s;
    }

    private String getPlayerY() {
        String s = "Y: ";
        double px = Mathutil.round(mc.thePlayer.posY, 1);
        double pxr = Mathutil.round(mc.thePlayer.posY, 0);
        if(round.getValue()) {
            s = "Y: " + pxr;
        } else {
            s = "Y: " + px;
        }

        return s;
    }

    private String getPlayerZ() {
        String s = "Z: ";
        double px = Mathutil.round(mc.thePlayer.posZ, 1);
        double pxr = Mathutil.round(mc.thePlayer.posZ, 0);
        if(round.getValue()) {
            s = "Z: " + pxr;
        } else {
            s = "Z: " + px;
        }

        return s;
    }
}

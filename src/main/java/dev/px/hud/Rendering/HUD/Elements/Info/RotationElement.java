package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.network.Packet;

public class RotationElement extends RenderElement {

    public RotationElement() {
        super("Rotations", 2, 115, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Boolean> yaw = create(new Setting<>("Yaw", true));
    Setting<Boolean> pitch = create(new Setting<>("Pitch", true));
    Setting<Mode> mode = create(new Setting<>("Mode", Mode.Up));

    @Override
    public void render(float partialTicks) {
        super.render(partialTicks);

        String y = yaw.getValue() ? "Yaw: " + Mathutil.round(mc.thePlayer.rotationYaw, 2) : "";
        String p = pitch.getValue() ? "Pitch: " + Mathutil.round(mc.thePlayer.rotationPitch, 2) : "";
        drawBackground();

        if(mode.getValue() == Mode.Side) {
            renderText(getRotations(), getX(), getY(), fontColor.getValue().getRGB());
        } else {
            renderText(y, getX(), getY(), fontColor.getValue().getRGB());
            renderText(p, getX(), getY() + getFontHeight(), fontColor.getValue().getRGB());
        }
        setWidth(getFontWidth(mode.getValue() == Mode.Side ? y + p : "Pitch: 111111"));
        setHeight(mode.getValue() == Mode.Side ? getFontHeight() : getFontHeight() * 2);
    }

    private String getRotations() {
        String s = "";
        if(yaw.getValue()) {
            s += "Yaw: " + Mathutil.round(mc.thePlayer.rotationYaw, 2);
        }
        s += yaw.getValue() && pitch.getValue() ? " " : "";
        if(pitch.getValue()) {
            s += "Pitch: " + Mathutil.round(mc.thePlayer.rotationPitch, 2);
        }
        return s;
    }

    private enum Mode {
        Up,
        Side
    }
}

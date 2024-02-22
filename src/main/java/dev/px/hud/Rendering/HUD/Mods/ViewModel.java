package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.Settings.Setting;

public class ViewModel extends ToggleableElement {

    public ViewModel() {
        super("View Model", HUDType.MOD);
    }

    public Setting<Double> rightX = create(new Setting<>("Right X", 0D, -2D, 2D));
    public Setting<Double> rightY = create(new Setting<>("Right Y", 0D, -2D, 2D));
    public Setting<Double> rightZ = create(new Setting<>("Right Z", 0D, -2D, 2D));
    public Setting<Float> rightYaw = create(new Setting<>("RightYaw", -180F, 0F, 180F));
    public Setting<Float> rightPitch = create(new Setting<>("RightPitch", -180F, 0F, 180F));
    public Setting<Float> rightRoll = create(new Setting<>("RightRoll", -180F, 0F, 180F));
    public Setting<Float> rightScale = create(new Setting<>("RightScale", -180F, 0F, 180F));




}

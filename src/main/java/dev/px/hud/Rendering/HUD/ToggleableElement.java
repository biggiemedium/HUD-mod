package dev.px.hud.Rendering.HUD;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.Notification;
import net.minecraftforge.common.MinecraftForge;

public class ToggleableElement extends Element {

    private String name;
    private HUDType hudType;

    private boolean enabled;
    private int key;

    public ToggleableElement(String name, String description, HUDType hudType) {
        super(name, hudType);
        this.key = -1;
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register(this);
        HUDMod.notificationManager.Add(new Notification("Enabled", this.name + " was enabled!", Notification.NotificationType.INFO, 5));
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        HUDMod.notificationManager.Add(new Notification("Disabled", this.name + " was disabled!", Notification.NotificationType.INFO, 5));
    }

    public void setToggled(boolean toggled) {
        setToggled(toggled);

        if(isToggled()) {
            this.enable();
        } else {
            this.disable();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public HUDType getHudType() {
        return hudType;
    }

    @Override
    public void setHudType(HUDType hudType) {
        this.hudType = hudType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

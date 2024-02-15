package dev.px.hud.Rendering.HUD;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Util.API.Keybind;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Bus.Listener.Listenable;
import dev.px.hud.Util.Event.ElementToggleEvent;
import dev.px.hud.Util.Event.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.common.MinecraftForge;

public class ToggleableElement extends Element implements Listenable {

    private String name;
    private HUDType hudType;

    private boolean enabled;
    private int key;
    private Setting<Keybind> keybind = new Setting<Keybind>("Bind", new Keybind());

    public ToggleableElement(String name, String description, HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.hudType = hudType;
        this.key = -1;
        this.enabled = toggled;
        this.settings.add(keybind);
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register(this);
     //   HUDMod.notificationManager.Add(new Notification("Enabled", this.name + " was enabled!", Notification.NotificationType.INFO, 5));
        MinecraftForge.EVENT_BUS.post(new ElementToggleEvent.ElementEnableEvent(this));
        HUDMod.EVENT_BUS.subscribe(this);
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
       // HUDMod.notificationManager.Add(new Notification("Disabled", this.name + " was disabled!", Notification.NotificationType.INFO, 5));
        MinecraftForge.EVENT_BUS.post(new ElementToggleEvent.ElementDisableEvent(this));
        HUDMod.EVENT_BUS.unsubscribe(this);
    }

    public void setEnabled(boolean toggled) {
        setToggled(toggled);
        this.enabled = toggled;


        if(isEnabled()) {
            enable();
            MinecraftForge.EVENT_BUS.register(this);
            MinecraftForge.EVENT_BUS.post(new ElementToggleEvent.ElementEnableEvent(this));
            HUDMod.EVENT_BUS.subscribe(this);
        } else {
            disable();
            MinecraftForge.EVENT_BUS.unregister(this);
            MinecraftForge.EVENT_BUS.post(new ElementToggleEvent.ElementDisableEvent(this));
            HUDMod.EVENT_BUS.unsubscribe(this);
        }
    }

    public void toggle() {
        this.enabled = !enabled;
        setToggled(enabled);

        if(isEnabled()) {
            enable();
        } else {
            disable();
        }
    }

    public void onUpdate() {

    }

    public void onRender(Render3dEvent event) {

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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

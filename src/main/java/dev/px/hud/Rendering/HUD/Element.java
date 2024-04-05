package dev.px.hud.Rendering.HUD;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.Event.Bus.Listener.Listenable;
import dev.px.hud.Util.Event.Client.ElementToggleEvent;
import dev.px.hud.Util.Settings.Setting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class Element implements Wrapper, Listenable {

    private String name;
    private HUDType hudType;
    protected boolean toggled;

    protected Minecraft mc = Minecraft.getMinecraft();
    protected ArrayList<Setting<?>> settings;

    public Element(String name, HUDType hudType) {
        this.name = name;
        this.hudType = hudType;
        this.settings = new ArrayList<>();
        this.toggled = false;
    }

    protected <T> Setting<T> create(Setting<T> hudSetting) {
        this.settings.add(hudSetting);
        return hudSetting;
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
        this.toggled = toggled;


        if(isToggled()) {
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
        this.toggled = !toggled;
        setToggled(toggled);

        if(isToggled()) {
            enable();
        } else {
            disable();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HUDType getHudType() {
        return hudType;
    }

    public void setHudType(HUDType hudType) {
        this.hudType = hudType;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public enum HUDType {
        COMBAT("Combat"),
        INFO("Info"),
        RENDER("Render Mod"),
        MOD("Mods");

        HUDType(String name) {
            this.name = name;
        }

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

package dev.px.hud.Rendering.HUD;

import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Input.Keybind;
import dev.px.hud.Util.Event.Bus.Listener.Listenable;
import dev.px.hud.Util.Event.Client.ElementToggleEvent;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.common.MinecraftForge;

public class ToggleableElement extends Element implements Listenable {

    private String name, description;
    private HUDType hudType;

   // private boolean enabled;
    private int key;
    private Setting<Keybind> keybind = new Setting<Keybind>("Bind", new Keybind());

    public ToggleableElement(String name, String description, HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.hudType = hudType;
        this.description = description;
        this.key = -1;
     //   this.enabled = toggled;
        this.settings.add(keybind);
    }

    public ToggleableElement(String name, HUDType hudType) {
        super(name, hudType);
        this.name = name;
        this.hudType = hudType;
        this.description = "";
        this.key = -1;
      //  this.enabled = toggled;
        this.settings.add(keybind);
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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

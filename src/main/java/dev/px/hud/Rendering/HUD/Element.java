package dev.px.hud.Rendering.HUD;

import dev.px.hud.Util.Event.SettingUpdateEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.ArrayList;

public class Element implements Wrapper {

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

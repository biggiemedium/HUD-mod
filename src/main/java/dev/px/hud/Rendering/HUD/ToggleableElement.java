package dev.px.hud.Rendering.HUD;

import net.minecraftforge.common.MinecraftForge;

public class ToggleableElement extends Element {

    public int x, y, width, height;
    public HUDType type;
    public String name;

    public int keybind;

    public ToggleableElement(String name, int x, int y, int width, int height, HUDType hudType) {
        super(name, x, y, width, height, hudType);
        this.keybind = -1;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void render(float partialTicks) {
        super.render(partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }
}

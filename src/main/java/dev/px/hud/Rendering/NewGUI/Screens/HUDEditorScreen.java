package dev.px.hud.Rendering.NewGUI.Screens;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class HUDEditorScreen extends Screen {

    private int x, y, width, height;

    public HUDEditorScreen() {
        super("HUD Editor");
        setResourceLocation(new ResourceLocation("minecraft", "GUI/visual.png"));
    }

}

package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Dimension;
import net.minecraft.util.ResourceLocation;

public class TestScreen extends Screen {

    public int x, y, width, height;

    public TestScreen(int x, int y, int width, int height) {
        super("TESTING");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setResourceLocation(new ResourceLocation("minecraft", "GUI/cancel.png"));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        Fontutil.drawText("TESTINGSINGOSDNFSJKSUDFH", x + 5, y + 5, -1);
    }
}

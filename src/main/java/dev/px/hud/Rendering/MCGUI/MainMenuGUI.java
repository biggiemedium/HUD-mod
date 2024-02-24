package dev.px.hud.Rendering.MCGUI;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class MainMenuGUI extends GuiScreen {

    private PanoramaGUI panorama = new PanoramaGUI(width, height);

    private ResourceLocation title = new ResourceLocation("textures/gui/title/minecraft.png");

    private GuiButton modButton;

    @Override
    public void initGui() {
        panorama.init();
        panorama.updateSize(width, height);

        int y = this.height / 4 + 48;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, y, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, y + 24 * 1, I18n.format("menu.multiplayer")));
        this.buttonList.add(modButton = new GuiButton(2, this.width / 2 - 100, y + 24 * 2, 98 * 2 + 4, 20, "Mods"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, y + 72, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, y + 72, 98, 20, I18n.format("menu.quit")));
        //    this.buttonList.add(new GuiButtonLanguage(6, this.width / 2 - 124, y + 72)); // remove language button - ENGLISH IS THE ONLY RIGHT LANGUAGE >:) !!!!
        }

    @Override
    public void updateScreen() {
        super.updateScreen();
        panorama.update();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.disableAlpha();
        panorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();

        java.util.List<String> brandings = com.google.common.collect.Lists.reverse(net.minecraftforge.fml.common.FMLCommonHandler.instance().getBrandings(true));
        for (int brdline = 0; brdline < brandings.size(); brdline++) {
            String brd = brandings.get(brdline);
            if (!com.google.common.base.Strings.isNullOrEmpty(brd)) {
                this.drawString(this.mc.fontRendererObj, brd, 2, this.height - (10 + brdline * (this.mc.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            }
        }

        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.mc.getTextureManager().bindTexture(title);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void render(float x, float y, float width, float height) {
        render(x, y, width, height, 0F, 0F, 1F, 1F);
    }

    public void render(float x, float y, float width, float height, float u, float v, float t, float s) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
        renderer.pos(x, y, 0F).tex(u, v).endVertex();
        renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
        renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
        renderer.pos(x + width, y + height, 0F).tex(t, s).endVertex();
        renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
        tessellator.draw();

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiModList(this));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                this.mc.shutdown();
                break;
            case 5:
                this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }

}

package dev.px.hud.Rendering.MCGUI;

import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class CustomMainMenuGUI extends GuiScreen {

    private PanoramaGUI panorama = new PanoramaGUI(width, height);

    private static ResourceLocation title = new ResourceLocation("textures/gui/title/minecraft.png");
    private ResourceLocation title2 = new ResourceLocation("minecraft", "Textures/title.png");

    private GuiButton modButton;
    private GuiButton hudButton;

    @Override
    public void initGui() {
        panorama.init();
        panorama.updateSize(width, height);

        int y = this.height / 4 + 48;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, y, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, y + 24 * 1, I18n.format("menu.multiplayer")));
        this.buttonList.add(modButton = new GuiButton(2, this.width / 2 - 100, y + 24 * 2, 98 * 2 + 4, 20, "Mods"));
        this.buttonList.add(hudButton = new GuiButton(69420, this.width / 2 - 100, y + 24 * 3, 98 * 2 + 4, 20, "HUD Mod"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, y + 96, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, y + 96, 98, 20, I18n.format("menu.quit")));
      //  this.buttonList.add(new GuiButtonLanguage(6, this.width / 2 - 124, y + 72)); // remove language button - ENGLISH IS THE ONLY RIGHT LANGUAGE >:) !!!!
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

        GL11.glPushMatrix();
        float titleX = width / 2 - (100);
        float titleY = 20;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        renderT(titleX, titleY + 30, 200, 100);
        GL11.glPopMatrix();


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
            case 69420:
                this.mc.displayGuiScreen(new HUDMenuGUI());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void renderT(float x, float y, float width, float height) {
        renderT(x, y, width, height, 0F, 0F, 1F, 1F);
    }

    public void renderT(float x, float y, float width, float height, float u, float v, float t, float s) {
        bindTexture();
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

    private void bindTexture() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(title2);
        GlStateManager.enableTexture2D();
    }
}

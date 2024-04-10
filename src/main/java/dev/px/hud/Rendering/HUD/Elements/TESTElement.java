package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, 135, 50, HUDType.INFO);
        setTextElement(true);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }

    private enum Mode {
        UP, SIDE
    }

    private int offset = 13;
    protected float zLevelFloat;

    //   private SpotifyAPIClient api= new SpotifyAPIClient();
 //   private Track currentTrack;
 //   private CurrentlyPlayingContext currentPlayingContext;
    private boolean playing = false;
    private Color imageColor = Color.WHITE;
    private ResourceLocation currentAlbumCover;
    public final float albumCoverSize = getHeight();
    private final float playerWidth = 135;
    private Renderutil.ScissorStack stack = new Renderutil.ScissorStack();

    @Override
    public void render(float partialTicks) {
        String s = "";
    }

    @Override
    public void enable() {
        /*
        if(mc.thePlayer == null) {
            toggle();
            return;
        }

        Util.sendClientSideMessage("Testing");

        if(this.api == null) {
            this.api = new SpotifyAPIClient();
        }

        api.build("3e828637c29840eeaf6a76533e2b2d40", "00ea674442454a82b749c29204f900a0");
        api.startConnection();

        super.enable();

         */
    }

    @Override
    public void render2D(Render2DEvent event) {
        /*
        if(mc.thePlayer == null) {
            toggle();
            return;
        }

        if(this.api == null) {
            this.api = new SpotifyAPIClient();
        }

        if(api == null) {
            return;
        }

        if(api.getCurrentTrack() == null || api.getCurrentPlayingContext() == null) return;

        Util.sendClientSideMessage(api.getCurrentTrack().getName(), true);

        setWidth((int) albumCoverSize + (int) playerWidth);

        if (currentTrack != api.getCurrentTrack() || currentPlayingContext != api.getCurrentPlayingContext()) {
            this.currentTrack = api.getCurrentTrack();
            this.currentPlayingContext = api.getCurrentPlayingContext();
        }

        playing = currentPlayingContext.getIs_playing();
        Color color2 = Colorutil.darker(imageColor, .65f);

        float[] hsb = Color.RGBtoHSB(imageColor.getRed(), imageColor.getGreen(), imageColor.getBlue(), null);
        if (hsb[2] < .5f) {
            color2 = Colorutil.brighter(imageColor, .65f);
        }

        RoundedShader.drawGradientVertical(getX() + (albumCoverSize - 15), getY(), getWidth() + 15, getHeight(), 6,
                color2, imageColor);


        int diff = currentTrack.getDurationMs() - currentPlayingContext.getProgress_ms();
        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        String trackRemaining = String.format("-%s:%s", diffMinutes < 10 ? "0" +
                diffMinutes : diffMinutes, diffSeconds < 10 ? "0" + diffSeconds : diffSeconds);


        this.stack.pushScissor(getX() + (int) albumCoverSize, getY(), getWidth(), getHeight());
        final StringBuilder artistsDisplay = new StringBuilder();

        for (int artistIndex = 0; artistIndex < currentTrack.getArtists().length; artistIndex++) {
            ArtistSimplified artist = currentTrack.getArtists()[artistIndex];
            artistsDisplay.append(artist.getName()).append(artistIndex + 1 == currentTrack.getArtists().length ? '.' : ", ");
        }



        this.stack.popScissor();

         */

        if(mc.currentScreen instanceof PanelGUIScreen) {
            renderDammy();
        } else {
            renderT();
        }
        this.setWidth(182);
        this.setHeight(18);
    }

    private void renderT() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0){
            this.mc.getTextureManager().bindTexture(Gui.icons);
            --BossStatus.statusBarTime;
            this.mc.getTextureManager().bindTexture(Gui.icons);
            int j = 182;
            this.mc.getTextureManager().bindTexture(Gui.icons);
            int l = (int)(BossStatus.healthScale * (float)(j + 1));
            drawTexturedModalRect(this.getX(), this.getY() + offset, 0, 74, j, 5);
            drawTexturedModalRect(this.getX(), this.getY() + offset, 0, 74, j, 5);

            if (l > 0)
            {
                drawTexturedModalRect(this.getX(), this.getY() + offset, 0, 79, l, 5);
            }

            this.mc.getTextureManager().bindTexture(Gui.icons);

            String s = BossStatus.bossName;

            mc.fontRendererObj.drawStringWithShadow(s, (((182 / 2) - (mc.fontRendererObj.getStringWidth(s) / 2)) + this.getX()), (this.getY() - 10) + offset, 16777215);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(Gui.icons);
        }
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height){
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double) Renderutil.itemRender.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)Renderutil.itemRender.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)Renderutil.itemRender.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)Renderutil.itemRender.zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }


    private void renderDammy() {
        this.mc.getTextureManager().bindTexture(Gui.icons);
        --BossStatus.statusBarTime;
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int j = 182;
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int l = (int)(BossStatus.healthScale * (float)(j + 1));
        drawTexturedModalRect(this.getX(), this.getY() + offset + 1, 0, 74, j, 5);
        drawTexturedModalRect(this.getX(), this.getY() + offset + 1, 0, 74, j, 5);
        if (l > 0)
        {
            drawTexturedModalRect(this.getX(), this.getY() + offset + 1, 0, 79, l, 5);
        }
        this.mc.getTextureManager().bindTexture(Gui.icons);

        String s = "Bossbar";

        mc.fontRendererObj.drawStringWithShadow(s, (((182 / 2) - (mc.fontRendererObj.getStringWidth(s) / 2)) + this.getX()), (this.getY() - 10) + offset, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
    }

}

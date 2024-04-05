package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.SpotifyAPIClient;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.concurrent.TimeUnit;

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
    }

}

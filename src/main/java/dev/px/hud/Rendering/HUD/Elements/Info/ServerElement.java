package dev.px.hud.Rendering.HUD.Elements.Info;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Event.Render.Render2DEvent;

public class ServerElement extends RenderElement {

    public ServerElement() {
        super("Server", 60, 60, HUDType.INFO);
    }

    @Override
    public void render2D(Render2DEvent event) {
        renderText(getIP(), getX(), getY(), fontColor.getValue().getRGB());
        setWidth(getFontWidth(getIP()));
        setHeight(getFontHeight());
    }

    public String getIP() {
        String server = "Server: ";

        if(mc.isSingleplayer()) {
            server = "Server: SinglePlayer";
        } else if(mc.getCurrentServerData().serverIP != null) {
            server = "Server: " + mc.getCurrentServerData().serverIP;
        }

        return server;
    }
}

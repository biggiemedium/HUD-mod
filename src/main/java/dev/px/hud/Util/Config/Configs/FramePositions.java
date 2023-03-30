package dev.px.hud.Util.Config.Configs;

import dev.px.hud.ClickGUI.ClickGUI;
import dev.px.hud.ClickGUI.Render.HUDFrame;
import dev.px.hud.Util.Config.Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FramePositions extends Config {

    public FramePositions() {
        super("frame");
    }

    @Override
    public void saves() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getSubFile() + "frame.txt"));
            for(HUDFrame f : ClickGUI.INSTANCE.hudFrames()) {
                String s = f.isOpen() ? "open" : "closed";
                writer.write(f.getName() + ":" + f.getX() + ":" + f.getY() + ":" + s);
                writer.write("\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loads() {

    }
}

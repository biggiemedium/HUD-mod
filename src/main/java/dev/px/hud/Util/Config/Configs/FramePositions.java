package dev.px.hud.Util.Config.Configs;

import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.Rendering.Panel.ClickGUI.Frame;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Config.Config;
import dev.px.hud.Util.Config.ConfigManifest;

import java.io.*;

@ConfigManifest(name = "FramePosition")
public class FramePositions extends Config {

    private int x, y, width, height;

    public FramePositions() {
        super("GUI", "Frame");
    }

    @Override
    public void saves() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getSaveDoc()));
        for(Frame f : ClickGUI.INSTANCE.getFrames()) {
                String open = f.isOpen() ? "open" : "closed";
                writer.write(f.getName() + ":" + f.getX() + ":" + f.getY() + ":" + open);
                writer.write("\r\n");
            }

            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void loads() {
        try {
            FileInputStream inputStream = new FileInputStream(getSaveDoc());
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));

            for(Frame f : ClickGUI.INSTANCE.getFrames()) {
                String line;
                while ((line = writer.readLine()) != null) {
                    String cl = line.trim();
                    String name = cl.split(":")[0];
                    String x = cl.split(":")[1];
                    String y = cl.split(":")[2];
                    String open = cl.split(":")[3];

                    if(ClickGUI.INSTANCE.getFramebyName(name) == null) {
                        continue;
                    }

                    boolean openTab = open.equalsIgnoreCase("open");
                    ClickGUI.INSTANCE.getFramebyName(name).setX(Integer.parseInt(x));
                    ClickGUI.INSTANCE.getFramebyName(name).setY(Integer.parseInt(y));
                    ClickGUI.INSTANCE.getFramebyName(name).setOpen(openTab);
                }
            }
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

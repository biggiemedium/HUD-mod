package dev.px.hud.Util.Config.Configs;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.ClickGUI.ClickGUI;
import dev.px.hud.Rendering.Panel.ClickGUI.Frame;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.Config.Config;
import dev.px.hud.Util.Config.ConfigManifest;

import java.io.*;

@ConfigManifest(name = "RenderElementConfigs")
public class RenderElementConfig extends Config {

    public RenderElementConfig() {
        super("Client", "RenderElements");
    }

    @Override
    public void saves() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getSaveDoc()));
            for(Element e : HUDMod.elementInitalizer.getElements()) {
                if(e instanceof RenderElement) {
                    String open = e.isToggled() ? "on" : "off";
                    writer.write(e.getName() + ":" +
                            ((RenderElement) e).getX() + ":" +
                            ((RenderElement) e).getY() + ":" +
                            ((RenderElement) e).getWidth() + ":" +
                            ((RenderElement) e).getHeight() + ":" + open);

                    writer.write("\r\n");
                }
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

            for(Element e : HUDMod.elementInitalizer.getElements()) {
                String line;
                while ((line = writer.readLine()) != null) {
                    String cl = line.trim();
                    String name = cl.split(":")[0];
                    String x = cl.split(":")[1];
                    String y = cl.split(":")[2];
                    String width = cl.split(":")[3];
                    String height = cl.split(":")[4];
                    String open = cl.split(":")[5];

                    if(ClickGUI.INSTANCE.getFramebyName(name) == null) {
                        continue;
                    }

                    boolean openTab = open.equalsIgnoreCase("on");
                    ClickGUI.INSTANCE.getFramebyName(name).setX(Integer.parseInt(x));
                    ClickGUI.INSTANCE.getFramebyName(name).setY(Integer.parseInt(y));
                    ClickGUI.INSTANCE.getFramebyName(name).setWidth(Integer.parseInt(width));
                    ClickGUI.INSTANCE.getFramebyName(name).setHeight(Integer.parseInt(height));
                    ClickGUI.INSTANCE.getFramebyName(name).setOpen(openTab);
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

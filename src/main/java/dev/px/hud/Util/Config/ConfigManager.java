package dev.px.hud.Util.Config;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Panel.ClickGUI.Frame;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigManager {

    private Minecraft mc = Minecraft.getMinecraft();
    private File mainFile;
    private File settingsPath;
    private File elementPath;
    private File guiPath;

    public ConfigManager() {
        mainFile = new File(mc.mcDataDir + File.separator + HUDMod.MODID);
        if(!mainFile.exists()) {
            mainFile.mkdirs();
        }

        settingsPath = new File(mainFile + File.separator + "Settings");
        if(!settingsPath.exists()) {
            settingsPath.mkdirs();
        }

        elementPath = new File(mainFile + File.separator + "Elements");
        if(!elementPath.exists()) {
            elementPath.mkdirs();
        }

        guiPath = new File(mainFile + File.separator + "GUI");
        if(!guiPath.exists()) {
            guiPath.mkdirs();
        }

        loadRenderElements();
        loadToggleElements();
        loadRenderElementPositions();
        loadGUI();
        loadRenderElementSetting();
    }

    public void save() {
        saveRenderElements();
        saveToggleElements();
        saveRenderElementPositions();
        saveGUI();
        saveRenderElementSetting();
    }

    public void load() {

    }

    public void saveGUI() {

        try {
            File file = new File(this.guiPath, "GUI.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Frame f : PanelGUIScreen.INSTANCE.getCLICKGUI().getFrames()) {
                String s = f.isOpen() ? "open" : "closed";
                writer.write(f.getName() + ":" + f.getX() + ":" + f.getY() + ":" + s);
                writer.write("\r\n");
            }
            writer.close();
        } catch (Exception ignored) {}

    }

    public void loadGUI() {
        try {
            File f = new File(this.guiPath, "GUI.txt");
            FileInputStream inputStream = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = writer.readLine()) != null) {
                String cl = line.trim();
                String name = cl.split(":")[0];
                String x = cl.split(":")[1];
                String y = cl.split(":")[2];
                String open = cl.split(":")[3];

                if(PanelGUIScreen.INSTANCE.getCLICKGUI().getFramebyName(name) == null) { // || !f.exists()
                    continue;
                }

                boolean openTab = open.equalsIgnoreCase("open");
                PanelGUIScreen.INSTANCE.getCLICKGUI().getFramebyName(name).setX(Integer.parseInt(x));
                PanelGUIScreen.INSTANCE.getCLICKGUI().getFramebyName(name).setY(Integer.parseInt(y));
                PanelGUIScreen.INSTANCE.getCLICKGUI().getFramebyName(name).setOpen(openTab);
            }

        } catch (Exception ignored) {}
    }

    public void loadRenderElements() {
        try {
            File file = new File(this.elementPath, "RenderElements.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (Element m : HUDMod.elementInitalizer.getElements()) {
                    if(m instanceof ToggleableElement) {
                        continue;
                    }
                    try {
                        if (m.getName().equals(line)) {
                            m.setToggled(true);
                        }
                    } catch(Exception e) {}
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveRenderElements();
        }
    }

    public void saveRenderElements() {
        try {
            File file = new File(this.elementPath, "RenderElements.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element module : HUDMod.elementInitalizer.getElements()) {
                if(module instanceof ToggleableElement) {
                    continue;
                }
                try {
                    if (module.isToggled() && !module.getName().matches("null")) {
                        out.write(module.getName());
                        out.write("\r\n");
                    }
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }


    public void loadRenderElementPositions() {
        try {
            File f = new File(this.elementPath, "RenderElementPositions.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> lines = Files.readAllLines(f.toPath());
            ArrayList<Element> renderElements = new ArrayList<Element>();
            for(Element e : HUDMod.elementInitalizer.getElements()) { // fixed issue
                if(e instanceof ToggleableElement) {
                    continue;
                }
                if(e instanceof RenderElement) {
                    renderElements.add(e);
                }
            }
            int index = 0;
            for (String line : lines) {
                String[] regex = line.split(":");
                Element component = renderElements.get(index);
                assert component != null;
                if(component instanceof RenderElement) {
                    ((RenderElement) component).setX(Integer.parseInt(regex[1]));
                    ((RenderElement) component).setY(Integer.parseInt(regex[2]));
                    component.setToggled(regex[3].equals("open"));

                    index++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRenderElementPositions() {
        try {
            File file = new File(this.elementPath, "RenderElementPositions.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element f : HUDMod.elementInitalizer.getElements()) {
                if(f instanceof ToggleableElement) {
                    continue;
                }
                if(f instanceof RenderElement) {
                    String s = f.isToggled() ? "open" : "closed";
                    out.write(f.getName() + ":" + ((RenderElement) f).getX() + ":" + ((RenderElement) f).getY() + ":" + s);
                    out.write("\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
    }

    public void saveToggleElements() {
        try {
            File file = new File(this.elementPath, "ToggleElements.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element module : HUDMod.elementInitalizer.getElements()) {
                if(module instanceof RenderElement) {
                    continue;
                }
                try {
                    if (module.isToggled() && !module.getName().matches("null") && !module.getName().equalsIgnoreCase("fakeplayer")) {
                        out.write(module.getName());
                        out.write("\r\n");
                    }
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }

    public void loadToggleElements() {
        try {
            File file = new File(this.elementPath, "ToggleElements.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (Element m : HUDMod.elementInitalizer.getElements()) {
                    if(m instanceof RenderElement) {
                        continue;
                    }
                    if(m == null) {
                        continue;
                    }
                    try {
                        if (m.getName().equals(line)) {
                            ((ToggleableElement) m).setToggled(true);
                        }
                    } catch(Exception e) {}
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveToggleElements();
        }
    }

    public void saveRenderElementSetting() {
        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderBoolean.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Boolean) {
                            String v = (Boolean) set.getValue() ? "true" : "false";
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}
    }

    public void loadRenderElementSetting() {
        try {
            File f = new File(this.elementPath, "RenderBoolean.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while((line = reader.readLine()) != null) {
                String cl = line.trim();
                String name = cl.split(":")[0];
                String modName = cl.split(":")[1];
                String value = cl.split(":")[2];
                AtomicReference<Setting> setting = null;
                HUDMod.elementInitalizer.getElements().forEach(s -> s.getSettings().forEach(set -> {
                    if(set.getValue() instanceof Boolean) {
                        setting.set(set);
                    }
                }));

                if(setting != null) {
                    boolean val = value.equalsIgnoreCase("true");
                    setting.get().setValue(val);
                }
            }
            reader.close();
        } catch (Exception ignored) {}
    }

}
